class SmartLight extends ZakladniZarizeni {

	public SmartLight(String nazev) {
		this(nazev, 60, 5);
	}

	public SmartLight(String nazev, double prikon, int priorita) {
		super(nazev, prikon, priorita);
	}

	@Override
	public String toString() {
		return (
			getNazev() +
			" - " +
			stav() +
			" (" +
			getPrikon() +
			" W, priorita " +
			getPriorita() +
			", spuštění " +
			getPocetSpusteni() +
			")"
		);
	}
}
