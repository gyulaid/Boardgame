package hu.unideb.inf.boardgame.player;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {


    @Mock
    PlayerDao playerDao;


    @InjectMocks
    PlayerService playerService;

    @Test
    public void getPlayersShouldSucceedWhenPlayersListNotNull() {
        Player testPlayer = new Player();
        testPlayer.setUserName("Test");

        List<Player> players = new ArrayList<>();
        players.add(testPlayer);

        when(playerDao.getPlayers()).thenReturn(players);

        List<Player> checkPlayers = playerService.getAllPlayers();

        verify(playerDao).getPlayers();

        Assertions.assertFalse(checkPlayers.isEmpty());

        Assertions.assertEquals(testPlayer, players.get(0));
    }


    @Test
    public void getPlayerDataShouldSucceedWhenPlayersHaveAnUsername(){
        Player testPlayer = new Player();
        testPlayer.setUserName("Test");

        when(playerDao.searchByUserName(any(String.class))).thenReturn(testPlayer);

        Player checkPlayer = playerService.getPlayerData("Test");

        verify(playerDao).searchByUserName(any(String.class));

        Assertions.assertEquals(checkPlayer, testPlayer);

    }


    @Test
    public void createUserShouldSucceedWhenPlayerIsNotNull(){

        Player testPlayer = new Player();
        testPlayer.setUserName("TestPlayer");
        testPlayer.setPassword("TestPlayer");

        try {
            playerService.createUser(testPlayer.getUserName(), testPlayer.getPassword());
        } catch (Exception e){
            e.printStackTrace();
        }

        verify(playerDao).savePlayer(any(Player.class));

    }

    @Test
    public void validateLoginShouldSucceedWhenPlayerExists(){

        Player testPlayer = new Player();
        testPlayer.setUserName("TestName");
        testPlayer.setPassword("TestPassword");

        try {
            playerService.validateLogIn(testPlayer.getUserName(), testPlayer.getPassword(), "TestColor");
        }catch (Exception e){
            e.printStackTrace();
        }

        verify(playerDao).searchByUserName(any(String.class));
    }

}
