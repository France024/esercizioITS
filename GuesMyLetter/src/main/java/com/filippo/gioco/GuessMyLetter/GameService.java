package com.filippo.gioco.GuessMyLetter;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GameService {

    private char letteraCasuale = generaLetteraCasuale();
    private int tentativiGiocatore = 0;
    private String nomeGiocatore = "";

    private List<Player> classifica = new ArrayList<>();

    public void caricaClassifica() {
        classifica.clear(); // Evita duplicati se caricato più volte
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("classifica.csv")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                classifica.add(Player.fromString(line));
            }
        } catch (Exception e) {
            // Se file non esiste o problemi, si ignora
            System.out.println("Classifica non trovata o vuota.");
        }
    }

    public void salvaClassifica() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/classifica.csv"))) {
            for (Player player : classifica) {
                writer.write(player.getTentativi());
                writer.write(player.getNome());
                writer.write(player.getLettera());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String processaTentativo(String nomeGiocatoreInput, char lettera) {
        if (this.nomeGiocatore.isEmpty()) {
            this.nomeGiocatore = nomeGiocatoreInput;
        }

        tentativiGiocatore++;

        if (lettera < letteraCasuale) {
            return "La lettera è dopo di " + lettera+ " ";
        } else if (lettera > letteraCasuale) {
            return "La lettera è prima di " + lettera+ " ";
        } else {
            String messaggio = "Complimenti, hai indovinato la lettera " + letteraCasuale + " in " + tentativiGiocatore + " tentativi!";
            classifica.add(new Player(nomeGiocatore, tentativiGiocatore, lettera));
            classifica.sort(Comparator.comparingInt(Player::getTentativi));
            salvaClassifica();
            resetGioco();
            return messaggio;
        }
    }

    public void resetGioco() {
        letteraCasuale = generaLetteraCasuale();
        tentativiGiocatore = 0;
        nomeGiocatore = "";
    }

    private char generaLetteraCasuale() {
        return (char) ('a' + (int)(Math.random() * 26));
    }

    public int getTentativiGiocatore() {
        return tentativiGiocatore;
    }

    public String getNomeGiocatore() {
        return nomeGiocatore;
    }
    
    public char getLetteraGiocatore() {
        return letteraCasuale;
    }

    public List<Player> getClassifica() {
        return classifica;
    }
}
