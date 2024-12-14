
-- Tabella per le valutazioni
DROP TABLE IF EXISTS valutazione;
CREATE TABLE valutazione (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    esito INT NOT NULL, -- negativa, neutra, positiva
    utente_valutato VARCHAR(50),
    utente_valutante VARCHAR(50),
    ID_evento INT
);

