package fr.kitsxki_.noxisapi.objects;

import fr.kitsxki_.noxisapi.database.PlayerData;
import fr.kitsxki_.noxisapi.database.PlayerInfo;
import org.bukkit.entity.Player;

public class NoxisPlayer {

    Player player;
    PlayerData playerData;
    PlayerInfo playerInfo;

    public NoxisPlayer(Player player, PlayerData playerData, PlayerInfo playerInfo) {
        this.player = player;
        this.playerData = playerData;
        this.playerInfo = playerInfo;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }
}
