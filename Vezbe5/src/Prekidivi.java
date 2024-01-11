public class Prekidivi extends Thread{
    private FirmaKosilica fk;

    public Prekidivi(FirmaKosilica fk) {
        this.fk = fk;
    }

    @Override
    public void run() {
        int vreme = (int) (Math.random()*2 + 2);
        int sek = 0;
        Object lock = null;
        try {
            lock = fk.getKosilica();
        } catch (InterruptedException e) {
        }
        if(lock != null){
            synchronized (lock){
                try {
                    while(!interrupted() && sek < vreme) {
                        Thread.sleep(1_000);
                        sek++;
                    }
                } catch (InterruptedException e){
                }
            }
            fk.oslobodi(lock);
        }
        synchronized (fk.vremeSync){
            fk.vreme += sek;
        }
        System.out.println(getName() + " zavrsio");
    }
}
