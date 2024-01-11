package trambolina.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * A)
 *
 * Nažalost, i trambolina starog Pere je dosta stara pa ne može izdržati više
 * od 300 kila. Prilikom implementacije rešenja imati ovo u vidu i ne dozvoliti
 * da se trambolina pokida. Potrebno je blokirati mališane koji žele da skaču
 * na trambolini ako bi ukupna težina prešla 300 kila.
 */
public class TrampolinaA {
    private int maxT;
    private AtomicInteger t = new AtomicInteger(0);

    public TrampolinaA(int maxT) {
        this.maxT = maxT;
    }

    public void udji(int tezina) {
        boolean ok;
        do {
            int stara = t.get();
            int nova = stara + tezina;
            ok = nova <= maxT;
            if (ok) {
                ok = t.compareAndSet(stara, nova);
            }
        } while (!ok);
    }

    public void izadji(int tezina) {
        boolean ok;
        do {
            int stara = t.get();
            int nova = stara - tezina;
            ok = nova >= 0;
            if (ok) {
                ok = t.compareAndSet(stara, nova);
            }
        } while (!ok);
    }
}
