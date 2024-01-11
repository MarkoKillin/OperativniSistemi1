import java.util.ArrayList;
import java.util.List;

/*
 * Napisati program koji kreira jednu Deda Mrazovu radionicu poklona i cetiri
 * vilenjaka. Vilenjaci koji rade u radionici se zovu Maglor, Mrazdalf,
 * Vetromir i Snegolas i definisani su kao zasebni procesi. Prva dva vilenjaka
 * su definisana pomocu klase Thread kao korisnicki procesi, a druga dva pomocu
 * interfejsa Runnable kao pozadinski procesi. (5 poena)
 * 
 * Radni dan prva dva vilenjaka se sastoji samo od proizvodnje igracaka, pri
 * cemu svako od njih moze u toku dana da napravi po 50 igracaka, posle cega
 * zavrsavaju svoj radni dan. (5 poena)
 * 
 * Druga dva vilenjaka po ceo dan pakuju poklone u koje stavljaju po jednu
 * igracku (pozivajuci metod Igracka::upakuj). (5 poena)
 * 
 * Sinhronizovati klasu Radionica tako da se ni u kom slucaju ne izgube
 * igracke. Takodje, blokirati vilenjaka koji pokusa da upakuje poklon bez
 * igracke ili napravi novu igracku ako je radionica puna. Na stolu u radionici
 * moze da stane najvise 10 igracaka. Odblokirati vilenjake cim se stvore
 * uslovi za nastavak njihovog rada. (10 poena)
 * 
 * Obratiti paznju na elegantnost i objektnu orijentisanost realizacije i stil
 * resenja. Za program koji se ne kompajlira, automatski se dobija 0 poena bez
 * daljeg pregledanja.
 */
public class Program {
	
	public static void main(String[] args) throws InterruptedException {
		Radionica radionica = new Radionica();
		Igracka igracka = new Igracka();
		
		Radnici maglor = new Radnici(radionica, igracka);
		maglor.setName("Maglor");
		maglor.start();
		
		Radnici mrazdalf = new Radnici(radionica, igracka);
		mrazdalf.setName("Mrazdalf");
		mrazdalf.start();
		
		Thread vetromir = new Thread(new Dekor(radionica, igracka));
		vetromir.setDaemon(true);
		vetromir.setName("Vetromir");
		vetromir.start();
		
		Thread snegolas = new Thread(new Dekor(radionica, igracka));
		snegolas.setDaemon(true);
		snegolas.setName("Snegolas");
		snegolas.start();
		
		Thread.sleep(45000);
		System.out.println("Ostalo je " + radionica.igracke.size() + " igracaka");
	}

}

class Radnici extends Thread{
	Radionica radionica;
	Igracka igracka;
	
	public Radnici(Radionica radionica, Igracka igracka) {
		this.radionica = radionica;
		this.igracka = igracka;
		setDaemon(false);
	}
	
	@Override
	public void run() {
		int brojac = 0;
		while(!interrupted() && brojac < 50) {
			try {
				Igracka nova = igracka.napravi();
				radionica.stavi(nova);
				brojac++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Dekor implements Runnable{
	Radionica radionica;
	Igracka igracka;
	
	public Dekor(Radionica radionica, Igracka igracka) {
		this.radionica = radionica;
		this.igracka = igracka;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!Thread.interrupted()) {
			try {
				Igracka uzeta = radionica.uzmi();
				igracka.upakuj(uzeta);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

class Radionica{
	List<Igracka> igracke = new ArrayList<Igracka>();
	
	public synchronized void stavi(Igracka igracka) throws InterruptedException {
		while(igracke.size() == 10) {
			wait();
		}
		notifyAll();
		igracke.add(igracka);
	}
	
	public synchronized Igracka uzmi() throws InterruptedException {
		while(igracke.size() == 0) {
			wait();
		}
		notifyAll();
		return igracke.remove(0);
	}
}

class Igracka{
	
	String opis;
	
	String[] vrsteIgracaka = {"Medved", "Lisica", "Vuk", "Nidzo", "Strela"};
	
	public Igracka() {
		this.opis = vrsteIgracaka[(int) ((vrsteIgracaka.length - 1) * Math.random())];
	}
	
	public Igracka napravi() {
		Igracka nova = new Igracka();
		System.out.println(Thread.currentThread().getName() + " je napravio igracku " + opis);
		try {
			Thread.sleep((long) (500 + 500*Math.random()));
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		return nova;
	}
	
	public void upakuj(Igracka igracka) {
		System.out.println(Thread.currentThread().getName() + " je upakovao igracku " + igracka);
		try {
			Thread.sleep((long) (500 + 700*Math.random()));
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return opis;
	}
}