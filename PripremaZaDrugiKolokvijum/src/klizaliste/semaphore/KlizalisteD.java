package klizaliste.semaphore;

import java.util.concurrent.Semaphore;

/*
 * D)
 *
 * Zbog velike gužve, klizalište ima samo 10 parova klizaljki na raspolaganju
 * za iznajmljivanje.
 *
 * Uključiti animaciju kršenja uslova postavljanjem polja MAX_BR na 10.
 */
public class KlizalisteD {
    private Semaphore s;

    public KlizalisteD(int maxT) {
        this.s = new Semaphore(maxT);
    }

    public void udji() throws InterruptedException {
        s.acquire();
    }

    public void izadji() {
        s.release();
    }
}
