package model.Segnalazione;

import java.io.Serializable;

public class Segnalazione implements Serializable {
 
    private static final long serialVersionUID = 1L;
	
    private int id;
    
	private String motivazione; // Enum per motivazione: 'assenza', 'violenza fisica', 'discriminazione', 'violenza verbale', 'condotta antisportiva', 'non appropriato', 'ritardo'
    private String stato;             // Enum per stato: in attesa, risolta   
    private String utenteSegnalato;
    private String utenteSegnalante;
    private int idEvento;

    public Segnalazione( String motivazione, String stato, String utenteSegnalato, String utenteSegnalante, int idEvento) {
        this.motivazione = motivazione;
        this.stato = stato;
        this.utenteSegnalato = utenteSegnalato;
        this.utenteSegnalante = utenteSegnalante;
        this.idEvento = idEvento;
    }

    // Get e Set
    public int getId() {
        return id;
    }
    public void setId(int id) {
		this.id = id;
	}


    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getUtenteSegnalato() {
        return utenteSegnalato;
    }

    public void setUtenteSegnalato(String utenteSegnalato) {
        this.utenteSegnalato = utenteSegnalato;
    }

    public String getUtenteSegnalante() {
        return utenteSegnalante;
    }

    public void setUtenteSegnalante(String utenteSegnalante) {
        this.utenteSegnalante = utenteSegnalante;
    }

    public int getIdEvento() {
        return idEvento;
    }
    
    public void setIdEvento(int idEvento) {
    	this.idEvento = idEvento;
    }

    @Override
    public String toString() {
        return "Segnalazione{" +
                "id=" + id +
                ", motivazione=" + motivazione +
                ", stato=" + stato +
                ", utenteSegnalato='" + utenteSegnalato + '\'' +
                ", utenteSegnalante='" + utenteSegnalante + '\'' +
                ", idEvento=" + idEvento +
                '}';
    }
}

