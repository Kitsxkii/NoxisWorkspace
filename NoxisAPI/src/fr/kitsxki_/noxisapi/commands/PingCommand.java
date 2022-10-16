package fr.kitsxki_.noxisapi.commands;

import fr.kitsxki_.noxisapi.NoxisAPI;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            System.out.println(NoxisAPI.getAPIInstance().getConfigPatch("messages.commands.console_try_player_command"));
            return true;
        }

        Player player = (Player) sender;

        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();

        player.sendMessage(NoxisAPI.getAPIInstance().getConfigPatch("messages.give_player_ping").replace("<ping>", Integer.toString(nmsPlayer.ping)));


        return false;
    }

}
