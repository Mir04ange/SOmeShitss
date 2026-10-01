/** Společná implementace vlastností všech chytrých zařízení. */
public abstract class ZakladniZarizeni implements ISmartDevice {

	private String nazev;
	private boolean zapnuto;
	private int pocetSpusteni;
	private final double prikon;
	private final int priorita;

	protected ZakladniZarizeni(String nazev, double prikon, int priorita) {
		if (nazev == null || nazev.trim().isEmpty()) throw new IllegalArgumentException("Název nesmí být prázdný.");
		if (prikon < 0) throw new IllegalArgumentException("Příkon nesmí být záporný.");
		if (priorita < 1 || priorita > 10) throw new IllegalArgumentException("Priorita musí být 1 až 10.");
		this.nazev = nazev;
		this.prikon = prikon;
		this.priorita = priorita;
	}

	@Override
	public void zapni() {
		if (!zapnuto) {
			zapnuto = true;
			pocetSpusteni++;
		}
		System.out.println(nazev + " je zapnuto.");
	}

	@Override
	public void vypni() {
		zapnuto = false;
		System.out.println(nazev + " je vypnuto.");
	}

	@Override
	public String stav() {
		return zapnuto ? "zapnuto" : "vypnuto";
	}

	@Override
	public String getNazev() {
		return nazev;
	}

	@Override
	public void setNazev(String nazev) {
		if (nazev == null || nazev.trim().isEmpty()) throw new IllegalArgumentException("Název nesmí být prázdný.");
		this.nazev = nazev;
	}

	@Override
	public int getPocetSpusteni() {
		return pocetSpusteni;
	}

	@Override
	public double getPrikon() {
		return prikon;
	}

	@Override
	public int getPriorita() {
		return priorita;
	}

	public boolean isZapnuto() {
		return zapnuto;
	}

	public void setZapnuto(boolean zapnuto) {
		this.zapnuto = zapnuto;
	}

	@Override
	public String toString() {
		return (
			nazev + " - " + stav() + " (" + prikon + " W, priorita " + priorita + ", spuštění " + pocetSpusteni + ")"
		);
	}
}
