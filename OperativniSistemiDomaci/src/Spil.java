import java.util.HashMap;

public class Spil {
    private HashMap<Integer, Karta> spil;

    public Spil() {
        spil = new HashMap<>();
        for (int i = 1; i <= 14; i++) {
            spil.put(i, new Karta(Boja.TREF, Rang.valueOf("" + i)));
            spil.put(i, new Karta(Boja.PIK, Rang.valueOf("" + i)));
            spil.put(i, new Karta(Boja.HERC, Rang.valueOf("" + i)));
            spil.put(i, new Karta(Boja.KARO, Rang.valueOf("" + i)));
        }
    }
}
