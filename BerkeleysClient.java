import java.io.*;
import java.net.*;

public class BerkeleysClient {

    public static void main(String[] args) {

        String server = (args.length > 0) ? args[0] : "localhost";
        int port = 8094;

        try (
            Socket socket = new Socket(server, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {

            String request = in.readLine();

            if ("GetTime".equals(request)) {

                long time = System.currentTimeMillis();

                // send local time
                out.println(time);

                // receive offset
                String response = in.readLine();
                long offset = Long.parseLong(response.split(" ")[1]);

                long syncedTime = time + offset;

                System.out.println("Server offset: " + offset);
                System.out.println("Synchronized time: " + syncedTime);

            } else if (request.startsWith("SetTime")) {

                long offset = Long.parseLong(request.split(" ")[1]);

                long newTime = System.currentTimeMillis() + offset;

                System.out.println("Adjusted time after sync: " + newTime);

            } else {
                System.out.println("Invalid request from server.");
            }

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}