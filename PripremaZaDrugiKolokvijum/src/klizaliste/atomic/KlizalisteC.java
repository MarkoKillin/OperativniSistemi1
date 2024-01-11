package klizaliste.atomic;


import java.util.concurrent.atomic.AtomicInteger;

/*
 * C)
 *
 * Umesto osnovnog problema, sinhronizovati klizače tako da dok klizaju momci,
 * devojke ne stupaju na klizalište. Takođe, dok devojke klizaju, momci ne stu-
 * paju na klizalište.
 *
 * Uključiti animaciju kršenja uslova postavljanjem polja JEDAN_POL na true.
 */
public class KlizalisteC {
    private AtomicInteger broj = new AtomicInteger(0);



    public void udji(int pol) throws InterruptedException {
        if (pol == 1) {
            boolean ok;
            do {
                int stara = broj.get();
                int nova = stara + 1;
                ok = nova > 0;
                if(ok)
                    ok = broj.compareAndSet(stara, nova);
            } while (!ok);
        } else {
            boolean ok;
            do {
                int stara = broj.get();
                int nova = stara - 1;
                ok = nova < 0;
                if(ok)
                    ok = broj.compareAndSet(stara, nova);
            } while (!ok);
        }
    }

    public void izadji(int pol) throws InterruptedException {
        if (pol == 1) {
            broj.decrementAndGet();
        } else {
            broj.incrementAndGet();
        }
    }
}
