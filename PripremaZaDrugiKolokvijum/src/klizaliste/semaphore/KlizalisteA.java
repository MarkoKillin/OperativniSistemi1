package klizaliste.semaphore;


import java.util.concurrent.Semaphore;

/* A)
 *
 * Sinhronizovati ove mlade parove tako da momak neće iznajmiti klizaljke i
 * početi da kliza bez da sačeka devojku, niti će se obuti i otići bez devojke.
 * Analogno ni devojka neće uzeti klizaljke i klizati, ili otići iz dvorane bez
 * da i momak uradi to isto.
 */
public class KlizalisteA {
    private Semaphore momak = new Semaphore(0);
    private Semaphore devojka = new Semaphore(0);

    public void stigao(int i) throws InterruptedException {
        if (i == 1) {
            momak.release();
            devojka.acquire();
        } else {
            devojka.release();
            momak.acquire();
        }
    }
}
