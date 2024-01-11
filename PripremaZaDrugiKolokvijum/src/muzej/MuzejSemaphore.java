package muzej;

import java.util.concurrent.Semaphore;

public class MuzejSemaphore {
    private Semaphore muzej = new Semaphore(1);

    private Semaphore[] mutex = {
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1)
    };
    private int[] br = new int[3];

    public void zakljucajEnglezi() throws InterruptedException {
        mutex[0].acquire();
        try {
            if (br[0] == 0) {
                muzej.acquire();
            }
            br[0]++;
        } finally {
            mutex[0].release();
        }
    }

    public void zakljucajNemci() throws InterruptedException {
        mutex[1].acquire();
        try {
            if (br[1] == 0) {
                muzej.acquire();
            }
            br[1]++;
        } finally {
            mutex[1].release();
        }
    }

    public void zakljucajItalijani() throws InterruptedException {
        mutex[2].acquire();
        try {
            if (br[2] == 0) {
                muzej.acquire();
            }
            br[2]++;
        } finally {
            mutex[2].release();
        }
    }

    public void otkljucajEnglezi() throws InterruptedException {
        mutex[0].acquire();
        try {
            br[0]--;
            if (br[0] == 0) {
                muzej.release();
            }
        } finally {
            mutex[0].release();
        }
    }

    public void otkljucajNemci() throws InterruptedException {
        mutex[1].acquire();
        try {
            br[1]--;
            if (br[1] == 0) {
                muzej.release();
            }
        } finally {
            mutex[1].release();
        }
    }

    public void otkljucajItalijani() throws InterruptedException {
        mutex[2].acquire();
        try {
            br[2]--;
            if (br[2] == 0) {
                muzej.release();
            }
        } finally {
            mutex[2].release();
        }
    }
}
