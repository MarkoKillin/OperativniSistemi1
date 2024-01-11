import java.util.ArrayList;
import java.util.List;

/*
 * Napisati program koji kreira jednu praznu gajbu piva i tri gradjevinska
 * radnika. Dva gradjevinska radnika se zovu Joza i Muhi i predstavljeni su
 * zasebnim procesima definisanim pomocu klase Thread. Treci radnik se zove
 * Gule, najmladji je od njih trojice i definisan je kao pozadinski proces
 * pomocu interfejsa Runnable. (5 poena)
 * 
 * Radni dan starije dvojce radnika se sastoji samo od ispijanja piva u
 * hladovini, pri cemu svako od njih moze u toku dana da popije po 50 piva,
 * posle cega zavrsava svoj "radni" dan. (5 poena)
 * 
 * Posto je najmladji, Gule po ceo dan odlazi do obliznjeg diskonta i kupuje
 * pivo drugoj dvojci radnika. Oni mu za ovo, naravno, daju nesto novca, a kako
 * diskont stalno nudi razne akcije, Gule svaki put donese razlicit broj flasa
 * piva. Ovo je vec dato i potrebno je samo pozvati metod Pivo::kupi. (5 poena)
 * 
 * Sinhronizovati klasu Gajba tako da se ni u kom slučaju ne izgubi ni jedno
 * pivo. Takodje, blokirati radnika koji pokusa da uzme pivo iz prazne gajbe
 * ili stavi pivo u punu gajbu. U gajbu može stati najvise 12 piva. Odblokirati
 * radnike cim se stvore uslovi za nastavak njihovog "rada". (10 poena)
 * 
 * Obratiti paznju na elegantnost i objektnu orijentisanost realizacije i stil
 * resenja. Za program koji se ne kompajlira, automatski se dobija 0 poena bez
 * daljeg pregledanja.
 */

public class Program {
	public static void main(String[] args) {
		Gajba gajba = new Gajba();
		Pivo pivo = new Pivo();
		
		Radnik joza = new Radnik(gajba, pivo);
		joza.setName("Joza");
		joza.start();
		
		Radnik muhi = new Radnik(gajba, pivo);
		muhi.setName("Muhi");
		muhi.start();
		
		Thread gule = new Thread(new Gule(gajba, pivo));
		gule.setDaemon(true);
		gule.setName("Gule");
		gule.start();	
	}
}

class Radnik extends Thread{
	Gajba gajba;
	Pivo pivo;
	
	public Radnik(Gajba gajba, Pivo pivo) {
		this.gajba = gajba;
		this.pivo = pivo;
		setDaemon(false);
	}
	
	@Override
	public void run() {
		int brojac = 0;
		while(!interrupted() && brojac < 50) {
			Pivo pom;
			try {
				pom = gajba.uzmi();
				pom.ispij();
				brojac++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + " je zavrsio radni dan");
	}
}


class Gule implements Runnable{
	Gajba gajba;
	Pivo pivo;
	
	public Gule(Gajba gajba, Pivo pivo) {
		this.gajba = gajba;
		this.pivo = pivo;
	}
	@Override
	public void run() {
		while(!Thread.interrupted()) {
			Pivo[] kupljena = pivo.kupi();
			for(int i =0; i<kupljena.length; i++) {
				try {
					gajba.stavi(kupljena[i]);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}

class Gajba {
	List<Pivo> piva = new ArrayList<Pivo>();
	
	public synchronized void stavi(Pivo pivo) throws InterruptedException {
		while(piva.size() == 12) {
			wait();
		}
		piva.add(pivo);
		notifyAll();
	}
	
	public synchronized Pivo uzmi() throws InterruptedException {
		while(piva.size() == 0) {
			wait();
		}
		notifyAll();
		return piva.remove(0);
	}
}

class Pivo {
	private String opis;
	
	private String[] vrstePiva = {"Niksicko", "Zajecarsko", "Jelen", "Tamno Niksicko", "Tamno Zajecarsko", "Tamno Jelen"};
	
	public Pivo() {
		this.opis = vrstePiva[(int) ((vrstePiva.length - 1) * Math.random())];
	}
	
	public void ispij() {
		System.out.println(Thread.currentThread().getName() + " je ispio " + opis);
		try {
			Thread.sleep((long) (500 + 300 * Math.random()));
		}catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	public Pivo[] kupi() {
		int brPiva = (int) (2 + 8 * Math.random());
		Pivo[] pomocna = new Pivo[brPiva];
		for(int i = 0; i<brPiva; i++) {
			pomocna[i] = new Pivo();
		}
		System.out.println(Thread.currentThread().getName() + " kupio je " + brPiva + " piva");
		try {
			Thread.sleep((long) (500 + 500 * Math.random()));
		}catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return pomocna;
	}
	
}