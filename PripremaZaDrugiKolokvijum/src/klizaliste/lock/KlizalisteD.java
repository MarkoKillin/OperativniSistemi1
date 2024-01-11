package klizaliste.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * D)
 *
 * Zbog velike gužve, klizalište ima samo 10 parova klizaljki na raspolaganju
 * za iznajmljivanje.
 *
 * Uključiti animaciju kršenja uslova postavljanjem polja MAX_BR na 10.
 */
public class KlizalisteD {
    private int max;
    private int c = 0;
    private Lock l = new ReentrantLock();
    private Condition cipele = l.newCondition();
    public KlizalisteD(int max) {
        this.max = max;
    }

    public void udji() throws InterruptedException {
        l.lock();
        try {
            while(c + 1 > max){
                cipele.await();
            }
            c++;
        } finally {
            l.unlock();
        }
    }

    public void izadji(){
        l.lock();
        try {
            c--;
            cipele.signalAll();
        } finally {
            l.unlock();
        }
    }
}
