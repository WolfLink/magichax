package me.nathanp.magiccreatures.model

object Paths {
    const val playerCollectionPath = "players"
    private const val currentGame = "currentGame"
    const val gameCollectionPath = "games"
    const val cardCollectionPath = "cards"
    const val traitCollectionPath = "traits"
    private const val creaturesPath = "creatures"

    fun getGameDocumentPath(gameId: String) = "$gameCollectionPath/$gameId"

    @JvmStatic
    fun getPlayerDocumentPath(uid: String) = "$playerCollectionPath/$uid"

    @JvmStatic
    fun getPlayerCreaturesCollectionPath(uid: String) = "$playerCollectionPath/$uid/$creaturesPath"

    fun getPlayerCreatureDocumentPath(uid: String, id: String) = getPlayerCreaturesCollectionPath(uid) + "/" + id

    @JvmStatic
    fun getPlayerCurrentGamePath(uid: String) = "$playerCollectionPath/$uid/$currentGame"
}