package trambolina.atomic;


import java.util.concurrent.atomic.AtomicInteger;

/*
 * C)
 *
 * Kako su dečaci nestašniji i manje paze da nekog slučajno ne udare tokom ska-
 * kanja, potrebno je odvojiti dečake i devojčice, tj. blokirati ulaz devojči-
 * cama ako na trambolini trenutno skaču dečaci, odnosno dečacima ako je trenu-
 * tno koriste devojčice.
 */
public class TrampolinaC {
    private AtomicInteger t = new AtomicInteger(0);

    public void udji(int i) {
        if (i == 1) {
            boolean ok;
            do {
                int stara = t.get();
                int nova = stara + 1;
                ok = nova > 0;
                if (ok) {
                    ok = t.compareAndSet(stara, nova);
                }
            } while (!ok);
        } else {
            boolean ok;
            do {
                int stara = t.get();
                int nova = stara - 1;
                ok = nova < 0;
                if (ok) {
                    ok = t.compareAndSet(stara, nova);
                }
            } while (!ok);
        }
    }

    public void izadji(int i) {
        if (i == 1) {
            boolean ok;
            do {
                int stara = t.get();
                int nova = stara - 1;
                ok = nova >= 0;
                if (ok) {
                    ok = t.compareAndSet(stara, nova);
                }
            } while (!ok);
        } else {
            boolean ok;
            do {
                int stara = t.get();
                int nova = stara + 1;
                ok = nova <= 0;
                if (ok) {
                    ok = t.compareAndSet(stara, nova);
                }
            } while (!ok);
        }
    }
}
