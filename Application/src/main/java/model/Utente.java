package model;

import java.util.Date;

/**
 * La classe {@code Utente} rappresenta un record della tabella `utente` del database.
 * Contiene informazioni personali, statistiche di valutazione e informazioni sullo stato dell'utente.
 * 
 * <p>Ogni istanza della classe rappresenta un utente univoco identificato da uno username.</p>
 * 
 * @author Carmine
 * @version 1.0
 */
public class Utente {

    // Informazioni personali
    private String username;
    private String cognome;
    private String nome;
    private Date dataDiNascita;
    private String email;
    private String pw; // Password hash (CHAR(64))

    // Stato dell'utente
    private int numTimeout; // Numero di timeout ricevuti
    private boolean isTimeout; // Indica se l'utente è in timeout
    private boolean isAdmin; // Indica se l'utente è un amministratore
    private Date dataOraFineTimeout; // Data e ora di fine timeout

    // Valutazioni
    private int numValutazioniNeutre;
    private int numValutazioniNegative;
    private int numValutazioniPositive;

    /**
     * Costruttore completo per creare un'istanza di {@code Utente}.
     * 
     * @param username               lo username dell'utente
     * @param cognome                il cognome dell'utente
     * @param nome                   il nome dell'utente
     * @param dataDiNascita          la data di nascita dell'utente
     * @param email                  l'email dell'utente
     * @param pw                     la password (hashata) dell'utente
     * @param numTimeout             il numero di timeout ricevuti
     * @param isTimeout              indica se l'utente è attualmente in timeout
     * @param isAdmin                indica se l'utente è un amministratore
     * @param dataOraFineTimeout     la data e ora in cui termina il timeout (se applicabile)
     * @param numValutazioniNeutre   il numero di valutazioni neutre ricevute
     * @param numValutazioniNegative il numero di valutazioni negative ricevute
     * @param numValutazioniPositive il numero di valutazioni positive ricevute
     */
    public Utente(String username, String cognome, String nome, Date dataDiNascita, String email, String pw,
                  int numTimeout, boolean isTimeout, boolean isAdmin, Date dataOraFineTimeout,
                  int numValutazioniNeutre, int numValutazioniNegative, int numValutazioniPositive) {
        this.username = username;
        this.cognome = cognome;
        this.nome = nome;
        this.dataDiNascita = dataDiNascita;
        this.email = email;
        this.pw = pw;
        this.numTimeout = numTimeout;
        this.isTimeout = isTimeout;
        this.isAdmin = isAdmin;
        this.dataOraFineTimeout = dataOraFineTimeout;
        this.numValutazioniNeutre = numValutazioniNeutre;
        this.numValutazioniNegative = numValutazioniNegative;
        this.numValutazioniPositive = numValutazioniPositive;
    }

    // Getter e setter

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public int getNumTimeout() {
        return numTimeout;
    }

    public void setNumTimeout(int numTimeout) {
        this.numTimeout = numTimeout;
    }

    public boolean isTimeout() {
        return isTimeout;
    }

    public void setTimeout(boolean isTimeout) {
        this.isTimeout = isTimeout;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Date getDataOraFineTimeout() {
        return dataOraFineTimeout;
    }

    public void setDataOraFineTimeout(Date dataOraFineTimeout) {
        this.dataOraFineTimeout = dataOraFineTimeout;
    }

    public int getNumValutazioniNeutre() {
        return numValutazioniNeutre;
    }

    public void setNumValutazioniNeutre(int numValutazioniNeutre) {
        this.numValutazioniNeutre = numValutazioniNeutre;
    }

    public int getNumValutazioniNegative() {
        return numValutazioniNegative;
    }

    public void setNumValutazioniNegative(int numValutazioniNegative) {
        this.numValutazioniNegative = numValutazioniNegative;
    }

    public int getNumValutazioniPositive() {
        return numValutazioniPositive;
    }

    public void setNumValutazioniPositive(int numValutazioniPositive) {
        this.numValutazioniPositive = numValutazioniPositive;
    }
}
