public class Ring {

    public static void main(String[] args) {

        int n = 3;

        Process[] processes = new Process[n];

        // Create processes
        for (int i = 0; i < n; i++) {
            processes[i] = new Process(i);
        }

        // Link ring
        for (int i = 0; i < n; i++) {
            processes[i].setNext(processes[(i + 1) % n]);
        }

        // Start processes
        for (int i = 0; i < n; i++) {
            processes[i].start();
        }

        // Give token to Process 0
        Token token = new Token();
        processes[0].assignToken(token);

        System.out.println("Token initially given to Process 0");
    }
}