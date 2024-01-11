import java.util.Random;

class Spil {

    private static final String[] BOJE = "\u2660,\u2665,\u2666,\u2663".split(",");
    private static final String[] RANGOVI = "2,3,4,5,6,7,8,9,10,J,Q,K,A".split(",");
    private static final String[] DZOKERI = "\u2605,\u2606".split(",");
    private static final Random random = new Random();

    public synchronized Karta uzmi() {
        int id = random.nextInt(54);
        if (id == 53) {
            return new Karta('X', DZOKERI[0]);
        }
        if (id == 52) {
            return new Karta('X', DZOKERI[1]);
        }
        String boja = BOJE[id / 13];
        String rang = RANGOVI[id % 13];
        return new Karta(rang.charAt(0), boja);
    }

}
