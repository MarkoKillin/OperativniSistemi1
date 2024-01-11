package klizaliste.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * D)
 *
 * Zbog velike gužve, klizalište ima samo 10 parova klizaljki na raspolaganju
 * za iznajmljivanje.
 *
 * Uključiti animaciju kršenja uslova postavljanjem polja MAX_BR na 10.
 */
public class KlizalisteD {
    private AtomicInteger t;

    public KlizalisteD(int maxt) {
        this.t = new AtomicInteger(maxt);
    }

    public void udji() throws InterruptedException {
        boolean ok;
        do {
            int stara = t.get();
            int nova = stara - 1;
            ok = nova >= 0;
            if(ok)
                ok = t.compareAndSet(stara, nova);
        } while (!ok);
    }

    public void izadji() {
        boolean ok;
        do {
            int stara = t.get();
            int nova = stara + 1;
            ok = nova <= 10;
            if(ok)
                ok = t.compareAndSet(stara, nova);
        } while (!ok);
    }
}
