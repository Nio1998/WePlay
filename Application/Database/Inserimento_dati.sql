INSERT INTO evento (data_inizio, ora_inizio, prezzo, sport, titolo, indirizzo, massimo_di_partecipanti, citta, stato)
VALUES
-- Eventi non iniziati (date future)
('2024-12-10', '18:00:00', 10.00, 'Calcio', 'Partita amatoriale', 'Via Roma 10', 10, 'Milano', 'non iniziato'),
('2024-12-15', '20:00:00', 12.00, 'Basket', 'Torneo serale 3x3', 'Piazza Duomo 5', 6, 'Roma', 'non iniziato'),
('2024-12-18', '15:00:00', 5.00, 'Pallavolo', 'Allenamento libero', 'Parco Centrale', 12, 'Firenze', 'non iniziato'),
('2024-12-20', '19:00:00', 8.00, 'Padel', 'Torneo di Padel amatoriale', 'Via Mare 2', 4, 'Napoli', 'non iniziato'),
('2024-12-22', '10:00:00', 25.00, 'Golf', 'Gara su 9 buche', 'Circolo Golf Torino', 8, 'Torino', 'non iniziato'),
('2024-12-16', '17:00:00', 7.00, 'Ping Pong', 'Torneo 1v1', 'Palestra Comunale', 10, 'Verona', 'non iniziato'),
('2024-12-14', '16:00:00', 6.00, 'Badminton', 'Doppio amatoriale', 'Centro Sportivo', 8, 'Bologna', 'non iniziato'),
('2024-12-11', '14:00:00', 15.00, 'Beach Volley', 'Partita sulla spiaggia', 'Spiaggia Centrale', 10, 'Rimini', 'non iniziato'),
('2024-12-19', '09:00:00', 10.00, 'Tennis', 'Torneo singolare', 'Club Tennis', 8, 'Genova', 'non iniziato'),
('2024-12-13', '18:00:00', 5.00, 'Bocce', 'Partita amatoriale', 'Parco della Pace', 6, 'Bari', 'non iniziato'),

-- Eventi iniziati (data odierna: 2024-12-05)
('2024-12-05', '19:00:00', 0.00, 'Calcio', 'Partita di beneficenza', 'Stadio Comunale', 22, 'Palermo', 'iniziato'),
('2024-12-05', '20:00:00', 8.00, 'Basket', 'Partita serale', 'Palestra Rossini', 10, 'Venezia', 'iniziato'),
('2024-12-05', '18:00:00', 5.00, 'Pallavolo', 'Allenamento misto', 'Centro Sportivo', 12, 'Torino', 'iniziato'),

-- Eventi finiti (date passate)
('2024-12-01', '17:00:00', 20.00, 'Golf', 'Gara amatoriale', 'Circolo Golf Torino', 8, 'Torino', 'finito'),
('2024-11-30', '10:00:00', 5.00, 'Calcio', 'Partita tra amici', 'Campetto San Paolo', 10, 'Napoli', 'finito'),
('2024-11-28', '14:00:00', 15.00, 'Tennis', 'Finale torneo', 'Club Tennis Roma', 2, 'Roma', 'finito'),
('2024-11-29', '16:00:00', 7.00, 'Ping Pong', 'Torneo amatoriale', 'Palestra Comunale', 8, 'Firenze', 'finito'),
('2024-11-27', '15:00:00', 6.00, 'Badminton', 'Partita singolare', 'Palazzetto dello Sport', 4, 'Bologna', 'finito'),
('2024-11-26', '13:00:00', 10.00, 'Beach Volley', 'Torneo misto', 'Spiaggia Sud', 12, 'Cagliari', 'finito'),
('2024-11-25', '18:00:00', 5.00, 'Bocce', 'Partita tra amici', 'Giardini Pubblici', 6, 'Bari', 'finito');







