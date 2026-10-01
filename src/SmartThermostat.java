class SmartThermostat extends ZakladniZarizeni {

	private double teplota;

	public SmartThermostat(String nazev, double teplota) {
		this(nazev, teplota, 1500, 8);
	}

	public SmartThermostat(String nazev, double teplota, double prikon, int priorita) {
		super(nazev, prikon, priorita);
		this.teplota = teplota;
	}

	public void nastavTeplotu(double novaTeplota) {
		this.teplota = novaTeplota;
		System.out.println("Teplota nastavena na " + teplota + "°C.");
	}

	public double getTeplota() {
		return teplota;
	}

	public void setTeplota(double teplota) {
		this.teplota = teplota;
	}

	@Override
	public void zapni() {
		if (!isZapnuto()) super.zapni(); else System.out.println(
			getNazev() + " je již zapnutý, teplota nastavena na " + teplota + "°C."
		);
	}

	@Override
	public String toString() {
		return super.toString() + ", teplota " + teplota + "°C";
	}
}
