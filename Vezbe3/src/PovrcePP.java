public class PovrcePP extends Thread{
    private Restoran r;

    public PovrcePP(Restoran r) {
        this.r = r;
    }

    @Override
    public void run() {
        try {
            while (!interrupted()){
                Thread.sleep(900);
                synchronized (r.povrceSync) {
                    r.povrce += 1000;
                    r.povrceSync.notifyAll();
                }
                System.out.println("povrce");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Povrce gotov");
    }
}
