public class Process extends Thread {

    int id;
    Process nextProcess;
    volatile boolean hasToken = false;

    static Token token;

    static int MAX_CYCLES = 5;
    static int cycleCount = 0;

    public Process(int id) {
        this.id = id;
    }

    public void setNext(Process next) {
        this.nextProcess = next;
    }

    public void assignToken(Token t) {
        this.token = t;
        this.hasToken = true;
    }

    public void receiveToken(Token t) {
        this.token = t;
        this.hasToken = true;
        System.out.println("Process " + id + " received token");
    }

    public void run() {

        while (cycleCount < MAX_CYCLES) {

            if (hasToken) {

                enterCriticalSection();

                System.out.println("Process " + id +
                        " passing token to Process " + nextProcess.id);

                nextProcess.receiveToken(token);
                hasToken = false;

                cycleCount++;
            }

            try {
                Thread.sleep(500);
            } catch (Exception e) {}
        }

        System.out.println("Process " + id + " finished execution");
    }

    private void enterCriticalSection() {
        System.out.println("Process " + id + " ENTERED Critical Section");

        try {
            Thread.sleep(1000);
        } catch (Exception e) {}

        System.out.println("Process " + id + " EXITED Critical Section");
    }
}