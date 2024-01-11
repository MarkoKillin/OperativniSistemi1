import java.util.concurrent.atomic.AtomicInteger;

public class BrojacDogadjaja {
    // implementacija brojaca dogadjaja pomocu semafora

    private AtomicInteger bd;

    //init metoda je konstruktor
    public BrojacDogadjaja(int v){
        this.bd = new AtomicInteger(v);
    }

    public int read(){
        return this.bd.get();
    }

    public void advance(){
        this.bd.incrementAndGet();
    }

    public void await(int v){
        boolean ok;
        do {
            int staraVrednost = bd.get();
            ok = staraVrednost == v;
        } while(!ok);
    }
}
