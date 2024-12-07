package model.prenotazione;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrenotazioneBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int eventoID;
	private String utenteUsername;
	private int posizioneInCoda;
	private String stato;
	
	public PrenotazioneBean() {}
	
	public PrenotazioneBean(int eventoID, String utenteUsername, int posizioneInCoda, String stato) {
		super();
		this.eventoID = eventoID;
		this.utenteUsername = utenteUsername;
		this.posizioneInCoda = posizioneInCoda;
		this.stato = stato;
	}
	
	public PrenotazioneBean(ResultSet rs) throws SQLException {
		super();
		this.eventoID = rs.getInt("ID_evento");
		this.utenteUsername = rs.getString("username_utente");
		this.posizioneInCoda = rs.getInt("posizione_in_coda");
		this.stato = rs.getString("stato");
	}
	
	public int getEventoID() {
		return eventoID;
	}
	public void setEventoID(int eventoID) {
		this.eventoID = eventoID;
	}
	public String getUtenteUsername() {
		return utenteUsername;
	}
	public void setUtenteUsername(String utenteUsername) {
		this.utenteUsername = utenteUsername;
	}
	public int getPosizioneInCoda() {
		return posizioneInCoda;
	}
	public void setPosizioneInCoda(int posizioneInCoda) {
		this.posizioneInCoda = posizioneInCoda;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}

	@Override
	public String toString() {
		return "PrenotazioneBean [eventoID=" + eventoID + ", utenteUsername=" + utenteUsername + ", posizioneInCoda="
				+ posizioneInCoda + ", stato=" + stato + "]";
	}
	
}
