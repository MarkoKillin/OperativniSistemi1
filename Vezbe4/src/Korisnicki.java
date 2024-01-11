public class Korisnicki implements Runnable{
    private Kamp k;
    private Suma s;

    public Korisnicki(Kamp k, Suma s) {
        this.k = k;
        this.s = s;
    }

    @Override
    public void run() {
        for (int i = 0; i < 25; i++) {
            k.donesiDrva(s.traziDrva());
        }
    }
}
