package klizaliste.lock;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* A)
 *
 * Sinhronizovati ove mlade parove tako da momak neće iznajmiti klizaljke i
 * početi da kliza bez da sačeka devojku, niti će se obuti i otići bez devojke.
 * Analogno ni devojka neće uzeti klizaljke i klizati, ili otići iz dvorane bez
 * da i momak uradi to isto.
 */
public class KlizalisteA {
    private Lock l = new ReentrantLock();
    private int brM = 0;
    private int brD = 0;
    private Condition momak = l.newCondition();
    private Condition devojka = l.newCondition();

    public void stigao(int i) throws InterruptedException {
        if (i == 1) {
            l.lock();
            try {
                brM++;
                momak.signal();
                while(brD == 0){
                    devojka.await();
                }
                brD--;
            } finally {
                l.unlock();
            }
        } else {
            l.lock();
            try {
                brD++;
                devojka.signal();
                while (brM == 0){
                    momak.await();
                }
                brM++;
            } finally {
                l.unlock();
            }
        }
    }
}
