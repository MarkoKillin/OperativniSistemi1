package trambolina.semaphore;


import java.util.concurrent.Semaphore;

/*
 * C)
 *
 * Kako su dečaci nestašniji i manje paze da nekog slučajno ne udare tokom ska-
 * kanja, potrebno je odvojiti dečake i devojčice, tj. blokirati ulaz devojči-
 * cama ako na trambolini trenutno skaču dečaci, odnosno dečacima ako je trenu-
 * tno koriste devojčice.
 */
public class TrampolinaC {
    private Semaphore[] mutex = {
            new Semaphore(1),
            new Semaphore(1)
    };
    private Semaphore s = new Semaphore(1);
    private int[] br = new int[2];


    public void udji(int i) throws InterruptedException {
        if (i == 1) {
            mutex[0].acquire();
            try {
                if (br[0] == 0) {
                    s.acquire();
                }
                br[0]++;
            } finally {
                mutex[0].release();
            }
        } else {
            mutex[1].acquire();
            try {
                if (br[1] == 0) {
                    s.acquire();
                }
                br[1]++;
            } finally {
                mutex[1].release();
            }
        }
    }

    public void izadji(int i) throws InterruptedException {
        if (i == 1) {
            mutex[0].acquire();
            try {
                br[0]--;
                if (br[0] == 0) {
                    s.release();
                }
            } finally {
                mutex[0].release();
            }
        } else {
            mutex[1].acquire();
            try {
                br[1]--;
                if (br[1] == 0) {
                    s.release();
                }
            } finally {
                mutex[1].release();
            }
        }
    }
}
