import java.util.concurrent.atomic.AtomicInteger;

public class PisciCitaociAtomic {
    private AtomicInteger pisci = new AtomicInteger(0);
    private AtomicInteger citaoci = new AtomicInteger(0);
    Thread owner;

    public void zauzmiP(){
        boolean ok;
        do {
            int brC = citaoci.get();
            int brP = pisci.get();
            int brPN = brP + 1;
            ok = (brC == 0) && ((brP == 0) || (brP > 0 && Thread.currentThread() == owner));
            if(ok){
                ok = pisci.compareAndSet(brP, brPN);
            }
        } while (!ok);
    }

    public void oslobodiP(){
        if(pisci.decrementAndGet() == 0)
            owner = null;
    }

    public void zauzmiC(){
        boolean ok;
        do{
            int brP = pisci.get();
            int brC = citaoci.get();
            int brCN = brC + 1;
            ok = brP == 0;
            if(ok)
                ok = citaoci.compareAndSet(brC, brCN);
        } while (!ok);
    }

    public void oslobodiC(){
        citaoci.decrementAndGet();
    }

}
