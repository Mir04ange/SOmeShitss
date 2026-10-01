public interface IStreamingService {
	void prehrat(String nazevTitulu);
	void stop();
	boolean prehrava();

	default String getNazev() {
		return getClass().getSimpleName();
	}

	default int getPocetSpusteni() {
		return 0;
	}
}
