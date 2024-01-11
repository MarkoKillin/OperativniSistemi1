package trambolina.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * C)
 *
 * Kako su dečaci nestašniji i manje paze da nekog slučajno ne udare tokom ska-
 * kanja, potrebno je odvojiti dečake i devojčice, tj. blokirati ulaz devojči-
 * cama ako na trambolini trenutno skaču dečaci, odnosno dečacima ako je trenu-
 * tno koriste devojčice.
 */
public class TrampolinaC {
    private Lock l = new ReentrantLock();
    private Condition c = l.newCondition();
    private int brD = 0;

    public void udji(int pol) throws InterruptedException {
        l.lock();
        try {
            if (pol == 1) {
                while (brD < 0) {
                    c.await();
                }
                brD++;
            } else {
                while (brD > 0) {
                    c.await();
                }
                brD--;
            }
        } finally {
            l.unlock();
        }
    }

    public void izadji(int pol) {
        l.lock();
        try {
            if (pol == 1) {
                brD--;
            } else {
                brD++;
            }
            if(brD == 0)
                c.signalAll();
        } finally {
            l.unlock();
        }
    }
}
