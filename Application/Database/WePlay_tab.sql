CREATE DATABASE IF NOT EXISTS weplay;
USE weplay;

/*TABELLA UTENTE*/
-- Tabella per gli utenti
DROP TABLE IF EXISTS utente;
CREATE TABLE utente (
    username VARCHAR(50) PRIMARY KEY,
    cognome VARCHAR(50) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    num_timeout INT DEFAULT 0,
    is_timeout BOOLEAN DEFAULT FALSE,
    is_admin BOOLEAN DEFAULT FALSE NOT NULL,
    num_valutazioni_neutre INT DEFAULT 0,
    num_valutazioni_negative INT DEFAULT 0,
    num_valutazioni_positive INT DEFAULT 0,
    data_ora_fine_timeout DATETIME DEFAULT NULL,
    data_di_nascita DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    pw CHAR(64) NOT NULL
);

-- Tabella per gli eventi
DROP TABLE IF EXISTS evento;
CREATE TABLE evento (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    data_inizio DATE NOT NULL,
    ora_inizio TIME NOT NULL,
    prezzo DECIMAL(5, 2) NOT NULL,
    sport ENUM('Calcio', 'Basket', 'Pallavolo', 'Tennis', 'Ping Pong', 'Badminton', 'Golf', 'Beach Volley', 'Padel', 'Bocce') NOT NULL,
    titolo VARCHAR(100) NOT NULL,
    indirizzo VARCHAR(255) NOT NULL,
    massimo_di_partecipanti INT NOT NULL,
    citta VARCHAR(50) NOT NULL,
    stato ENUM('non iniziato', 'iniziato', 'finito') NOT NULL
);

-- Tabella per le prenotazioni
DROP TABLE IF EXISTS prenotazione;
CREATE TABLE prenotazione (
    username_utente VARCHAR(50),
    ID_evento INT,
    stato ENUM('in accettazione', 'in coda', 'attiva', 'organizzatore') NOT NULL,  -- in accettazione quando all'utente arriva la notifica di un posto libero e deve accettare o rifiutare
																				   -- in coda quando l'evento è pieno e l'utente prenotandosi viene aggiunto in una lista d'attesa aspettando un partecipante che cancella la sua prenotazione
                                                                                   -- attiva quando l'utente è un partecipante effettivo di un evento
                                                                                   -- organizzatore quando l'utente ha creato quell'evento
    posizione_in_coda INT DEFAULT NULL,
    PRIMARY KEY (username_utente, ID_evento),
    FOREIGN KEY (username_utente) REFERENCES Utente(username),
    FOREIGN KEY (ID_evento) REFERENCES Evento(ID) ON DELETE CASCADE
);

-- Tabella per le segnalazioni
DROP TABLE IF EXISTS segnalazione;
CREATE TABLE segnalazione (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    motivazione ENUM('assenza', 'violenza fisica', 'discriminazione', 'violenza verbale', 'condotta antisportiva', 'non appropriato', 'ritardo') NOT NULL,    -- assenza quando l'utente partecipante non si presenta all'evento
																																							  -- non appropriato quando l'utente si presenta all'evento in condizioni tali da non poter partecipare
    stato ENUM('in attesa', 'risolta') NOT NULL,
    utente_segnalato VARCHAR(50),
    utente_segnalante VARCHAR(50),
    ID_evento INT,
    FOREIGN KEY (utente_segnalato) REFERENCES Utente(username),
    FOREIGN KEY (utente_segnalante) REFERENCES Utente(username),
    FOREIGN KEY (ID_evento) REFERENCES Evento(ID) 
);

-- Tabella per le valutazioni
DROP TABLE IF EXISTS valutazione;
CREATE TABLE valutazione (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    esito INT NOT NULL, -- negativa, neutra, positiva
    utente_valutato VARCHAR(50),
    utente_valutante VARCHAR(50),
    ID_evento INT,
    FOREIGN KEY (utente_segnalato) REFERENCES Utente(username),
    FOREIGN KEY (utente_segnalante) REFERENCES Utente(username),
    FOREIGN KEY (ID_evento) REFERENCES Evento(ID) 
);

