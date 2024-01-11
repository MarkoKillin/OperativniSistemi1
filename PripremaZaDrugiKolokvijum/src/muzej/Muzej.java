package muzej;

import os.simulation.Application;
import os.simulation.AutoCreate;
import os.simulation.Container;
import os.simulation.Item;
import os.simulation.Operation;
import os.simulation.Thread;

/*
 * U okviru maturske ekskurzije, za djake iz tri evropske drzave - Engleske,
 * Nemacke i Italije - je organizovan obilazak muzeja Louvre u Parizu. Sve tri
 * grupe djaka borave neko vreme ispred muzeja, nakon cega ulaze u muzej i uzi-
 * vaju u izlozenim umetnickim delima. Medjutim, u jednom momentu samo djaci
 * jedne drzave mogu boraviti u muzeju, jer bi se u suprotnom njihovi vodici
 * morali nadvikivati i niko nista ne bi cuo.
 *
 * Sinhronizovati boravak djaka u muzeju tako da u jednom momentu samo jedna
 * grupa bude unutar muzeja. Svaki djak je predstavljen jednom niti cija klasa
 * odredjuje drzavu iz koje on dolazi.
 */
public class Muzej extends Application {
    //private MuzejLock ml = new MuzejLock();
    //private MuzejSemaphore ms = new MuzejSemaphore();
    private MuzejAtomic mt = new MuzejAtomic();

    @AutoCreate(8)
    protected class Englez extends Thread {

        @Override
        protected void step() {
            odmara();
            try {
                //ml.zakljucajEnglezi();
                //ms.zakljucajEnglezi();
                mt.zakljucajEnglezi();
                try {
                    obilazi();
                } finally {
                    //ml.otkljucajEnglezi();
                    //ms.otkljucajEnglezi();
                    mt.otkljucajEnglezi();
                }
            } catch (InterruptedException e) {
                stopGracefully();
            }
        }
    }

    @AutoCreate(8)
    protected class Nemac extends Thread {

        @Override
        protected void step() {
            odmara();
            try {
                //ml.zakljucajNemci();
                //ms.zakljucajNemci();
                mt.zakljucajNemci();
                try {
                    obilazi();
                } finally {
                    //ml.otkljucajNemci();
                    //ms.otkljucajNemci();
                    mt.otkljucajNemci();
                }
            } catch (InterruptedException e) {
                stopGracefully();
            }
        }
    }

    @AutoCreate(8)
    protected class Italijan extends Thread {

        @Override
        protected void step() {
            odmara();
            try {
                //ml.zakljucajItalijani();
                //ms.zakljucajItalijani();
                mt.zakljucajItalijani();
                try {
                    obilazi();
                } finally {
                    //ml.otkljucajItalijani();
                    //ms.otkljucajItalijani();
                    mt.otkljucajItalijani();
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

    protected final Container englezi = box("Енглези").color(MAROON);
    protected final Container nemci = box("Немци").color(ROYAL);
    protected final Container italijani = box("Италијани").color(ARMY);
    protected final Container muzej = box("Музеј").color(NAVY);
    protected final Container main = column(row(englezi, nemci, italijani), muzej);
    protected final Operation englez = init().container(englezi).name("Енглез %d").color(RED);
    protected final Operation nemac = init().container(nemci).name("Немац %d").color(PURPLE);
    protected final Operation italijan = init().container(italijani).name("Италијан %d").color(GREEN);

    protected final Operation odmaranje = duration("7±2").text("Одмара").textAfter("Чека");
    protected final Operation obilazak = duration("5±2").text("Обилази").container(muzej).textAfter("Обишао").update(this::azuriraj);

    protected void odmara() {
        odmaranje.performUninterruptibly();
    }

    protected void obilazi() {
        obilazak.performUninterruptibly();
    }

    protected void azuriraj(Item item) {
        long brE = muzej.stream(Englez.class).count();
        long brN = muzej.stream(Nemac.class).count();
        long brI = muzej.stream(Italijan.class).count();
        muzej.setText(String.format("%d / %d / %d", brE, brN, brI));
        if (brE == 0 && brN == 0 && brI == 0) {
            muzej.setColor(NAVY);
        } else if (brE > 0 && brN == 0 && brI == 0) {
            muzej.setColor(MAROON);
        } else if (brE == 0 && brN > 0 && brI == 0) {
            muzej.setColor(ROYAL);
        } else if (brE == 0 && brN == 0 && brI > 0) {
            muzej.setColor(ARMY);
        } else {
            muzej.setColor(CARBON);
        }
    }

    @Override
    protected void initialize() {
        azuriraj(null);
    }

    public static void main(String[] a) {
        launch("Музеј");
    }
}
