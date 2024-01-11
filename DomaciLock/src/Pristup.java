public class Pristup {
    //broj ulazaka u rekurziju za istog korisnika
    private int brP = 0;
    //broj razlicitih korisnika koji citaju
    private int brC = 0;
    //referenca na vlasnika trenutnog locka
    Thread owner;

    public synchronized void zauzmiP() throws InterruptedException {
        while ((brP > 0 && owner != Thread.currentThread()) || brC > 0) {
            wait();
        }
        owner = Thread.currentThread();
        brP++;
    }

    public synchronized void oslobodiP() {
        brP--;
        if(brP == 0){
            notifyAll();
            owner = null;
        }
    }

    public synchronized void zauzmiC() throws InterruptedException {
        while (brP > 0) {
            wait();
        }
        brC++;
    }

    public synchronized void oslobodiC() {
        brC--;
        if (brC == 0) {
            notify();
        }
    }
}
