package trambolina.semaphore;


import java.util.concurrent.Semaphore;

/*
 * B)
 *
 * Zbog povećane mogućnosti povreda kada više dece skače na trambolini, stari
 * Pera ne dozvoljava da na njoj bude više od 5 dece. Takođe, ne terati mališa-
 * ne da nepotrebno čekaju ako ima mesta na trambolini.
 *
 */
public class TrampolinaB {
    private Semaphore s;
    public TrampolinaB(int maxBr) {
        this.s = new Semaphore(maxBr);
    }

    public void udji() throws InterruptedException {
        s.acquire();
    }

    public void izadji() {
        s.release();
    }
}
