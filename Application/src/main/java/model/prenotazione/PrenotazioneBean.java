package model.prenotazione;

import java.io.Serializable;

public class PrenotazioneBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer eventoID;
	private String utenteUsername;
	private Integer posizioneInCoda;
	private String stato;
	
	public PrenotazioneBean(Integer eventoID, String utenteUsername, Integer posizioneInCoda, String stato) {
		super();
		this.eventoID = eventoID;
		this.utenteUsername = utenteUsername;
		this.posizioneInCoda = posizioneInCoda;
		this.stato = stato;
	}
	
	public Integer getEventoID() {
		return eventoID;
	}
	public void setEventoID(Integer eventoID) {
		this.eventoID = eventoID;
	}
	public String getUtenteUsername() {
		return utenteUsername;
	}
	public void setUtenteUsername(String utenteUsername) {
		this.utenteUsername = utenteUsername;
	}
	public Integer getPosizioneInCoda() {
		return posizioneInCoda;
	}
	public void setPosizioneInCoda(Integer posizioneInCoda) {
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
