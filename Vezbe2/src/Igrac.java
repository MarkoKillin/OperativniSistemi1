public class Igrac extends Thread{
    private Karta k;
    private Talon t;
    private Spil s;
    public Igrac(Talon t, Spil s){
        this.t = t;
        this.s = s;
    }
    @Override
    public void run() {
        this.k = s.uzmi();
        t.putCard(this.k);
        try {
            t.cekaj();
            if(t.jeNajjaca(k))
                System.out.println("Pobedio " + k);
            else
                System.out.println("Nije pobedio " + k);
        } catch (InterruptedException e) {
            System.out.println("Prekinut");
        }
    }
}
