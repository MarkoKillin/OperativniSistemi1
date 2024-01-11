public enum Rang {
    KEC(1, "1"),
    DVA(2, "2"),
    TRI(3, "3"),
    CETIRI(4, "4"),
    PET(5, "5"),
    SEST(6, "6"),
    SEDAM(7, "7"),
    OSAM(8, "8"),
    DEVET(9, "9"),
    DESET(10, "10"),
    ZANDAR(12, "12"),
    KRALJICA(13, "13"),
    KRALJ(14, "14"),
    DZOKER(15, "15");

    private final int num;
    private final String text;

    private Rang(int num, String text) {
        this.text = text;
        this.num = num;
    }

    public int getBroj() {
        return num;
    }
}
