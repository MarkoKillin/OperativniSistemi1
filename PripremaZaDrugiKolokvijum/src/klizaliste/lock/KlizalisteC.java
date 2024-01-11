package klizaliste.lock;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private Lock l = new ReentrantLock();
    private Condition kliz = l.newCondition();
    private int m = 0;
    private int d = 0;

    public void udji(int pol) throws InterruptedException {
        if (pol == 1) {
            l.lock();
            try {
                while (d > 0) {
                    kliz.await();
                }
                m++;
            } finally {
                l.unlock();
            }
        } else {
            l.lock();
            try {
                while (m > 0) {
                    kliz.await();
                }
                d++;
            } finally {
                l.unlock();
            }
        }
    }

    public void izadji(int pol) {
        if (pol == 1) {
            l.lock();
            try {
                m--;
                if (m == 0) {
                    kliz.signalAll();
                }
            } finally {
                l.unlock();
            }
        } else {
            l.lock();
            try {
                d--;
                if (d == 0) {
                    kliz.signalAll();
                }
            } finally {
                l.unlock();
            }
        }
    }
}
