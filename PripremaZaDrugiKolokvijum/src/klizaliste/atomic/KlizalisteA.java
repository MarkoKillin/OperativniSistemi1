package klizaliste.atomic;


import java.util.concurrent.atomic.AtomicInteger;

/* A)
 *
 * Sinhronizovati ove mlade parove tako da momak neće iznajmiti klizaljke i
 * početi da kliza bez da sačeka devojku, niti će se obuti i otići bez devojke.
 * Analogno ni devojka neće uzeti klizaljke i klizati, ili otići iz dvorane bez
 * da i momak uradi to isto.
 */
public class KlizalisteA {
    private AtomicInteger brM = new AtomicInteger(0);
    private AtomicInteger brD = new AtomicInteger(0);

    public void stigao(int i) {
        if (i == 1) {
            brM.incrementAndGet();
            boolean ok;
            do {
                int stara = brD.get();
                int nova = stara + 1;
                ok = nova >= 0;
                if (ok) {
                    ok = brD.compareAndSet(stara, nova);
                }
            } while (!ok);
        } else {
            brD.incrementAndGet();
            boolean ok;
            do {
                int stara = brM.get();
                int nova = stara - 1;
                ok = nova >= 0;
                if (ok) {
                    ok = brM.compareAndSet(stara, nova);
                }
            } while (!ok);
        }
    }
}
