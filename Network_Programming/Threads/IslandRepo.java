import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class IslandRepo{

//1) Reading file
    public static Map<String, Island> loadIslands(String file) throws IOException {
        Map<String, Island> map = new HashMap<>();
  
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {

                // KEY#Name#Complex#Area#MaxAltitude#Population#Region#Prefecture
                String[] tokens = line.split("#");
                if (tokens.length != 8) {
                    System.out.println("Invalid entries");
                    continue;
                }
                String key= tokens[0];  // e.g. RHODES

                Island island = new Island(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7]);
                map.put(key, island);
            }
            br.close();
        }
        catch(IOException e){
            System.out.println("Error reading islands file");
        }

        return map;
    }

    //2) Creating Connection
    private static final int PORT = 6789;
    public static void main(String[] args) throws IOException {
        try {
            // 1) Load data once (initialization)
            Map<String, Island> islands = IslandRepo.loadIslands("islands.txt");
            System.out.println("Loaded islands: " + islands.size());

            // 2) Start server socket
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Service started on port " + PORT);

                // 3) Accept clients
                while(true){
                    System.out.println("Waiting for a client...");
                    try (Socket socket = serverSocket.accept()) {
                        System.out.println("Client connected");
                        socket.setSoTimeout(60000); //60 sec timeout
                        handleClient(socket, islands);

                        System.out.println("Client disconnected.");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //3) Handling Client
    private static void handleClient(Socket socket, Map<String, Island> islands) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println("Welcome to Island Service.");
        out.println("Type an island KEY (e.g. RHODES) or 'exit'.");

        try{
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.equalsIgnoreCase("exit")) {
                    out.println("Bye.");
                    break;
                }

                //keys are uppercase per statement but we normalize anyway
                String key = line.toUpperCase();

                Island island = islands.get(key);
                if (island == null) {
                    out.println("NOT FOUND");
                } else {
                    out.println(island.toString());
                }
            }
        } catch(SocketException e){
            e.printStackTrace();
        }
    }
}