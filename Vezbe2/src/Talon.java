public class Talon {
    private int brK = 0;
    private Karta najjaca;
    public synchronized void putCard(Karta k){
        if(najjaca == null || najjaca.rang < k.rang)
            najjaca = k;
        brK++;
        if(brK == 12)
            notifyAll();
    }

    public synchronized void cekaj() throws InterruptedException {
        while (brK < 12){
            wait();
        }
    }

    public boolean jeNajjaca(Karta k){
        return najjaca.rang == k.rang;
    }
}
