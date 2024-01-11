import os.simulation.Thread;
import os.simulation.*;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * U frizerskom salonu rade dva berberina. Ako nema musterija, berber sedi u
 * svojoj stolici i spava. Kada musterija udje, ako neki od berbera spava, budi
 * ga, seda za stolicu i berber je sisa. Ako su svi berberi zauzeti, musterija
 * seda za stolicu u cekaonici i ceka da se oslobodi neko od berbera. Kada
 * berber zavrsi sisanje musterije, ako ima musterija koje cekaju, krece da
 * sisa jednu od musterija koje cekaju. Ako nema vise musterija koje cekaju,
 * berber seda u svoju stolicu i spava.
 * 
 * Implementirati sinhronizaciju ove dve vrste procesa kako je opisano.
 */
class BerbernicaLock {
	private Lock l = new ReentrantLock();
	private Condition berberin = l.newCondition();
	private Condition musterija = l.newCondition();
	private int brB = 0;
	private int brM = 0;

	public void zauzmiBerbera(){
		l.lock();
		try {
			while(brB == 2) {
				berberin.awaitUninterruptibly();
			}
			brB++;
		} finally {
			l.unlock();
		}
	}
	public void oslobodiBerbera(){
		l.lock();
		try {
			brB--;
			berberin.signalAll();
		} finally {
			l.unlock();
		}
	}
	public void zauzmiMusteriju(){
		l.lock();
		try {
			while(brM == 2){
				musterija.awaitUninterruptibly();
			}
			brM++;
		} finally {
			l.unlock();
		}
	}
	public void oslobodiMusteriju(){
		l.lock();
		try {
			brM--;
			musterija.signalAll();
		} finally {
			l.unlock();
		}
	}
}

public class UspavaniBerberi2 extends Application {
	private BerbernicaLock bs = new BerbernicaLock();
	@AutoCreate(2)
	protected class Berber extends Thread {

		@Override
		protected void step() {
			bs.zauzmiMusteriju();
			try {
				sisa();
			} finally {
				bs.oslobodiMusteriju();
			}
		}
	}

	@AutoCreate
	protected class Musterija extends Thread {

		@Override
		protected void run() {
			bs.zauzmiBerbera();
			try {
				sisaSe();
			} finally {
				bs.oslobodiBerbera();
			}
		}
	}

	// ------------------- //
	//    Sistemski deo    //
	// ------------------- //
	// Ne dirati kod ispod //
	// ------------------- //

	protected final Container cekaonica = box("Чекаоница");
	protected final Container stolice   = box("Салон");
	protected final Container main      = column(cekaonica, stolice);
	protected final Operation berber    = init().name("Бербер %d").color(ROSE).text("Спава").container(stolice).update(this::azuriraj);
	protected final Operation musterija = duration("1±1").name("Мушт. %d").color(AZURE).text("Чека").container(cekaonica).update(this::azuriraj);
	protected final Operation sisanjeB  = duration("7").text("Шиша").update(this::azuriraj);
	protected final Operation sisanjeM  = duration("7").text("Шиша се").container(stolice).colorAfter(CHARTREUSE).textAfter("Ошишао се").update(this::azuriraj);

	protected void sisa() {
		sisanjeB.performUninterruptibly();
	}

	protected void sisaSe() {
		sisanjeM.performUninterruptibly();
	}

	protected void azuriraj(Item item) {
		long brB1 = 0;
		long brB2 = 0;
		for (Berber t : stolice.getItems(Berber.class)) {
			if (sisanjeB.getTextBefore().equals(t.getText())) {
				brB1++;
			} else {
				brB2++;
			}
		}
		long brM1 = stolice.stream(Musterija.class).count();
		long brM2 = cekaonica.stream(Musterija.class).count();
		cekaonica.setText(String.format("%d", brM2));
		stolice.setText(String.format("%d : %d", brB1, brM1));
		long razlika = brB1 - brM1;
		if (brB2 > 0 && brM2 > 0) {
			cekaonica.setColor(MAROON);
		} else {
			cekaonica.setColor(OLIVE);
		}
		if (razlika == 0) {
			stolice.setColor(ARMY);
		} else {
			stolice.setColor(MAROON);
		}
	}

	@Override
	protected void initialize() {
		azuriraj(null);
	}

	public static void main(String[] arguments) {
		launch("Успавани бербери");
	}
}
