import java.util.*;

public class Main {

    public static void main(String[] args) {

        // =========================
        // CREATE SERVERS
        // =========================
        Server s1 = new Server(1);
        Server s2 = new Server(2);
        Server s3 = new Server(3);
        Server s4 = new Server(4);
        Server s5 = new Server(5);

        // =========================
        // RANDOM FAILURE (DYNAMIC PART)
        // =========================

        // Uncomment different lines to test dynamic behavior

        //s5.crash();  // simulate failure of 5
        // s4.crash();  // simulate failure of 4
        // s3.crash();  // simulate failure of 3

        // =========================
        // BULLY SETUP
        // =========================
        s1.addAll(s2); s1.addAll(s3); s1.addAll(s4); s1.addAll(s5);
        s2.addAll(s1); s2.addAll(s3); s2.addAll(s4); s2.addAll(s5);
        s3.addAll(s1); s3.addAll(s2); s3.addAll(s4); s3.addAll(s5);
        s4.addAll(s1); s4.addAll(s2); s4.addAll(s3); s4.addAll(s5);
        s5.addAll(s1); s5.addAll(s2); s5.addAll(s3); s5.addAll(s4);

        // =========================
        // RING SETUP
        // =========================
        s1.setNext(s2);
        s2.setNext(s3);
        s3.setNext(s4);
        s4.setNext(s5);
        s5.setNext(s1);

        // =========================
        // DEMO (CHANGE START NODE = DYNAMIC)
        // =========================

        System.out.println("========== BULLY ALGORITHM ==========");
        s3.bullyElection();   // change this to s1/s2/s4/s5 for different results

        System.out.println("\n========== RING ALGORITHM ==========");
        List<Integer> visited = new ArrayList<>();
        s2.ringElection(visited);
    }
}