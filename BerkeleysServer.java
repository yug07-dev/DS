import java.io.*;
import java.net.*;
import java.util.*;

public class BerkeleysServer {

    public static void main(String[] args) {

        int port = 8094;
        int numClients = 3;

        try (ServerSocket serverSocket =
                     new ServerSocket(port)) {

            System.out.println(
                    "Server started on port " + port);

            System.out.println(
                    "Waiting for "
                            + numClients
                            + " clients...");

            List<Socket> clients =
                    new ArrayList<>();

            // Accept clients
            while (clients.size() < numClients) {

                Socket client =
                        serverSocket.accept();

                clients.add(client);

                System.out.println(
                        "Client "
                                + clients.size()
                                + " connected.");
            }

            long[] times =
                    new long[numClients];

            // Receive times from clients
            for (int i = 0; i < numClients; i++) {

                Socket s = clients.get(i);

                PrintWriter out =
                        new PrintWriter(
                                s.getOutputStream(),
                                true);

                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(
                                        s.getInputStream()));

                out.println("GetTime");

                String response =
                        in.readLine();

                times[i] =
                        Long.parseLong(response);

                System.out.println(
                        "Client "
                                + (i + 1)
                                + " Time: "
                                + times[i]);
            }

            // Calculate average
            long sum = 0;

            for (long t : times) {
                sum += t;
            }

            long avg =
                    sum / numClients;

            System.out.println(
                    "\nAverage Time: " + avg);

            // Send offsets
            for (int i = 0; i < numClients; i++) {

                long offset =
                        avg - times[i];

                Socket s =
                        clients.get(i);

                PrintWriter out =
                        new PrintWriter(
                                s.getOutputStream(),
                                true);

                out.println(
                        "SetTime " + offset);

                System.out.println(
                        "Offset sent to Client "
                                + (i + 1)
                                + ": "
                                + offset);
            }

            // Close sockets
            for (Socket s : clients) {
                s.close();
            }

            System.out.println(
                    "\nSynchronization Complete.");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}