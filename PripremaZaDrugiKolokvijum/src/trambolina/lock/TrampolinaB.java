package trambolina.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * B)
 *
 * Zbog povećane mogućnosti povreda kada više dece skače na trambolini, stari
 * Pera ne dozvoljava da na njoj bude više od 5 dece. Takođe, ne terati mališa-
 * ne da nepotrebno čekaju ako ima mesta na trambolini.
 *
 */
public class TrampolinaB {
    private Lock l = new ReentrantLock();
    private Condition deca = l.newCondition();
    private int maxBr;
    private int currBr;

    public TrampolinaB(int maxBr) {
        this.maxBr = maxBr;
        this.currBr = 0;
    }

    public void udji() throws InterruptedException {
        l.lock();
        try {
            while (currBr + 1 > maxBr) {
                deca.await();
            }
            currBr++;
        } finally {
            l.unlock();
        }
    }

    public void izadji() {
        l.lock();
        try {
            currBr--;
            deca.signalAll();
        } finally {
            l.unlock();
        }
    }
}
