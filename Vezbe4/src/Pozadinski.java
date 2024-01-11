public class Pozadinski extends Thread{
    private Kamp k;
    private Suma s;

    public Pozadinski(Kamp k, Suma s) {
        this.k = k;
        this.s = s;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20 && !interrupted(); i++) {
            k.donesiPecurke(s.traziPecurke());
        }

    }
}
