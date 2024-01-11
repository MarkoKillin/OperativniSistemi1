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

	public static void main(String[] args) throws InterruptedException {
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
		
		Thread.sleep(35000);
		System.out.println("Ostalo je " +gajba.brPiva + " piva");
	}
}

class Radnik extends Thread {
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
		while (!interrupted() && brojac < 50) {
			try {
				gajba.uzmi();
				pivo.ispij();
				brojac++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + " je zavrsio smenu");
	}
}

class Gule implements Runnable {
	Gajba gajba;
	Pivo pivo;

	public Gule(Gajba gajba, Pivo pivo) {
		this.gajba = gajba;
		this.pivo = pivo;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.interrupted()) {
			try {
				int n = pivo.kupi();
				gajba.stavi(n);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Gajba {
	int brPiva = 0;

	public synchronized void stavi(int n) throws InterruptedException {
		while (brPiva + n > 12) {
			wait();
		}
		notifyAll();
		brPiva += n;
	}

	public synchronized void uzmi() throws InterruptedException {
		while (brPiva == 0) {
			wait();
		}
		notifyAll();
		brPiva--;
	}
}

class Pivo {
	public int kupi() {
		int n = (int) (1 + 5 * Math.random());
		System.out.println(Thread.currentThread().getName() + " je kupio " + n + " piva");
		try {
			Thread.sleep((long) (500 + 5 * Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return n;
	}

	public void ispij() {
		System.out.println(Thread.currentThread().getName() + " je ispio pivo");
		try {
			Thread.sleep((long) (500 + 100 * Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}