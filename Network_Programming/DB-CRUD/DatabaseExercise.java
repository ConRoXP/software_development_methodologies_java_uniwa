import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseExercise {

    static final String AM = "23390338";

    static final String URL =
        "jdbc:mysql://localhost:3306/" + AM +
        "?useUnicode=true&characterEncoding=UTF-8";

    static final String USER = "root";
    static final String PASSWORD = "";

    static final String START = "Start_" + AM;
    static final String LAST = "Last_" + AM;

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement st = con.createStatement()) {

            //1) Print the initial table
            System.out.println("1. Initial table");
            printTable(con, START);

            //2) Student ID 23390338 is even
            // We copy the records with even IDs
            st.executeUpdate("DELETE FROM `" + LAST + "`");

            st.executeUpdate(
                "INSERT INTO `" + LAST + "` "
                + "(ID, Firstname, Lastname) "
                + "SELECT ID, Firstname, Lastname "
                + "FROM `" + START + "` "
                + "WHERE MOD(ID, 2) = 0"
            );

            System.out.println("\n2. After copying the records with even IDs");
            printTable(con, LAST);

            //3) Insert instructor's record
            String insertTeacher =
                "INSERT INTO `" + LAST + "` "
                + "(ID, Firstname, Lastname) "
                + "VALUES (?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(insertTeacher)) {
                ps.setInt(1, 0);
                ps.setString(2, "IName");
                ps.setString(3, "ILastname");

                ps.executeUpdate();
            }

            System.out.println("\n3. After inserting the instructor");
            printTable(con, LAST);

            //4) Read the records sorted by first name
            List<String[]> persons = new ArrayList<>();

            String selectSorted =
                "SELECT Firstname, Lastname "
                + "FROM `" + LAST + "` "
                + "ORDER BY Firstname, Lastname";

            try (ResultSet rs = st.executeQuery(selectSorted)) {
                while (rs.next()) {
                    String[] person = {
                        rs.getString("Firstname"),
                        rs.getString("Lastname")
                    };

                    persons.add(person);
                }
            }

            //Delete the old records
            st.executeUpdate("DELETE FROM `" + LAST + "`");

            //Insert the records again with IDs 0, 1, 2, ...
            String insertPerson =
                "INSERT INTO `" + LAST + "` "
                + "(ID, Firstname, Lastname) "
                + "VALUES (?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(insertPerson)) {
                int id = 0;

                for (String[] person : persons) {
                    ps.setInt(1, id);
                    ps.setString(2, person[0]);
                    ps.setString(3, person[1]);

                    ps.executeUpdate();
                    id++;
                }
            }

            System.out.println("\n4. Final sorted table");
            printTable(con, LAST);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void printTable(Connection con, String table) throws SQLException {
        String sql =
            "SELECT ID, Firstname, Lastname "
            + "FROM `" + table + "` "
            + "ORDER BY ID";

        try (Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            System.out.printf(
                "%-5s %-25s %-25s%n", //Column Allignment
                "ID", "Firstname", "Lastname"
            );

            while (rs.next()) {
                System.out.printf(
                    "%-5d %-25s %-25s%n",
                    rs.getInt("ID"),
                    rs.getString("Firstname"),
                    rs.getString("Lastname")
                );
            }
        }
    }
}