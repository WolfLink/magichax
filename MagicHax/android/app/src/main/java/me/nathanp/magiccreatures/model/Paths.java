package me.nathanp.magiccreatures.model;

public class Paths {
    private static String playerPath = "/players/";
    private static String currentGame = "/currentGame/";
    private static String gamePath = "/games/";
    private static String cardsPath = "/cards/";
    private static String creaturesPath = "/creatures/";

    public static String getPlayerPath(String uid) {
        return playerPath + uid;
    }

    public static String getCreaturesPath(String uid) {
        return creaturesPath + uid;
    }

    public static String getCreaturePath(String uid, String which) {
        return creaturesPath + uid + which;
    }

    public static String getCurrentGamePath(String uid) {
        return playerPath + uid + currentGame;
    }

    public static String getGamePath(String gameId) {
        return gamePath + gameId;
    }

    public static String getCardsPath(String uid) {
        return cardsPath + uid;
    }
}
