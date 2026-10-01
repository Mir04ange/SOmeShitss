import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		DomaciAsistent a = new DomaciAsistent(scanner);
		while (true) {
			System.out.println("\n--- Domácí Asistent Menu ---");
			System.out.println("*".repeat(80));
			System.out.println(
				"1 Přidat zařízení | 2 Odebrat | 3 Vypsat zařízení | 4 Zapnout všechna | 5 Vypnout všechna"
			);
			System.out.println("6 Přehrát na všech službách | 7 Ovládání termostatu | 8 Konec");
			System.out.println("9 Aktivní zařízení a služby | 10 Přejmenovat zařízení | 11 Statistika");
			System.out.println("12 Nastavit limit příkonu | 13 Aktuální spotřeba | 14 Úsporný režim");
			System.out.println("15 Vytvořit scénář | 16 Vypsat scénáře | 17 Spustit scénář | 18 Odstranit scénář");
			System.out.println("*".repeat(80));
			System.out.print("Vyberte možnost: ");
			int volba = scanner.nextInt();
			scanner.nextLine();
			switch (volba) {
				case 1:
					a.pridejZarizeni();
					break;
				case 2:
					a.odeberZarizeni();
					break;
				case 3:
					a.vypisZarizeni();
					break;
				case 4:
					a.zapniVse();
					break;
				case 5:
					a.vypniVse();
					break;
				case 6:
					a.prehratNaVsechSluzbach();
					break;
				case 7:
					a.ovladaniTermostatu();
					break;
				case 9:
					a.vypisAktivni();
					break;
				case 10:
					a.prepisNazevZarizeni();
					break;
				case 11:
					a.vypisStatistiku();
					break;
				case 12:
					a.nastavLimitPrikonu();
					break;
				case 13:
					a.vypisSpotrebu();
					break;
				case 14:
					a.uspornyRezim();
					break;
				case 15:
					a.vytvorScenar();
					break;
				case 16:
					a.vypisScenare();
					break;
				case 17:
					a.spustScenar();
					break;
				case 18:
					a.odstranScenar();
					break;
				case 8:
					System.out.println("Konec programu.");
					return;
				default:
					System.out.println("Neplatná volba.");
			}
			System.out.println("_".repeat(80));
		}
	}
}
