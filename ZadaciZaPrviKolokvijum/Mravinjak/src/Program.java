/*
 * Napisati program koji kreira jedan novi mravinjak i 20 mrava predstavljenih
 * zasebnim pozadinskim procesima. Prvih 15 mrava su radnici, dok su preostalih
 * 5 zidari, i zovu se "Mrav 1", "Mrav 2"... "Mrav 20". Program potom ceka 30
 * sekundi posle cega stampa velicinu mravinjaka i zavrsava rad. (5 poena)
 * 
 * Mravi radnici ceo dan skupljaju hranu (pomocu metoda Priroda::nadjiHranu) i
 * donose je u mravinjak. (5 poena)
 * 
 * Mravi zidari dan provode u mravinjaku i stalno ga prosiruju (pomocu metoda
 * Priroda::prosiriMravinjak). Prosirivanje mravinjaka zahteva puno energije i
 * zidari pre ovoga moraju da pojedu 25 grama hrane. (5 poena)
 * 
 * Sinhronizovati klasu Mravinjak tako da se ni u kom slučaju ne izgubi hrana.
 * Takodje, blokirati mrava koji pokusa da jede hranu ako je nema dovoljno ili
 * da doda hranu ako je mravinjak pun. U mravinjak velicine 1 moze stati 50
 * grama hrane, u mravinjak velicine 2 moze stati 60 grama, u mravinjak velicine
 * 3 moze stati 70 grama, itd. Odblokirati mrave cim se stvore uslovi za
 * nastavak rada. (10 poena)
 * 
 * Obratiti paznju na elegantnost i objektnu orijentisanost realizacije i stil
 * resenja. Za program koji se ne kompajlira, automatski se dobija 0 poena bez
 * daljeg pregledanja.
 */

public class Program {

	public static void main(String[] args) throws InterruptedException {
		Priroda priroda = new Priroda();
		Mravinjak mravinjak = new Mravinjak();

		for (int i = 0; i < 15; i++) {
			MravRadnik radnik = new MravRadnik(priroda, mravinjak);
			radnik.setName("Mrav " + i);
			radnik.start();

			if (i >= 9) {
				int j = i + 6;
				Thread zidar = new Thread(new MravZidar(priroda, mravinjak));
				zidar.setDaemon(true);
				zidar.setName("Mrav " + j);
				zidar.start();
			}
		}
		Thread.sleep(30000);
		System.out.println(mravinjak.velicina);
	}
}

class MravRadnik extends Thread {
	Priroda priroda;
	Mravinjak mravinjak;

	public MravRadnik(Priroda priroda, Mravinjak mravinjak) {
		this.priroda = priroda;
		this.mravinjak = mravinjak;
		setDaemon(true);
	}

	@Override
	public void run() {
		while (!interrupted()) {
			try {
				int hrana = priroda.pronadjiHranu();
				mravinjak.stavi(hrana);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class MravZidar implements Runnable {
	Priroda priroda;
	Mravinjak mravinjak;

	public MravZidar(Priroda priroda, Mravinjak mravinjak) {
		this.priroda = priroda;
		this.mravinjak = mravinjak;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				mravinjak.uzmi(25);
				priroda.prosiri();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Mravinjak {
	int velicina = 1;
	int kolicinaHrane = 0;

	public synchronized void stavi(int kolicina) throws InterruptedException {
		if (kolicinaHrane + kolicina > (40 + velicina * 10)) {
			wait();
		}
		notifyAll();
		kolicinaHrane = kolicinaHrane + kolicina;
	}

	public synchronized void uzmi(int kolicina) throws InterruptedException {
		if (kolicinaHrane - kolicina < 0) {
			wait();
		}
		notifyAll();
		kolicinaHrane = kolicinaHrane - kolicina;
		velicina++;
	}
}

class Priroda {

	public void prosiri() {
		try {
			Thread.sleep((long) (1000 + 3000 * Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " je prosirio mravinjak");
	}

	public int pronadjiHranu() {
		int hrana = (int) (1 + 5 * Math.random());
		System.out.println(Thread.currentThread().getName() + " je pronasao " + hrana + " hrane");
		try {
			Thread.sleep((long) (1000 + 1000 * Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return hrana;
	}
}