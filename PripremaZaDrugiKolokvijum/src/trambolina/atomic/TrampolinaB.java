package trambolina.atomic;


import java.util.concurrent.atomic.AtomicInteger;

/*
 * B)
 *
 * Zbog povećane mogućnosti povreda kada više dece skače na trambolini, stari
 * Pera ne dozvoljava da na njoj bude više od 5 dece. Takođe, ne terati mališa-
 * ne da nepotrebno čekaju ako ima mesta na trambolini.
 *
 */
public class TrampolinaB {
    private AtomicInteger t = new AtomicInteger(0);
    private int maxT;

    public TrampolinaB(int maxT) {
        this.maxT = maxT;
    }

    public void udji() {
        boolean ok;
        do {
            int stara = t.get();
            int nova = stara + 1;
            ok = nova <= maxT;
            if (ok) {
                ok = t.compareAndSet(stara, nova);
            }
        } while (!ok);
    }

    public void izadji() {
        boolean ok;
        do {
            int stara = t.get();
            int nova = stara - 1;
            ok = nova >= 0;
            if (ok) {
                ok = t.compareAndSet(stara, nova);
            }
        } while (!ok);
    }
}
