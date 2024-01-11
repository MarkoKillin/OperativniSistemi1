package klizaliste.semaphore;

import java.util.concurrent.Semaphore;

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
    private Semaphore s;

    public KlizalisteB(int maxT) {
        this.s = new Semaphore(maxT);
    }

    public void udji(int t) throws InterruptedException {
        s.acquire(t);
    }

    public void izadji(int t) {
        s.release(t);
    }
}
