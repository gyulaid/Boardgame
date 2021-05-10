package hu.unideb.inf.boardgame.player;


import java.io.*;
import java.util.List;



public class PlayerDao {

    List<Player> players;
    String filePath;

    public PlayerDao(String filePath){
        this.filePath = filePath;
    }


    public List<Player> getPlayers(){

        return players;
    }


    public void savePlayers(List<Player> players){

        }






    }
