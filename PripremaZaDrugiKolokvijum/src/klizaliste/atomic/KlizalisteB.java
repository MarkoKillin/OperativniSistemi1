package klizaliste.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * B)
 *
 * Toplo vreme je učinilo da led na klizalištu ne bude dovoljno debeo i čvrst.
 * Ovakav led može da izdrži najviše 300 kilograma težine. Sinhronizovati kli-
 * zače tako da ne stupaju na klizalište ako bi pri tome pukao led.
 *
 * Uključiti animaciju kršenja uslova postavljanjem polja MAX_TEZINA na 300.
 */
public class KlizalisteB {
    private AtomicInteger t;

    public KlizalisteB(int maxt) {
        this.t = new AtomicInteger(maxt);
    }

    public void udji(int tezina) throws InterruptedException {
        boolean ok;
        do {
            int stara = t.get();
            int nova = stara - tezina;
            ok = nova >= 0;
            if(ok)
                ok = t.compareAndSet(stara, nova);
        } while (!ok);
    }

    public void izadji(int tezina) {
        boolean ok;
        do {
            int stara = t.get();
            int nova = stara + tezina;
            ok = nova <= 300;
            if(ok)
                ok = t.compareAndSet(stara, nova);
        } while (!ok);
    }
}
