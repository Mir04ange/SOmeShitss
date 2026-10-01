import java.util.ArrayList;
import java.util.List;

/** Uživatelský scénář tvořený postupně prováděnými akcemi. */
class Scenario {

	private final String nazev;
	private final List<Runnable> akce = new ArrayList<>();
	private final List<String> popisy = new ArrayList<>();

	Scenario(String nazev) {
		this.nazev = nazev;
	}

	String getNazev() {
		return nazev;
	}

	void pridejAkci(String popis, Runnable runnable) {
		popisy.add(popis);
		akce.add(runnable);
	}

	void vypis() {
		System.out.println("- " + nazev);
		for (String popis : popisy) System.out.println("    " + popis);
	}

	void spust() {
		System.out.println("Spouštím scénář: " + nazev);
		for (Runnable runnable : akce) runnable.run();
	}
}
