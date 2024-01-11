package mace;

import java.util.concurrent.Semaphore;

public class MaceSemaphore {
    private int brojIgracaka;
    private Semaphore[] igracke = {
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1)
    };

    private Semaphore mtx = new Semaphore(1);

    public MaceSemaphore(int brojIgracaka) {
        this.brojIgracaka = brojIgracaka;
    }

    private int leva(int id) {
        return (id - 1 + 7) % brojIgracaka;
    }

    private int desna(int id) {
        return id;
    }

    public void uzmi(int id) throws InterruptedException {
        boolean ok;
        do {
            mtx.acquire();
            try {
                boolean leva = igracke[leva(id)].tryAcquire();
                boolean desna = igracke[desna(id)].tryAcquire();
                ok = leva && desna;
                if (!ok) {
                    if (leva)
                        igracke[leva(id)].release();
                    if (desna)
                        igracke[desna(id)].release();
                }
            } finally {
                mtx.release();
            }
        } while (!ok);

    }

    public void vrati(int id) throws InterruptedException {
        mtx.acquire();
        try {
            igracke[leva(id)].release();
            igracke[desna(id)].release();
        } finally {
            mtx.release();
        }
    }
}
