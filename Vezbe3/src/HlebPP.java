public class HlebPP extends Thread{
    private Restoran r;

    public HlebPP(Restoran r) {
        this.r = r;
    }

    @Override
    public void run() {
        try {
            while (!interrupted()){
                Thread.sleep(900);
                synchronized (r.hlebSync) {
                    r.hleb += 1000;
                    r.hlebSync.notifyAll();
                }
                System.out.println("hleb");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hleb gotov");
    }
}
