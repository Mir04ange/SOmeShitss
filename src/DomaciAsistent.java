import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class DomaciAsistent {

	private final List<ISmartDevice> zarizeni = new ArrayList<>();
	private final List<IStreamingService> sluzby = new ArrayList<>();
	private final List<Scenario> scenare = new ArrayList<>();
	private final Scanner scanner;
	private double maximalniPrikon = Double.POSITIVE_INFINITY;

	public DomaciAsistent(Scanner scanner) {
		this.scanner = scanner;
		sluzby.add(new Netflix());
		sluzby.add(new Spotify());
		sluzby.add(new YouTube());
	}

	public void pridejZarizeni() {
		System.out.println("Vyberte typ zařízení:\n1. SmartLight\n2. SmartThermostat");
		int typ = nactiInt("Typ: ");
		System.out.print("Zadejte název zařízení: ");
		String nazev = scanner.nextLine();
		System.out.print("Zadejte příkon ve wattech (prázdné = výchozí): ");
		String p = scanner.nextLine();
		double prikon = p.trim().isEmpty() ? (typ == 1 ? 60 : 1500) : Double.parseDouble(p);
		int priorita = nactiRozsah("Zadejte prioritu 1–10 (prázdné = 5): ", 5, 1, 10);
		if (typ == 1) zarizeni.add(new SmartLight(nazev, prikon, priorita)); else if (typ == 2) {
			double t = nactiDouble("Zadejte počáteční teplotu: ");
			zarizeni.add(new SmartThermostat(nazev, t, prikon, priorita));
		} else System.out.println("Neplatný typ zařízení.");
	}

	public void odeberZarizeni() {
		ISmartDevice z = najdiZarizeni("název zařízení, které chcete odebrat");
		if (z != null) {
			zarizeni.remove(z);
			System.out.println("Zařízení bylo odebráno.");
		}
	}

	public void vypisZarizeni() {
		System.out.println("Seznam spravovaných zařízení:");
		if (zarizeni.isEmpty()) System.out.println("(žádná zařízení)");
		for (ISmartDevice z : zarizeni) System.out.println(z);
	}

	public void prepisNazevZarizeni() {
		ISmartDevice z = najdiZarizeni("zařízení, jehož název chcete změnit");
		if (z != null) {
			System.out.print("Nový název: ");
			z.setNazev(scanner.nextLine());
			System.out.println("Název byl změněn.");
		}
	}

	public void zapniVse() {
		for (ISmartDevice z : zarizeni) zapni(z);
	}

	public void vypniVse() {
		for (ISmartDevice z : zarizeni) z.vypni();
	}

	public void zapniJedno(ISmartDevice z) {
		zapni(z);
	}

	private void zapni(ISmartDevice z) {
		if (z.stav().equals("zapnuto")) {
			System.out.println(z.getNazev() + " je již zapnuto.");
			return;
		}
		if (aktualniPrikon() + z.getPrikon() > maximalniPrikon) {
			System.out.println(
				"Upozornění: zařízení " + z.getNazev() + " nelze zapnout, překročil by se limit příkonu."
			);
			return;
		}
		z.zapni();
	}

	public void prehratNaVsechSluzbach() {
		System.out.print("Zadejte název obsahu: ");
		String titul = scanner.nextLine();
		for (IStreamingService s : sluzby) s.prehrat(titul);
	}

	public void ovladaniTermostatu() {
		System.out.println("Seznam termostatů:");
		boolean nalezen = false;
		for (ISmartDevice z : zarizeni) {
			if (z instanceof SmartThermostat) {
				System.out.println(z);
				nalezen = true;
			}
		}
		if (!nalezen) {
			System.out.println("(žádné termostaty)");
			return;
		}
		System.out.print("Zadejte název termostatu: ");
		String n = scanner.nextLine();
		for (ISmartDevice z : zarizeni) {
			if (z instanceof SmartThermostat && z.getNazev().equalsIgnoreCase(n)) {
				((SmartThermostat) z).nastavTeplotu(nactiDouble("Nová teplota: "));
				return;
			}
		}
		System.out.println("Termostat nebyl nalezen.");
	}

	public void vypisAktivni() {
		System.out.println("Zapnutá zařízení:");
		for (ISmartDevice z : zarizeni) if (z.stav().equals("zapnuto")) System.out.println(z);
		System.out.println("Spuštěné služby:");
		for (IStreamingService s : sluzby) if (s.prehrava()) System.out.println(
			s.getNazev() + " (spuštění " + s.getPocetSpusteni() + ")"
		);
	}

	public void vypisStatistiku() {
		ISmartDevice zdroj = zarizeni
			.stream()
			.max(Comparator.comparingInt(ISmartDevice::getPocetSpusteni))
			.orElse(null);
		IStreamingService sluzba = sluzby
			.stream()
			.max(Comparator.comparingInt(IStreamingService::getPocetSpusteni))
			.orElse(null);
		int soucetZ = zarizeni.stream().mapToInt(ISmartDevice::getPocetSpusteni).sum();
		int soucetS = sluzby.stream().mapToInt(IStreamingService::getPocetSpusteni).sum();
		System.out.println(
			"Statistika:\nNejpoužívanější zařízení: " +
			(zdroj == null ? "žádné" : zdroj.getNazev() + " (" + zdroj.getPocetSpusteni() + ")")
		);
		System.out.println(
			"Nejpoužívanější služba: " +
			(sluzba == null ? "žádná" : sluzba.getNazev() + " (" + sluzba.getPocetSpusteni() + ")")
		);
		System.out.println("Součet spuštění všech zařízení: " + soucetZ);
		System.out.println("Součet spuštění všech služeb: " + soucetS);
	}

	public void nastavLimitPrikonu() {
		maximalniPrikon = nactiDouble("Maximální povolený příkon domácnosti ve wattech: ");
		System.out.println("Limit nastaven na " + maximalniPrikon + " W.");
	}

	public void vypisSpotrebu() {
		double aktualni = aktualniPrikon();
		double rezerva = Double.isInfinite(maximalniPrikon) ? 0 : Math.max(0, maximalniPrikon - aktualni);
		System.out.println(
			"Aktuální spotřeba: " +
			aktualni +
			" W\nZbývající rezerva: " +
			(Double.isInfinite(maximalniPrikon) ? "neomezená" : rezerva + " W")
		);
		ISmartDevice max = zarizeni.stream().max(Comparator.comparingDouble(ISmartDevice::getPrikon)).orElse(null);
		System.out.println(
			"Největší příkon má: " + (max == null ? "žádné zařízení" : max.getNazev() + " (" + max.getPrikon() + " W)")
		);
	}

	private double aktualniPrikon() {
		return zarizeni.stream().filter(z -> z.stav().equals("zapnuto")).mapToDouble(ISmartDevice::getPrikon).sum();
	}

	private List<ISmartDevice> nejlepsiKombinace;
	private int nejlepsiPriorita;
	private double nejlepsiKombinacePrikon;

	public void uspornyRezim() {
		List<ISmartDevice> zapnuta = new ArrayList<>();
		for (ISmartDevice z : zarizeni) if (z.stav().equals("zapnuto")) zapnuta.add(z);
		nejlepsiKombinace = new ArrayList<>();
		nejlepsiPriorita = -1;
		nejlepsiKombinacePrikon = Double.POSITIVE_INFINITY;
		hledejKombinaci(zapnuta, 0, new ArrayList<ISmartDevice>(), 0, 0);
		for (ISmartDevice z : zapnuta) if (!nejlepsiKombinace.contains(z)) z.vypni();
		double suma = nejlepsiKombinace.stream().mapToDouble(ISmartDevice::getPrikon).sum();
		System.out.println(
			"Úsporný režim ponechal zapnuto " + nejlepsiKombinace.size() + " zařízení (" + suma + " W)."
		);
	}

	private void hledejKombinaci(
		List<ISmartDevice> kandidati,
		int index,
		List<ISmartDevice> vybrana,
		int priorita,
		double prikon
	) {
		if (prikon > maximalniPrikon) return;
		if (index == kandidati.size()) {
			if (priorita > nejlepsiPriorita || (priorita == nejlepsiPriorita && prikon < nejlepsiKombinacePrikon)) {
				nejlepsiPriorita = priorita;
				nejlepsiKombinacePrikon = prikon;
				nejlepsiKombinace = new ArrayList<>(vybrana);
			}
			return;
		}
		hledejKombinaci(kandidati, index + 1, vybrana, priorita, prikon);
		ISmartDevice z = kandidati.get(index);
		vybrana.add(z);
		hledejKombinaci(kandidati, index + 1, vybrana, priorita + z.getPriorita(), prikon + z.getPrikon());
		vybrana.remove(vybrana.size() - 1);
	}

	public void vytvorScenar() {
		System.out.print("Název scénáře: ");
		Scenario s = new Scenario(scanner.nextLine());
		while (true) {
			System.out.println("1 Zapnout zařízení, 2 Vypnout zařízení, 3 Přehrát službu, 4 Zastavit službu, 0 Hotovo");
			int volba = nactiInt("Akce: ");
			if (volba == 0) break;
			if (volba == 1 || volba == 2) {
				ISmartDevice z = najdiZarizeni("zařízení pro akci");
				if (z != null) {
					if (volba == 1) s.pridejAkci("Zapnout " + z.getNazev(), () -> zapni(z)); else s.pridejAkci(
						"Vypnout " + z.getNazev(),
						z::vypni
					);
				}
			} else if (volba == 3 || volba == 4) {
				IStreamingService sl = najdiSluzbu();
				if (sl != null) {
					if (volba == 3) {
						System.out.print("Titul: ");
						String t = scanner.nextLine();
						s.pridejAkci("Přehrát " + sl.getNazev(), () -> sl.prehrat(t));
					} else s.pridejAkci("Zastavit " + sl.getNazev(), sl::stop);
				}
			}
		}
		scenare.add(s);
	}

	public void vypisScenare() {
		if (scenare.isEmpty()) System.out.println("(žádné scénáře)");
		for (Scenario s : scenare) s.vypis();
	}

	public void spustScenar() {
		Scenario s = najdiScenar();
		if (s != null) s.spust();
	}

	public void odstranScenar() {
		Scenario s = najdiScenar();
		if (s != null) {
			scenare.remove(s);
			System.out.println("Scénář odstraněn.");
		}
	}

	private ISmartDevice najdiZarizeni(String prompt) {
		vypisZarizeni();
		System.out.print("Zadejte " + prompt + ": ");
		String n = scanner.nextLine();
		for (ISmartDevice z : zarizeni) if (z.getNazev().equalsIgnoreCase(n)) return z;
		System.out.println("Zařízení nebylo nalezeno.");
		return null;
	}

	private IStreamingService najdiSluzbu() {
		System.out.println("Služby:");
		for (IStreamingService s : sluzby) System.out.println(s.getNazev());
		System.out.print("Zadejte službu: ");
		String n = scanner.nextLine();
		for (IStreamingService s : sluzby) if (s.getNazev().equalsIgnoreCase(n)) return s;
		System.out.println("Služba nebyla nalezena.");
		return null;
	}

	private Scenario najdiScenar() {
		vypisScenare();
		System.out.print("Zadejte název scénáře: ");
		String n = scanner.nextLine();
		for (Scenario s : scenare) if (s.getNazev().equalsIgnoreCase(n)) return s;
		System.out.println("Scénář nebyl nalezen.");
		return null;
	}

	private int nactiInt(String p) {
		System.out.print(p);
		int x = scanner.nextInt();
		scanner.nextLine();
		return x;
	}

	private double nactiDouble(String p) {
		System.out.print(p);
		double x = scanner.nextDouble();
		scanner.nextLine();
		return x;
	}

	private int nactiRozsah(String p, int d, int min, int max) {
		System.out.print(p);
		String x = scanner.nextLine();
		if (x.trim().isEmpty()) return d;
		int v = Integer.parseInt(x);
		return Math.max(min, Math.min(max, v));
	}
}
