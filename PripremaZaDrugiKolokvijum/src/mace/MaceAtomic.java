package mace;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class MaceAtomic {
    private int brojIgracaka;
    private AtomicIntegerArray igracke;

    public MaceAtomic(int brojIgracaka) {
        this.brojIgracaka = brojIgracaka;
        this.igracke = new AtomicIntegerArray(brojIgracaka);
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
            int stara = igracke.get(leva(id));
            int nova = stara + 1;
            ok = nova == 1;
            if(ok)
                ok = igracke.compareAndSet(leva(id), stara, nova);
        } while (!ok);
        do {
            int stara = igracke.get(desna(id));
            int nova = stara + 1;
            ok = nova == 1;
            if(ok)
                ok = igracke.compareAndSet(desna(id), stara, nova);
        } while (!ok);
    }

    public void vrati(int id) {
        igracke.decrementAndGet(leva(id));
        igracke.decrementAndGet(desna(id));
    }
}