-- Inserimento di utenti
INSERT INTO utente (username, cognome, nome, num_timeout, is_timeout, is_admin, num_valutazioni_neutre, num_valutazioni_negative, num_valutazioni_positive, data_ora_fine_timeout, data_di_nascita, email, pw) 
VALUES 
('mario_rossi', 'Rossi', 'Mario', 0, false, false, 0, 1, 3, NULL, '1985-04-20', 'mario.rossi@email.com', SHA2('password123', 256)),
('luigi_bianchi', 'Bianchi', 'Luigi', 0, false, false, 1, 0, 2, NULL, '1990-07-15', 'luigi.bianchi@email.com', SHA2('password456', 256)),
('giulia_verdi', 'Verdi', 'Giulia', 1, false, false, 2, 0, 1, NULL, '1992-02-05', 'giulia.verdi@email.com', SHA2('password789', 256)),
('anna_neri', 'Neri', 'Anna', 0, false, false, 0, 0, 5, NULL, '1995-11-30', 'anna.neri@email.com', SHA2('password101', 256)),
('paolo_toscano', 'Toscano', 'Paolo', 0, false, false, 1, 1, 2, NULL, '1991-03-12', 'paolo.toscano@email.com', SHA2('password202', 256)),
('alessandro_benetti', 'Benetti', 'Alessandro', 0, false, false, 0, 0, 1, NULL, '1988-08-22', 'alessandro.benetti@email.com', SHA2('password303', 256)),
('maria_costa', 'Costa', 'Maria', 0, false, false, 1, 2, 3, NULL, '1987-05-30', 'maria.costa@email.com', SHA2('password404', 256)),
('giovanni_roma', 'Roma', 'Giovanni', 0, false, false, 2, 1, 2, NULL, '1993-09-10', 'giovanni.roma@email.com', SHA2('password505', 256)),
('marco_ferrari', 'Ferrari', 'Marco', 0, false, false, 0, 0, 4, NULL, '1990-12-01', 'marco.ferrari@email.com', SHA2('password606', 256)),
('francesca_longo', 'Longo', 'Francesca', 0, false, false, 3, 0, 1, NULL, '1994-06-17', 'francesca.longo@email.com', SHA2('password707', 256)),
('luca_rossi', 'Rossi', 'Luca', 0, false, false, 0, 1, 4, NULL, '1992-01-11', 'luca.rossi@email.com', SHA2('password808', 256)),
('sara_netti', 'Netti', 'Sara', 0, false, false, 0, 0, 5, NULL, '1991-10-05', 'sara.netti@email.com', SHA2('password909', 256)),
('stefano_mancini', 'Mancini', 'Stefano', 0, false, false, 1, 1, 3, NULL, '1990-03-20', 'stefano.mancini@email.com', SHA2('password1010', 256)),
('admin01', 'Admin', 'User', 0, false, true, 0, 0, 0, NULL, '1985-01-01', 'admin@example.com', SHA2('adminpass', 256));








-- Prenotazioni per eventi non iniziati
INSERT INTO prenotazione (username_utente, ID_evento, stato, posizione_in_coda) 
VALUES
('mario_rossi', 1, 'in accettazione', NULL),
('luigi_bianchi', 2, 'attiva', NULL),
('giulia_verdi', 3, 'in coda', 1),
('anna_neri', 4, 'in coda', 2),
('paolo_toscano', 5, 'attiva', NULL),
('alessandro_benetti', 6, 'attiva', NULL),
('maria_costa', 7, 'attiva', NULL),
('giovanni_roma', 8, 'in accettazione', NULL),
('marco_ferrari', 9, 'attiva', NULL),
('francesca_longo', 10, 'in accettazione', NULL);

-- Prenotazioni per eventi iniziati
INSERT INTO prenotazione (username_utente, ID_evento, stato, posizione_in_coda)
VALUES
('luca_rossi', 11, 'attiva', NULL),
('sara_netti', 12, 'attiva', NULL),
('stefano_mancini', 13, 'attiva', NULL);

