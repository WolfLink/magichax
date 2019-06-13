package me.nathanp.magiccreatures.model;

public class Paths {
    private static String playersPath = "players/";
    private static String currentGame = "currentGame/";
    private static String gamePath = "games/";
    private static String cardsPath = "cards/";
    private static String traitsPath = "traits/";
    private static String creaturesPath = "creatures/";

    public static String getPlayerCollectionPath() {
        return playersPath;
    }

    public static String getCardCollectionPath() {
        return cardsPath;
    }

    public static String getTraitCollectionPath() {
        return traitsPath;
    }

    public static String getGameCollectionPath() {
        return gamePath;
    }

    public static String getGameDocumentPath(String gameId) {
        return getGameCollectionPath() + gameId;
    }

    public static String getPlayerDocumentPath(String uid) {
        return getPlayerCollectionPath() + uid;
    }

    public static String getPlayerCreaturesCollectionPath(String uid) {
        return getPlayerCollectionPath() + uid + "/" + creaturesPath;
    }

    public static String getPlayerCreatureDocumentPath(String uid, String id) {
        return getPlayerCreaturesCollectionPath(uid) + "/" + id;
    }

    public static String getPlayerCurrentGamePath(String uid) {
        return getPlayerCollectionPath() + uid + "/" + currentGame;
    }
}
