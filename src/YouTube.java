/** Nová streamovací služba přidaná do domácího asistenta. */
class YouTube implements IStreamingService {

	private boolean prehravani;
	private int pocetSpusteni;

	@Override
	public void prehrat(String titul) {
		if (!prehravani) pocetSpusteni++;
		prehravani = true;
		System.out.println("Přehrávání na YouTube: " + titul);
	}

	@Override
	public void stop() {
		prehravani = false;
		System.out.println("YouTube přehrávání ukončeno.");
	}

	@Override
	public boolean prehrava() {
		return prehravani;
	}

	@Override
	public int getPocetSpusteni() {
		return pocetSpusteni;
	}
}
