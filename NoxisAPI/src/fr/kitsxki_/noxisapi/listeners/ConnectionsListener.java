package fr.kitsxki_.noxisapi.listeners;

import fr.kitsxki_.noxisapi.NoxisAPI;
import fr.kitsxki_.noxisapi.database.PlayerData;
import fr.kitsxki_.noxisapi.database.PlayerInfo;
import fr.kitsxki_.noxisapi.enums.PrefixMode;
import fr.kitsxki_.noxisapi.objects.NoxisPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class ConnectionsListener implements Listener {

    @EventHandler
    public void JoinEvent(PlayerJoinEvent e) {

        final Player player = e.getPlayer();
        final UUID playerUUID = player.getUniqueId();
        final NoxisPlayer noxisPlayer = new NoxisPlayer(player, new PlayerData(playerUUID), new PlayerInfo(player));

        NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.CONSOLE) + "The player " + player.getName() + " has joined the server.");

        NoxisAPI.getDatabaseManager().createAccount(playerUUID);
        NoxisAPI.getNoxisPlayers().add(noxisPlayer);

    }

    @EventHandler
    public void QuitEvent(PlayerQuitEvent e) {

        final Player player = e.getPlayer();
        final UUID playerUUID = player.getUniqueId();
        final NoxisPlayer noxisPlayer = new NoxisPlayer(player, new PlayerData(playerUUID), new PlayerInfo(player));

        NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.CONSOLE) + "The player " + player.getName() + " has left the server.");

        NoxisAPI.getNoxisPlayers().remove(noxisPlayer);

    }

}
