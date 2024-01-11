public class Igrac extends Thread{
    private Karta k;

    @Override
    public void run() {
        synchronized (this){
            while(k == null) {
                try {
                    wait();
                } catch (InterruptedException e) {

                }
            }
        }
        System.out.println(k);
    }

    public void putCard(Karta k){
        this.k = k;
        synchronized (this){
            notify();
        }
    }
}
