package klizaliste.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private int maxT;
    private int c = 0;
    private Lock l = new ReentrantLock();
    private Condition tezina = l.newCondition();
    public KlizalisteB(int maxT) {
        this.maxT = maxT;
    }

    public void udji(int t) throws InterruptedException {
        l.lock();
        try {
            while(c + t > maxT){
                tezina.await();
            }
            c += t;
        } finally {
            l.unlock();
        }
    }

    public void izadji(int t){
        l.lock();
        try {
            c -= t;
            tezina.signalAll();
        } finally {
            l.unlock();
        }
    }
}
