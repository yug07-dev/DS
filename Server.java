import java.util.*;

public class Server {

    int id;
    boolean active = true;
    boolean coordinator = false;

    Server next;                 // for Ring
    List<Server> all = new ArrayList<>(); // for Bully

    static boolean electionRunning = false;

    public Server(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // =========================
    // SETUP METHODS
    // =========================
    public void addAll(Server s) {
        all.add(s);
    }

    public void setNext(Server s) {
        next = s;
    }

    public void crash() {
        active = false;
    }

    // =========================
    // 🔵 BULLY ALGORITHM (DYNAMIC)
    // =========================
    public void bullyElection() {

        if (!active || electionRunning) return;

        electionRunning = true;

        System.out.println("\nElection started by Server " + id);

        int maxId = id;
        boolean higherExists = false;

        for (Server s : all) {

            if (s.active && s.id > this.id) {
                System.out.println("Server " + id + " sends ELECTION to Server " + s.id);
                higherExists = true;
            }

            if (s.active && s.id > maxId) {
                maxId = s.id;
            }
        }

        if (!higherExists) {
            System.out.println("No higher server responding...");
        }

        System.out.println(">>> Server " + maxId + " becomes COORDINATOR <<<");

        for (Server s : all) {
            if (s.active) {
                s.updateCoordinator(maxId);
            }
        }

        electionRunning = false;
    }

    public void updateCoordinator(int leaderId) {
        coordinator = false;
        System.out.println("Server " + id + " updated coordinator: " + leaderId);
    }

    // =========================
    // 🟢 RING ALGORITHM (DYNAMIC)
    // =========================
    public void ringElection(List<Integer> visited) {

        if (!active) return;

        if (visited.contains(id)) {

            int max = -1;
            for (int x : visited) {
                if (x > max) max = x;
            }

            System.out.println("\nRing Election Completed");
            System.out.println(">>> Coordinator is Server " + max + " <<<");

            return;
        }

        visited.add(id);

        System.out.println("Server " + id + " forwards election in ring");

        next.ringElection(visited);
    }
}