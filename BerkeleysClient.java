import java.io.*;
import java.net.*;

public class BerkeleysClient {

    public static void main(String[] args) {

        String server = "localhost";
        int port = 8094;

        try (

                Socket socket =
                        new Socket(server, port);

                PrintWriter out =
                        new PrintWriter(
                                socket.getOutputStream(),
                                true);

                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()))
        ) {

            String request =
                    in.readLine();

            if ("GetTime".equals(request)) {

                // Artificial time difference
                long localTime =
                        System.currentTimeMillis()
                                + (long)
                                (Math.random()
                                        * 10000 - 5000);

                System.out.println(
                        "Local Time: "
                                + localTime);

                // Send local time
                out.println(localTime);

                // Receive offset
                String response =
                        in.readLine();

                if (response.startsWith("SetTime")) {

                    long offset =
                            Long.parseLong(
                                    response.split(" ")[1]);

                    long syncedTime =
                            localTime + offset;

                    System.out.println(
                            "Offset Received: "
                                    + offset);

                    System.out.println(
                            "Synchronized Time: "
                                    + syncedTime);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}