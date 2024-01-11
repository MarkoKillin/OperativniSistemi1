package muzej;

import java.util.concurrent.atomic.AtomicInteger;

public class MuzejAtomic {
    private AtomicInteger muzej = new AtomicInteger(0);

    public void zakljucajEnglezi() throws InterruptedException {
        boolean ok;
        do {
            int stara = muzej.get();
            int nova = stara + 11;
            ok = (stara % 11 == 0 && stara / 11 > 0) || stara == 0;
            if (ok)
                ok = muzej.compareAndSet(stara, nova);
        } while (!ok);
    }

    public void zakljucajNemci() throws InterruptedException {
        boolean ok;
        do {
            int stara = muzej.get();
            int nova = stara + 13;
            ok = (stara % 13 == 0 && stara / 13 > 0) || stara == 0;
            if (ok)
                ok = muzej.compareAndSet(stara, nova);
        } while (!ok);
    }

    public void zakljucajItalijani() throws InterruptedException {
        boolean ok;
        do {
            int stara = muzej.get();
            int nova = stara + 15;
            ok = (stara % 15 == 0 && stara / 15 > 0) || stara == 0;
            if (ok)
                ok = muzej.compareAndSet(stara, nova);
        } while (!ok);
    }

    public void otkljucajEnglezi() {
        boolean ok;
        do {
            int stara = muzej.get();
            int nova = stara - 11;
            ok = nova % 11 == 0 && nova / 11 >= 0;
            if (ok)
                ok = muzej.compareAndSet(stara, nova);
        } while (!ok);
    }

    public void otkljucajNemci() {
        boolean ok;
        do {
            int stara = muzej.get();
            int nova = stara - 13;
            ok = nova % 13 == 0 && nova / 13 >= 0;
            if (ok)
                ok = muzej.compareAndSet(stara, nova);
        } while (!ok);
    }

    public void otkljucajItalijani() {
        boolean ok;
        do {
            int stara = muzej.get();
            int nova = stara - 15;
            ok = nova % 15 == 0 && nova / 15 >= 0;
            if (ok)
                ok = muzej.compareAndSet(stara, nova);
        } while (!ok);
    }
}
