import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class MemberClient {

    public static void main(String[] args) {

        try {
            //1) PUT - Member Insertion
            String putUrl =
                "http://localhost:8080/conference2026/webresources/MemberService/InsertMember"
              + "?surname=Papadopoulos"
              + "&name=Giorgos"
              + "&email=g@mail.com";

            URI uri = URI.create(putUrl);
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String id = in.readLine();  // respone -> id of inserted member
            in.close();

            System.out.println("Member inserted with id: " + id);

            //2) GET – Member Retrieval
            String getUrl =
                "http://localhost:8080/YourApp/webresources/MemberService/RetrieveMember/" + id;

            URI uri2 = URI.create(getUrl);
            URL url2 = uri2.toURL();
            HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
            con2.setRequestMethod("GET");

            BufferedReader in2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));

            String line;
            System.out.println("\nRetrieved member:");
            while ((line = in2.readLine()) != null)
                System.out.println(line);
            in2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}