-- Prenotazioni per eventi finiti
INSERT INTO prenotazione (username_utente, ID_evento, stato, posizione_in_coda)
VALUES
('mario_rossi', 14, 'attiva', NULL),
('luigi_bianchi', 15, 'attiva', NULL),
('giulia_verdi', 16, 'attiva', NULL);







-- Segnalazioni per eventi non iniziati
INSERT INTO segnalazione (motivazione, stato, utente_segnalato, utente_segnalante, ID_evento)
VALUES
('assenza', 'in attesa', 'giulia_verdi', 'mario_rossi', 1),
('violenza verbale', 'in attesa', 'alessandro_benetti', 'marco_ferrari', 2),
('ritardo', 'in attesa', 'paolo_toscano', 'giovanni_roma', 3),
('condotta antisportiva', 'in attesa', 'maria_costa', 'francesca_longo', 4),
('non appropriato', 'in attesa', 'giovanni_roma', 'maria_costa', 5),
('violenza fisica', 'in attesa', 'giovanni_roma', 'alessandro_benetti', 6),
('assenza', 'in attesa', 'francesca_longo', 'luigi_bianchi', 7),
('ritardo', 'in attesa', 'alessandro_benetti', 'giovanni_roma', 8),
('violenza verbale', 'in attesa', 'maria_costa', 'mario_rossi', 9),
('condotta antisportiva', 'in attesa', 'francesca_longo', 'luigi_bianchi', 10);

-- Segnalazioni per eventi iniziati
INSERT INTO segnalazione (motivazione, stato, utente_segnalato, utente_segnalante, ID_evento)
VALUES
('assenza', 'in attesa', 'sara_netti', 'luca_rossi', 11),
('violenza verbale', 'in attesa', 'stefano_mancini', 'sara_netti', 12),
('violenza fisica', 'in attesa', 'luca_rossi', 'stefano_mancini', 13);

-- Segnalazioni per eventi finiti
INSERT INTO segnalazione (motivazione, stato, utente_segnalato, utente_segnalante, ID_evento)
VALUES
('ritardo', 'in attesa', 'luigi_bianchi', 'giulia_verdi', 14),
('non appropriato', 'in attesa', 'giulia_verdi', 'mario_rossi', 15),
('assenza', 'in attesa', 'mario_rossi', 'luigi_bianchi', 16);







-- Valutazioni per eventi non iniziati
INSERT INTO valutazione (esito, utente_segnalato, utente_segnalante, ID_evento)
VALUES
(1, 'giulia_verdi', 'mario_rossi', 1),  -- Positiva
(0, 'giovanni_roma', 'anna_neri', 2),  -- Neutra
(-1, 'paolo_toscano', 'maria_costa', 3), -- Negativa
(1, 'alessandro_benetti', 'marco_ferrari', 4), -- Positiva
(0, 'francesca_longo', 'giovanni_roma', 5), -- Neutra
(-1, 'luigi_bianchi', 'giulia_verdi', 6), -- Negativa
(1, 'maria_costa', 'paolo_toscano', 7), -- Positiva
(0, 'alessandro_benetti', 'giovanni_roma', 8), -- Neutra
(-1, 'giovanni_roma', 'mario_rossi', 9), -- Negativa
(1, 'francesca_longo', 'luigi_bianchi', 10); -- Positiva

-- Valutazioni per eventi iniziati
INSERT INTO valutazione (esito, utente_segnalato, utente_segnalante, ID_evento)
VALUES
(0, 'sara_netti', 'luca_rossi', 11), -- Neutra
(1, 'stefano_mancini', 'sara_netti', 12), -- Positiva
(-1, 'luca_rossi', 'stefano_mancini', 13); -- Negativa

-- Valutazioni per eventi finiti
INSERT INTO valutazione (esito, utente_segnalato, utente_segnalante, ID_evento)
VALUES
(1, 'luigi_bianchi', 'giulia_verdi', 14), -- Positiva
(0, 'giulia_verdi', 'mario_rossi', 15), -- Neutra
(-1, 'mario_rossi', 'luigi_bianchi', 16); -- Negativa


