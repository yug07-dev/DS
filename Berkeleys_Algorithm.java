import java.io.*;
import java.net.*;
import java.util.*;

public class Berkeleys_Algorithm {

    public static void main(String[] args) {

        int port = 8094;
        int numClients = 1; // change if you want more clients

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server started on port " + port);
            System.out.println("Waiting for " + numClients + " client(s)...");

            List<Socket> clients = new ArrayList<>();

            // Accept clients
            while (clients.size() < numClients) {
                Socket client = serverSocket.accept();
                clients.add(client);
                System.out.println("Client " + clients.size() + " connected.");
            }

            long[] times = new long[numClients];

            // Request time from clients
            for (int i = 0; i < numClients; i++) {

                Socket s = clients.get(i);

                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

                out.println("GetTime");

                String response = in.readLine();
                times[i] = Long.parseLong(response);

                System.out.println("Client " + (i + 1) + " time: " + times[i]);
            }

            // Compute average time
            long sum = 0;
            for (long t : times) sum += t;

            long avg = sum / numClients;

            long[] offsets = new long[numClients];

            for (int i = 0; i < numClients; i++) {
                offsets[i] = avg - times[i];
            }

            // Send offset back to clients
            for (int i = 0; i < numClients; i++) {

                Socket s = clients.get(i);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);

                out.println("SetTime " + offsets[i]);
            }

            // Print final synchronized time
            System.out.println("\nSynchronized Times:");
            for (int i = 0; i < numClients; i++) {
                System.out.println("Client " + (i + 1) + ": " + (times[i] + offsets[i]));
            }

            // Close sockets
            for (Socket s : clients) {
                s.close();
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}