package de.papiertuch.bedwars.game;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Scheduler {

    private Lobby lobby;
    private Game game;
    private Boarder boarder;
    private Ending ending;

    public Scheduler() {
        this.lobby = new Lobby();
        this.game = new Game();
        this.boarder = new Boarder();
        this.ending = new Ending();
    }

    public Boarder getBoarder() {
        return boarder;
    }

    public Ending getEnding() {
        return ending;
    }

    public Game getGame() {
        return game;
    }

    public Lobby getLobby() {
        return lobby;
    }
}
