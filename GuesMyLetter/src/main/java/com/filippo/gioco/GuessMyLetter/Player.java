package com.filippo.gioco.GuessMyLetter;

public class Player {
    private final String nome;
    private final int tentativi;
    private final char lettera;

    public Player(String nome, int tentativi, char lettera) {
        this.nome = nome;
        this.tentativi = tentativi;
        this.lettera = lettera;
    }

    public String getNome() {
        return nome;
    }

    public int getTentativi() {
        return tentativi;
    }
    
    public char getLettera() {
        return lettera;
    }
    
    @Override
    public String toString() {
        return nome + "," + tentativi+ "," + lettera;
    }

    public static Player fromString(String line) {
        String[] parts = line.split(",");
        return new Player(parts[0], Integer.parseInt(parts[1]), parts[2].charAt(0));
    }
}
