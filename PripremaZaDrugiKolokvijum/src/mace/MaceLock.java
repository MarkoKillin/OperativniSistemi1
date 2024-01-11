package mace;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MaceLock {
    private boolean[] igracke;
    private int brojIgracaka;
    private Lock l = new ReentrantLock();
    private Condition c = l.newCondition();

    public MaceLock(int brojIgracaka) {
        this.igracke = new boolean[brojIgracaka];
        this.brojIgracaka = brojIgracaka;
    }

    private int leva(int id) {
        return (id - 1 + 7) % brojIgracaka;
    }

    private int desna(int id) {
        return id;
    }

    public void uzmi(int id) throws InterruptedException {
        l.lock();
        try {
            while (igracke[leva(id)] || igracke[desna(id)]) {
                c.await();
            }
            igracke[leva(id)] = true;
            igracke[desna(id)] = true;
        } finally {
            l.unlock();
        }
    }

    public void vrati(int id) {
        l.lock();
        try {
            igracke[leva(id)] = false;
            igracke[desna(id)] = false;
            c.signalAll();
        } finally {
            l.unlock();
        }
    }
}
