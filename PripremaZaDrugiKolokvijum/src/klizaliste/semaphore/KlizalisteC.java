package klizaliste.semaphore;


import java.util.concurrent.Semaphore;

/*
 * C)
 *
 * Umesto osnovnog problema, sinhronizovati klizače tako da dok klizaju momci,
 * devojke ne stupaju na klizalište. Takođe, dok devojke klizaju, momci ne stu-
 * paju na klizalište.
 *
 * Uključiti animaciju kršenja uslova postavljanjem polja JEDAN_POL na true.
 */
public class KlizalisteC {
    private Semaphore[] mutex = {
            new Semaphore(1),
            new Semaphore(1)
    };
    private Semaphore kliz = new Semaphore(1);
    private int momci = 0;
    private int devojke = 0;

    public void udji(int pol) throws InterruptedException {
        if (pol == 1) {
            mutex[0].acquire();
            try {
                if (momci == 0) {
                    kliz.acquire();
                }
                momci++;
            } finally {
                mutex[0].release();
            }
        } else {
            mutex[1].acquire();
            try {
                if (devojke == 0) {
                    kliz.acquire();
                }
                devojke++;
            } finally {
                mutex[1].release();
            }
        }
    }

    public void izadji(int pol) throws InterruptedException {
        if (pol == 1) {
            mutex[0].acquire();
            try {
                momci--;
                if (momci == 0) {
                    kliz.release();
                }
            } finally {
                mutex[0].release();
            }
        } else {
            mutex[1].acquire();
            try {
                devojke--;
                if (devojke == 0) {
                    kliz.release();
                }
            } finally {
                mutex[1].release();
            }
        }
    }
}
