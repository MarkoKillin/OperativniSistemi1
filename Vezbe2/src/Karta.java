class Karta{

    public char rang;
    public String boja;

    public Karta(char rang, String boja) {
        this.rang = rang;
        this.boja = boja;
    }

    @Override
    public String toString() {
        return rang + boja;
    }


}
