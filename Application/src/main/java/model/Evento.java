package model;

import java.time.LocalDate;
import java.time.LocalTime;


public class Evento {
	private LocalDate data_inizio;
	private LocalTime ora_inizio;
	private double prezzo;
	private String sport;
	private String titolo;
	private String indirizzo;
	private int massimo_di_partecipanti;
	private int citta;
	private String stato;
	
	public Evento(LocalDate data_inizio, LocalTime ora_inizio, double prezzo, String sport, String titolo, String indirizzo, int massimo_di_partecipanti, int citta, String stato) {
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
	public int getCitta() {
		return citta;
	}
	public void setCitta(int citta) {
		this.citta = citta;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
}

