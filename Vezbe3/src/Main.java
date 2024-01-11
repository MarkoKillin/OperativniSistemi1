public class Main {
    public static void main(String[] args) {
        Restoran r = new Restoran();
        PorudzbinaPP porudzbinaPP = new PorudzbinaPP(r);
        PovrcePP povrcePP = new PovrcePP(r);
        PotazPP potazPP = new PotazPP(r);
        HlebPP hlebPP = new HlebPP(r);
        TofuPP tofuPP = new TofuPP(r);

        porudzbinaPP.start();
        povrcePP.start();
        potazPP.start();
        hlebPP.start();
        tofuPP.start();
        System.out.println("Pokrenuti");

        try {
            Thread.sleep( 6_000);
        } catch (InterruptedException e) {}
        porudzbinaPP.interrupt();
        povrcePP.interrupt();
        potazPP.interrupt();
        hlebPP.interrupt();
        tofuPP.interrupt();
        System.out.println("Prekinuti");

        try {
            porudzbinaPP.join();
            povrcePP.join();
            potazPP.join();
            hlebPP.join();
            tofuPP.join();
        } catch (InterruptedException e) {}
        System.out.println("Zavrseni");

        synchronized (r.novacSync){
            System.out.println("Novac: " + r.novac);
        }
    }
}
