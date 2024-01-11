public class TofuPP extends Thread{
    private Restoran r;

    public TofuPP(Restoran r) {
        this.r = r;
    }

    @Override
    public void run() {
        try {
            while (!interrupted()){
                Thread.sleep(900);
                synchronized (r.tofuSync) {
                    r.tofu += 1000;
                    r.tofuSync.notifyAll();
                }
                System.out.println("tofu");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Tofu gotov");
    }
}
