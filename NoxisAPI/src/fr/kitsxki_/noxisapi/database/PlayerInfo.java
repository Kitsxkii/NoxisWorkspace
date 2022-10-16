package fr.kitsxki_.noxisapi.database;

import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;

public class PlayerInfo {

    private Player player;

    private PlayerData playerData;

    private static HashMap<Player, PlayerInfo> playerInfo = new HashMap<>();

    public PlayerInfo(Player player) {

        this.player = player;
        this.playerData = new PlayerData(player.getUniqueId());

        playerInfo.put(player, this);

    }

    // Getter
    public Player getPlayer() { return player; }

    public PlayerData getPlayerData() { return playerData; }

    public Float getCoins() throws SQLException { return playerData.getCoins(); }

    public static PlayerInfo getPlayerInfo(Player player) { return playerInfo.get(player); }

    // Coins modifiers
    public void addCoins(float amount) throws SQLException { playerData.addCoins(amount); }
    public void removeCoins(float amount) throws SQLException { playerData.removeCoins(amount); }
    public void setCoins(float amount) throws SQLException { playerData.setCoins(amount); }

}
