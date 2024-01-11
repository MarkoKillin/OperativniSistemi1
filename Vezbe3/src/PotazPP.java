public class PotazPP extends Thread{
    private Restoran r;

    public PotazPP(Restoran r) {
        this.r = r;
    }

    @Override
    public void run() {
        try {
            while (!interrupted()){
                Thread.sleep(900);
                synchronized (r.potazSync) {
                    r.potaz += 1000;
                    r.potazSync.notifyAll();
                }
                System.out.println("potaz");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Potaz gotov");
    }
}
