public class PorudzbinaPP extends Thread{
    private Restoran r;

    public PorudzbinaPP(Restoran r) {
        this.r = r;
    }

    @Override
    public void run() {
        try {
            while (!interrupted()){
                Thread.sleep(100);
                int index = (int) (Math.random() * 3);
                //sendvic
                if(index == 0){
                    int hleb = 2;
                    int tofu = 1;
                    int povrce = 100;
                    int novac = 230;
                    synchronized (r.hlebSync){
                        try {
                            while (r.hleb < hleb) {
                                r.hlebSync.wait();
                            }
                        } catch (InterruptedException ignored) {}
                        r.hleb -= hleb;
                    }
                    synchronized (r.tofuSync){
                        try {
                            while (r.tofu < tofu) {
                                r.tofuSync.wait();
                            }
                        } catch (InterruptedException ignored) {}
                        r.tofu -= tofu;
                    }
                    synchronized (r.povrceSync){
                        try {
                            while (r.povrce < povrce) {
                                r.povrceSync.wait();
                            }
                        } catch (InterruptedException ignored) {}
                        r.povrce -= povrce;
                    }
                    synchronized (r.novacSync){
                        r.novac += novac;
                    }
                } else if(index == 1){
                    int hleb = 1;
                    double potaz = 0.5;
                    int novac = 340;
                    synchronized (r.hlebSync){
                        try {
                            while (r.hleb < hleb) {
                                r.hlebSync.wait();
                            }
                        } catch (InterruptedException ignored) {}
                        r.hleb -= hleb;
                    }
                    synchronized (r.potazSync){
                        try {
                            while (r.potaz < potaz) {
                                r.potazSync.wait();
                            }
                        } catch (InterruptedException ignored) {}
                        r.potaz -= potaz;
                    }
                    synchronized (r.novacSync){
                        r.novac += novac;
                    }
                } else {
                    int tofu = 1;
                    int povrce = 300;
                    int novac = 520;
                    synchronized (r.tofuSync){
                        try {
                            while (r.tofu < tofu) {
                                r.tofuSync.wait();
                            }
                        } catch (InterruptedException ignored) {}
                        r.tofu -= tofu;
                    }
                    synchronized (r.povrceSync){
                        try {
                            while (r.povrce < povrce) {
                                r.povrceSync.wait();
                            }
                        } catch (InterruptedException ignored) {}
                        r.povrce -= povrce;
                    }
                    synchronized (r.novacSync){
                        r.novac += novac;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Porudzbina gotov");
    }
}
