package model.evento;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Evento {

    /* L'identificatore univoco dell'evento. */
    private int ID;

    /* La data di svolgimento dell'evento. */
    private LocalDate data_inizio;

    /* L'ora di inizio dell'evento. */
    private LocalTime ora_inizio;

    /* Il prezzo dell'evento. */
    private double prezzo;

    /* Lo sport associato all'evento. */
    private String sport;

    /* Il titolo dell'evento. */
    private String titolo;

    /* L'indirizzo dell'evento. */
    private String indirizzo;

    /* Il numero massimo di partecipanti per l'evento. */
    private int massimo_di_partecipanti;

    /* La città in cui si svolge l'evento. */
    private String citta;

    /* Lo stato in cui si trova l'evento. */
    private String stato;

    /*
     Costruttore per creare un evento con i dettagli forniti.
  	*/
    public Evento(LocalDate data_inizio, LocalTime ora_inizio, double prezzo, String sport, String titolo, String indirizzo, int massimo_di_partecipanti, String citta, String stato) {
        this.data_inizio = data_inizio;
        this.ora_inizio = ora_inizio;
        this.prezzo = prezzo;
        this.sport = sport;
        this.titolo = titolo;
        this.indirizzo = indirizzo;
        this.massimo_di_partecipanti = massimo_di_partecipanti;
        this.citta = citta;
        this.stato = stato;
    }

    /* Costruttore per creare un evento a partire da un ResultSet.
       @param rs Il ResultSet contenente i dettagli dell'evento.
       @throws SQLException Se si verifica un errore nell'accesso al ResultSet.
     */
    public Evento(ResultSet rs) throws SQLException {
        this.ID = rs.getInt("ID");
        this.data_inizio = (LocalDate) rs.getObject("data_inizio");
        this.ora_inizio = (LocalTime) rs.getObject("ora_inizio");
        this.prezzo = rs.getDouble("prezzo");
        this.sport = rs.getString("sport");
        this.titolo = rs.getString("titolo");
        this.indirizzo = rs.getString("indirizzo");
        this.massimo_di_partecipanti = rs.getInt("massimo_di_partecipanti");
        this.citta = rs.getString("citta");
        this.stato = rs.getString("stato");
    }

    public LocalDate getData_inizio() {
        return data_inizio;
    }


    public void setData_inizio(LocalDate data_inizio) {
        this.data_inizio = data_inizio;
    }

 
    public LocalTime getOra_inizio() {
        return ora_inizio;
    }

 
    public void setOra_inizio(LocalTime ora_inizio) {
        this.ora_inizio = ora_inizio;
    }

 
    public double getPrezzo() {
        return prezzo;
    }

 
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

 
    public String getSport() {
        return sport;
    }

 
    public void setSport(String sport) {
        this.sport = sport;
    }


    public String getTitolo() {
        return titolo;
    }

  
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }


    public String getIndirizzo() {
        return indirizzo;
    }


    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }


    public int getMassimo_di_partecipanti() {
        return massimo_di_partecipanti;
    }


    public void setMassimo_di_partecipanti(int massimo_di_partecipanti) {
        this.massimo_di_partecipanti = massimo_di_partecipanti;
    }


    public String getCitta() {
        return citta;
    }

 
    public void setCitta(String citta) {
        this.citta = citta;
    }

 
    public String getStato() {
        return stato;
    }

 
    public void setStato(String stato) {
        this.stato = stato;
    }


    public int getID() {
        return ID;
    }
    
    
}
