package klizaliste.semaphore;

import java.util.concurrent.Semaphore;

/*
 * E)
 *
 * Zbog velike gužve, klizalište ima ograničen broj klizaljki, tj. po 2 para od
 * sledećih veličina: 28‐31, 32‐35, 36‐39, 40‐43, 44‐47, 48‐51 i 52‐55.
 *
 * Uključiti animaciju kršenja uslova postavljanjem polja MAX_CIPELA na 2.
 */
public class KlizalisteE {

    private Semaphore[] semaphores = {
            new Semaphore(2),
            new Semaphore(2),
            new Semaphore(2),
            new Semaphore(2),
            new Semaphore(2),
            new Semaphore(2),
            new Semaphore(2)
    };

    public void udji(int brojCipela) throws InterruptedException {
        int num = getNum(brojCipela);
        semaphores[num].acquire();
    }

    public void izadji(int brojCipela) {
        int num = getNum(brojCipela);
        semaphores[num].release();
    }

    private int getNum(int num) {
        return (num-28)/4;
    }
}
