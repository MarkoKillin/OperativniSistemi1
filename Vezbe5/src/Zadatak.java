/*
 * Micko je kupio pet kosilica za travu sa idejom da ih iznajmljuje i tako
 * zaradi nesto para.
 * 
 * Napisati program koji simulira jedan dan iznajmljivanja kosilica. Program
 * kreira 25 prekidivih i 25 neprekidivih musterija i pokrece ih. Posle 60
 * sekundi, glavna nit prekida ostale i po zavrsetku svih njih, stampa kolika
 * bi bila zarada. (5 poena)
 * 
 * Prekidive musterije, odmah po pokretanju, generisu nasumicnu duzinu vremena
 * iznajmljivanja izmedju 10 i 20 sekundi. Potom iznajmljuju kosilicu, koriste
 * je zadato vreme, i na kraju vracaju pre zavrsetka svog rada. Ako ih neko
 * prekine u toku cekanja na kosilicu ili u toku koriscenja iste, odmah
 * prekidaju svoj rad, pazeci da i u tom slucaju vrate kosilicu ako su je
 * iznajmili. (5 poena)
 * 
 * Neprekidive musterije su implementirane na isti nacin kao i prekidive, sa
 * jedinom razlikom da ne reaguju na prekide i uvek ce sacekati svoj red da
 * iznajme kosilicu i uvek ce pokositi svu travu koju su zamislili. (5 poena)
 * 
 * Sinhronizovati iznajmljivanje tako da se ne moze iznajmiti vise od 5 kosilica
 * u isto vreme. (5 poena)
 * 
 * Korektno voditi zaradu prilikom iznajmljivanja ako je cena 20 dinara po
 * sekundi. Takodje, izracunati i koliko bi bila zarada da je cena bila 50
 * dinara po sekundi. (5 poena)
 * 
 * Obratiti paznju na elegantnost i objektnu orijentisanost realizacije i stil
 * resenja. Za program koji se ne kompajlira, automatski se dobija 0 poena bez
 * daljeg pregledanja.
 */
public class Zadatak {
    public static void main(String[] args) {
        Prekidivi[] pr = new Prekidivi[25];
        Neprekidivi[] npr = new Neprekidivi[25];
        FirmaKosilica fk = new FirmaKosilica();
        for (int i = 0; i < 25; i++) {
            pr[i] = new Prekidivi(fk);
            pr[i].start();
            npr[i] = new Neprekidivi(fk);
            npr[i].start();
        }
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
        }

        System.out.println("\n\n\n\nGasi mesalicu");
        for (int i = 0; i < 25; i++) {
            if(pr[i] != null){
                pr[i].interrupt();

            }
        }

        for (int i = 0; i < 25; i++) {
            try {
                if(pr[i] != null)
                    pr[i].join();
            } catch (InterruptedException e){

            }
            try {
                if(pr[i] != null)
                    npr[i].join();
            } catch (InterruptedException e){

            }
        }

        System.out.println("Po 20 din: " + fk.getZarada(20));
        System.out.println("Po 50 din: " + fk.getZarada(50));

    }
}
