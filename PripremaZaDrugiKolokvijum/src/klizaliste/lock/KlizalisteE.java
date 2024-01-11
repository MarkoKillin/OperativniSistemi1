package klizaliste.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * E)
 *
 * Zbog velike gužve, klizalište ima ograničen broj klizaljki, tj. po 2 para od
 * sledećih veličina: 28‐31, 32‐35, 36‐39, 40‐43, 44‐47, 48‐51 i 52‐55.
 *
 * Uključiti animaciju kršenja uslova postavljanjem polja MAX_CIPELA na 2.
 */
public class KlizalisteE {
    private Lock l = new ReentrantLock();
    private Condition[] conditions = {
            l.newCondition(),
            l.newCondition(),
            l.newCondition(),
            l.newCondition(),
            l.newCondition(),
            l.newCondition(),
            l.newCondition()
    };
    private int[] cip = new int[7];

    public void udji(int brojCipela) throws InterruptedException {
        l.lock();
        try {
            int num = getNum(brojCipela);
            while (cip[num] + 1 > 2){
                conditions[num].await();
            }
            cip[num]++;
        } finally {
            l.unlock();
        }
    }

    public void izadji(int brojCipela) {
        l.lock();
        try {
            int num = getNum(brojCipela);
            cip[num]--;
            conditions[num].signal();
        } finally {
            l.unlock();
        }
    }

    private int getNum(int num) {
        return (num-28)/4;
    }
}
