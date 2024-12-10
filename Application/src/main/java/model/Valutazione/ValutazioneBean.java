package model.Valutazione;

public class ValutazioneBean {
    
    private int id; // ID univoco per la valutazione
    private String esito; // Valori ammessi: "-1" (negativa), "0" (neutra), "1" (positiva)
    private String utenteValutato; // Utente che riceve la valutazione
    private String utenteValutante; // Utente che effettua la valutazione
    private int idEvento; // ID dell'evento associato

    // Costruttore
    public ValutazioneBean(int id, String esito, String utenteValutato, String utenteValutante, int idEvento) {
        this.id = id;
        this.esito = esito;
        this.utenteValutato = utenteValutato;
        this.utenteValutante = utenteValutante;
        this.idEvento = idEvento;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public String getUtenteValutato() {
        return utenteValutato;
    }

    public void setUtenteValutato(String utenteValutato) {
        this.utenteValutato = utenteValutato;
    }

    public String getUtenteValutante() {
        return utenteValutante;
    }

    public void setUtenteValutante(String utenteValutante) {
        this.utenteValutante = utenteValutante;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    // Override del metodo toString
    @Override
    public String toString() {
        return "Valutazione [" +
               "id=" + id +
               ", esito='" + esito + '\'' +
               ", utenteValutato='" + utenteValutato + '\'' +
               ", utenteValutante='" + utenteValutante + '\'' +
               ", idEvento=" + idEvento +
               ']';
    }
}
