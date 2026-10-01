/**
 * Třída SmartThermostat implementuje rozhraní ISmartDevice a poskytuje
 * konkrétní implementace metod pro zapnutí, vypnutí a zjištění stavu chytrého termostatu.
 * Navíc umožňuje nastavit teplotu.
 */
class SmartThermostat implements ISmartDevice {
    private static String nazev;
    private boolean zapnuto;
    private double teplota;

    /**
     * Vytvoří nový chytrý termostat s daným názvem a počáteční teplotou.
     *
     * @param nazev Název chytrého termostatu.
     * @param teplota Počáteční teplota termostatu.
     */
    public SmartThermostat(String nazev, double teplota) {
        this.nazev = nazev;
        this.teplota = teplota;
        this.zapnuto = false;
    }

    /**
     * Zapne chytrý termostat a nastaví teplotu.
     */
    @Override
    public void zapni() {
        zapnuto = true;
        System.out.println(nazev + " je zapnutý, teplota nastavena na " + teplota + "°C.");
    }

    /**
     * Vypne chytrý termostat.
     */
    @Override
    public void vypni() {
        zapnuto = false;
        System.out.println(nazev + " je vypnutý.");
    }

    /**
     * Vrátí aktuální stav chytrého termostatu.
     *
     * @return Řetězec reprezentující aktuální stav termostatu.
     */
    @Override
    public String stav() {
        if (zapnuto) {
            return "zapnuto";
        } else {
            return "vypnuto";
        }
    }

// jenom ja a bůh víme jak to funguje

    public void nastavTeplotu(double novaTeplota) {
        this.teplota = novaTeplota;
        System.out.println("Teplota nastavena na " + teplota + "°C.");
    }

    public static String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    @Override
    public int getPocetSpusteni() {
        return 0;
    }

    public boolean isZapnuto() {
        return zapnuto;
    }

    public void setZapnuto(boolean zapnuto) {
        this.zapnuto = zapnuto;
    }

    public double getTeplota() {
        return teplota;
    }

    public void setTeplota(double teplota) {
        this.teplota = teplota;
    }

    @Override
    public String toString() {
        return nazev + " - " + stav() ;
    }
}
