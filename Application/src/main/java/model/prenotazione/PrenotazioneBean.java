package model.prenotazione;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrenotazioneBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String utenteUsername;
	private int eventoID;
	private String stato;
	private int posizioneInCoda;
	
	public PrenotazioneBean() {}
	
	public PrenotazioneBean(String utenteUsername, int eventoID, String stato, int posizioneInCoda) {
		super();
		this.utenteUsername = utenteUsername;
		this.eventoID = eventoID;
		this.stato = stato;
		this.posizioneInCoda = posizioneInCoda;
	}
	
	public PrenotazioneBean(ResultSet rs) throws SQLException {
		super();
		this.utenteUsername = rs.getString("username_utente");
		this.eventoID = rs.getInt("ID_evento");
		this.stato = rs.getString("stato");
		this.posizioneInCoda = rs.getInt("posizione_in_coda");
	}
	
	public String getUtenteUsername() {
		return utenteUsername;
	}
	public void setUtenteUsername(String utenteUsername) {
		this.utenteUsername = utenteUsername;
	}
	public int getEventoID() {
		return eventoID;
	}
	public void setEventoID(int eventoID) {
		this.eventoID = eventoID;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public int getPosizioneInCoda() {
		return posizioneInCoda;
	}
	public void setPosizioneInCoda(int posizioneInCoda) {
		this.posizioneInCoda = posizioneInCoda;
	}

	@Override
	public String toString() {
		return "PrenotazioneBean [utenteUsername=" + utenteUsername + ", eventoID=" + eventoID + ", stato=" + stato
				+ ", posizioneInCoda=" + posizioneInCoda + "]";
	}
	
	
	
}
