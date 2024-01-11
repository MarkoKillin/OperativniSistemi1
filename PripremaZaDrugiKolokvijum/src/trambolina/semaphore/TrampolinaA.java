package trambolina.semaphore;


import java.util.concurrent.Semaphore;

/*
 * Nažalost, i trambolina starog Pere je dosta stara pa ne može izdržati više
 * od 300 kila. Prilikom implementacije rešenja imati ovo u vidu i ne dozvoliti
 * da se trambolina pokida. Potrebno je blokirati mališane koji žele da skaču
 * na trambolini ako bi ukupna težina prešla 300 kila.
 */
public class TrampolinaA {
    private Semaphore s;

    public TrampolinaA(int max) {
        this.s = new Semaphore(max);
    }

    public void udji(int tezina) throws InterruptedException {
        s.acquire(tezina);
    }

    public void izadji(int tezina) {
        s.release(tezina);
    }
}
