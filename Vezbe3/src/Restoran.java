public class Restoran {
    public int povrce = 0;
    public double potaz = 0;
    public int hleb = 0;
    public int tofu = 0;
    public int novac = 0;

    public final Object povrceSync = new Object();
    public final Object potazSync = new Object();
    public final Object hlebSync = new Object();
    public final Object tofuSync = new Object();
    public final Object novacSync = new Object();
}
