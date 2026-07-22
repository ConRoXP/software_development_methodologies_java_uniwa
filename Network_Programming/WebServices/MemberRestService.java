import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/MemberService")
public class MemberRestService {

    private static final String FILE = "members.txt";
    private static int counter = 1;

    //1) Inserting Member
    @PUT
    @Path("/InsertMember")
    @Produces("text/plain")
    public String insertMember(@QueryParam("surname") String surname,
                               @QueryParam("name") String name,
                               @QueryParam("email") String email) {

        int id = counter++;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true))) {
            bw.write(id + "#" + surname + "#" + name + "#" + email);
            bw.newLine();
        } catch (IOException e) {
            return "+ERROR+";
        }

        return String.valueOf(id);
    }

    //2) Retrieving Member
    @GET
    @Path("/RetrieveMember/{id}")
    @Produces("text/html")
    public String retrieveMember(@PathParam("id") int id) {

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("#");
                if (Integer.parseInt(tokens[0]) == id) {

                    return "<html><body>" +
                           "<p>Name: " + tokens[2] + "</p>" +
                           "<p>Surname: " + tokens[1] + "</p>" +
                           "<p>Email: " + tokens[3] + "</p>" +
                           "</body></html>";
                }
            }
        } catch (IOException e) {
            return "<html><body>Error</body></html>";
        }

        return "<html><body>Member not found</body></html>";
    }
}