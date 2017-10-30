package januarylimes.limeskoledy.model;

import java.io.Serializable;

/**
 * Created by kacper on 29.09.2016.
 */
public class Koleda implements Serializable {

    public Koleda(String nazwa, String tekst) {
        setNazwa(nazwa);
        setTekst(tekst);
    }

    private String Nazwa;

    public String getNazwa() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        Nazwa = nazwa;
    }

    private String Tekst;

    public String getTekst() {
        return Tekst;
    }

    public void setTekst(String tekst) {
        Tekst = tekst;
    }
}
