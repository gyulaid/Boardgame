package hu.unideb.inf.boardgame.player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PlayerTest {

    private static Stream<Arguments> provideInputForGamesPlayed(){
        return Stream.of(
                Arguments.of(10.0, 5.0, 5.0),
                Arguments.of(16.0, 11.0, 5.0),
                Arguments.of(3.0, 0.0, 3.0),
                Arguments.of(7.0, 3.0, 4.0)
        );
    }

    private static Stream<Arguments> provideInputForWinPerLoseRatio(){
        return Stream.of(
                Arguments.of(1.0, 5.0, 5.0),
                Arguments.of(0.5, 1.0, 2.0),
                Arguments.of(0.0, 0.0, 3.0),
                Arguments.of(0.25, 1.0, 4.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInputForGamesPlayed")
    public void gamesPlayedShouldReturnSumOfLossesAndWins(Double expected, Double input, Double input2){
        Player testPlayer = new Player();
        testPlayer.setAmountOfLosses(input);
        testPlayer.setAmountOfWins(input2);

        Assertions.assertEquals(expected, testPlayer.gamesPlayed());
    }


    @ParameterizedTest
    @MethodSource("provideInputForWinPerLoseRatio")
    public void winPerLoseRatioShouldReturnWinsDividedByLosses(Double expected, Double input, Double input2){
        Player testPlayer = new Player();
        testPlayer.setAmountOfWins(input);
        testPlayer.setAmountOfLosses(input2);

        Assertions.assertEquals(expected, testPlayer.winPerLoseRatio());
    }


    @Test
    public void winPerLoseRatioAsStringShouldReturnWithMaxThreeNumbersAfterDecimalPoint(){
        Player testPlayer = new Player();
        testPlayer.setAmountOfWins(1.0);
        testPlayer.setAmountOfLosses(3.0);
        String string = String.format("%.3f", testPlayer.winPerLoseRatio());
        Assertions.assertEquals(string , testPlayer.winPerLoseRatioAsString());
    }








}
