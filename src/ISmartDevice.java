public interface ISmartDevice {
    void zapni();
    void vypni();
    String stav();

    String getNazev();
    void setNazev(String nazev);

    int getPocetSpusteni();
}