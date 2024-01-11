public class Karta {
    private Boja boja;
    private Rang rang;

    public Karta(Boja boja, Rang rang) {
        this.boja = boja;
        this.rang = rang;
    }

    public Boja getBoja() {
        return boja;
    }

    public void setBoja(Boja boja) {
        this.boja = boja;
    }

    public Rang getRang() {
        return rang;
    }

    public void setRang(Rang rang) {
        this.rang = rang;
    }
}
