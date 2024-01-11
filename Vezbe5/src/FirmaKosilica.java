public class FirmaKosilica {
    public Object vremeSync = new Object();
    public int vreme = 0;
    private Object[] kosilice = new Object[5];

    public FirmaKosilica() {
        for (int i = 0; i < 5; i++) {
            kosilice[i] = new Object();
        }
    }

    private final boolean[] zauzeta = new boolean[5];
    public synchronized Object getKosilica() throws InterruptedException {
        while (true){
            for (int i = 0; i < zauzeta.length; i++) {
                if(!zauzeta[i]){
                    zauzeta[i] = true;
                    System.out.println("Zauzeta " + (i+1) + " kosilica.");
                    return kosilice[i];
                }
            }
            wait();
        }
    }
    public synchronized void oslobodi(Object o){
        for (int i = 0; i < kosilice.length; i++) {
            if(kosilice[i] == o){
                zauzeta[i] = false;
                System.out.println("Oslobodjena " + (i+1) + " kosilica.");
                notifyAll();
            }
        }
    }

    public int getZarada(int cena){
        synchronized (vremeSync){
            return cena * vreme;
        }
    }
}
