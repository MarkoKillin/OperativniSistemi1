package klizaliste.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/*
 * E)
 *
 * Zbog velike gužve, klizalište ima ograničen broj klizaljki, tj. po 2 para od
 * sledećih veličina: 28‐31, 32‐35, 36‐39, 40‐43, 44‐47, 48‐51 i 52‐55.
 *
 * Uključiti animaciju kršenja uslova postavljanjem polja MAX_CIPELA na 2.
 */
public class KlizalisteE {

    private AtomicIntegerArray arr = new AtomicIntegerArray(7);

    public void udji(int brojCipela) throws InterruptedException {
        boolean ok;
        int num = getNum(brojCipela);
        do {
            int stara = arr.get(num);
            int nova = stara + 1;
            ok = nova <= 2;
            if(ok)
                ok = arr.compareAndSet(num, stara, nova);
        } while (!ok);
    }

    public void izadji(int brojCipela) {
        arr.decrementAndGet(getNum(brojCipela));
    }

    private int getNum(int num) {
        return (num-28)/4;
    }
}
