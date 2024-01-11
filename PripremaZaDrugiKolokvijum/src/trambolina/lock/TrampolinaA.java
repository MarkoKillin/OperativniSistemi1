package trambolina.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Nažalost, i trambolina starog Pere je dosta stara pa ne može izdržati više
 * od 300 kila. Prilikom implementacije rešenja imati ovo u vidu i ne dozvoliti
 * da se trambolina pokida. Potrebno je blokirati mališane koji žele da skaču
 * na trambolini ako bi ukupna težina prešla 300 kila.
 */
public class TrampolinaA {
    private Lock l = new ReentrantLock();
    private Condition tezina = l.newCondition();
    private int maxT;
    private int currT;

    public TrampolinaA(int maxT) {
        this.maxT = maxT;
        this.currT = 0;
    }

    public void udji(int t) throws InterruptedException {
        l.lock();
        try {
            while (currT + t > maxT) {
                tezina.await();
            }
            currT += t;
        } finally {
            l.unlock();
        }
    }

    public void izadji(int t) {
        l.lock();
        try {
            currT -= t;
            tezina.signalAll();
        } finally {
            l.unlock();
        }
    }
}
