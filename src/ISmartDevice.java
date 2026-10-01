public interface ISmartDevice {
	void zapni();
	void vypni();
	String stav();
	String getNazev();
	void setNazev(String nazev);
	int getPocetSpusteni();

	default double getPrikon() {
		return 0;
	}

	default int getPriorita() {
		return 1;
	}
}
