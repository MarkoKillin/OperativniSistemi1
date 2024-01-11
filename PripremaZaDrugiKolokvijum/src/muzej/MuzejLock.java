package muzej;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MuzejLock {
    private Lock l = new ReentrantLock();
    private Condition englezi = l.newCondition();
    private Condition nemci = l.newCondition();
    private Condition italijani = l.newCondition();
    private int[] br = new int[3];


    public void zakljucajEnglezi() throws InterruptedException {
        l.lock();
        try {
            while (br[1] + br[2] > 0) {
                englezi.await();
            }
            br[0]++;
        } finally {
            l.unlock();
        }
    }

    public void zakljucajNemci() throws InterruptedException {
        l.lock();
        try {
            while (br[0] + br[2] > 0) {
                nemci.await();
            }
            br[1]++;
        } finally {
            l.unlock();
        }
    }

    public void zakljucajItalijani() throws InterruptedException {
        l.lock();
        try {
            while (br[1] + br[0] > 0) {
                italijani.await();
            }
            br[2]++;
        } finally {
            l.unlock();
        }
    }

    public void otkljucajEnglezi() {
        l.lock();
        try {
            br[0]--;
            if (br[0] == 0) {
                nemci.signalAll();
                italijani.signalAll();
            }
        } finally {
            l.unlock();
        }
    }

    public void otkljucajNemci() {
        l.lock();
        try {
            br[1]--;
            if (br[1] == 0) {
                englezi.signalAll();
                italijani.signalAll();
            }
        } finally {
            l.unlock();
        }
    }

    public void otkljucajItalijani() {
        l.lock();
        try {
            br[2]--;
            if (br[2] == 0) {
                englezi.signalAll();
                nemci.signalAll();
            }
        } finally {
            l.unlock();
        }
    }
}
