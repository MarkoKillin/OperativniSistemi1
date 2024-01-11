package mace;

import os.simulation.Application;
import os.simulation.Container;
import os.simulation.Item;
import os.simulation.Operation;
import os.simulation.Thread;

/*
 * U kuci na uglu zivi jedna starija gospodja sa svojih sedam macaka. Kako je
 * ona odvec stara i brzo se umara, vecinu dana provodi dremajuci na kaucu u
 * sobi. Za razliku od nje, njene macke se po ceo dan igraju. Ovu maciju igru
 * moze prekinuti samo jedna stvar a to je, naravno, glad. A posto su macke
 * uvek gladne, one stalno prekidaju igru i odlaze u kuhinju kako bi jele, i
 * potom nastavile da se igraju.
 *
 * Svaka macka ima dve omiljene igracke sa kojima se uvek igra. Macka se uvek
 * mora igrati sa obe svoje igracke istovremeno. Takodje, kada se igra sa nekom
 * igrackom, macka je ne deli sa drugim mackama. Ostale macke moraju da sace-
 * kaju da ona ode da jede.
 *
 * Azrael voli da se igra sa lopticom i gumenim misem, Garfildove omiljene igra-
 * cke su gumeni mis i klupce, Oliver obozava klupce i laznu zmiju, Silvesterove
 * igracke su lazna zmija i gazdaricina papuca, Tom mora da se igra sa gazdari-
 * cinom papucom i ukrasom za jelku, Feliksove omiljene igracke su ukras za jel-
 * ku i krpeni mis, dok igracke koje Cesko obozava su krpeni mis i loptica.
 *
 * Iskoristiti semafore i onemogućiti deljenje igracaka ali obezbediti nesmetanu
 * igru mackama.
 *
 * Dodatno ulepsati zabavu mackama, odnosno obezbediti im tri igracke za igru,
 * dve njihove omiljene i bilo koju slobodnu kao trecu.
 */
public class Mace extends Application {

    //private MaceLock ml = new MaceLock(7);
    private MaceSemaphore ml = new MaceSemaphore(7);
    //private MaceAtomic ml = new MaceAtomic(7);

    protected class Maca extends Thread {

        protected final int id = getID();

        @Override
        protected void step() {
            spava();
            try {
                ml.uzmi(id);
                try {
                    igraSe();
                } finally {
                    ml.vrati(id);
                }
            } catch (InterruptedException e) {
                stopGracefully();
            }
        }
    }

    // ------------------- //
    //    Sistemski deo    //
    // ------------------- //
    // Ne dirati kod ispod //
    // ------------------- //

    protected final String[] IMENA_MACA = {"Азраел", "Гарфилд", "Оливер", "Силвестер", "Том", "Феликс", "Чешко"};
    protected final String[] IMENA_IGR = {"Г. миш", "Клупче", "Л. змија", "Папуча", "Украс", "К. миш", "Лоптица"};
    protected final Container main = circle("Маце").color(WARM_GRAY);
    protected final Operation maca = init().name(IMENA_MACA).color(ROSE);
    protected final Operation igracka = init().name(IMENA_IGR).color(GREEN);
    protected final Operation spavanje = duration("3±2").color(AZURE).text("Спава").colorAfter(ROSE).textAfter("Чека");
    protected final Operation igranje = duration("3±1").color(CHARTREUSE).text("Игра се").colorAfter(CHARTREUSE).textAfter("Чека");

    protected void spava() {
        spavanje.performUninterruptibly();
    }

    protected void igraSe() {
        igranje.performUninterruptibly();
    }

    protected class Igracka extends Item {

        private Maca[] mace;
        private int max;

        public Igracka(int max, Maca... mace) {
            this.max = max;
            this.mace = mace;
            for (Maca maca : mace) {
                maca.addPropertyChangeListener(PROPERTY_TEXT, e -> azuriraj());
            }
            azuriraj();
        }

        private void azuriraj() {
            int broj = 0;
            for (Maca maca : mace) {
                if (igranje.getTextBefore().equals(maca.getText())) {
                    broj++;
                }
            }
            int slobodno = max - broj;
            if (slobodno > 0) {
                setColor(GREEN);
                setText("Слободно");
            } else if (slobodno == 0) {
                setColor(YELLOW);
                setText("Заузето");
            } else {
                setColor(RED);
                setText("Дели се");
            }
        }
    }

    @Override
    protected void initialize() {
        Maca[] mace = new Maca[IMENA_MACA.length];
        for (int i = 0; i < mace.length; i++) {
            mace[i] = new Maca();
        }
        Igracka[] igracke = new Igracka[IMENA_IGR.length];
        for (int i = 0; i < igracke.length; i++) {
            igracke[i] = new Igracka(1, mace[i], mace[(i + 1) % mace.length]);
        }
        for (int i = 0; i < mace.length; i++) {
            mace[i].setContainer(main);
            igracke[i].setContainer(main);
        }
        for (int i = 0; i < mace.length; i++) {
            mace[i].start();
        }
    }

    public static void main(String[] arguments) {
        launch("Игра маца");
    }
}
