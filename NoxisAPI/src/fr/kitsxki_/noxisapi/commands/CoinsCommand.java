package fr.kitsxki_.noxisapi.commands;

import fr.kitsxki_.noxisapi.NoxisAPI;
import fr.kitsxki_.noxisapi.database.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CoinsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            System.out.println(NoxisAPI.getAPIInstance().getConfigPatch("messages.commands.console_try_player_command"));
            return true;
        }

        Player player = (Player) sender;
        PlayerInfo playerInfo = new PlayerInfo(player);

        if(!player.isOp()) {
            player.sendMessage(NoxisAPI.getAPIInstance().getConfigPatch("messages.commands.no_permission"));
            return true;
        }

        if(args.length == 0) {
            try { player.sendMessage(NoxisAPI.getAPIInstance().getConfigPatch("messages.commands.coins.player_look_his_coins").replace("<arg0>", playerInfo.getCoins().toString())); } catch (SQLException e) { e.printStackTrace(); }
            return true;
        } else if(args.length >= 1) {

            if(args[0].equalsIgnoreCase("add")) {

                if(args.length == 3) {

                    if(Bukkit.getPlayer(args[1]) != null) {
                        try {

                            Player target = Bukkit.getPlayer(args[1]);
                            PlayerInfo targetInfo = new PlayerInfo(target);

                            float amount = Float.valueOf(args[2]);

                            targetInfo.addCoins(amount);

                            player.sendMessage(NoxisAPI.getAPIInstance().getConfigPatch("messages.commands.coins.give_coins_to_player").replace("<amout>", Float.toString(amount).replace("<target_name>", target.getName())));

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;

                } else {

                    player.sendMessage(NoxisAPI.getAPIInstance().getConfigPatch("messages.commands.invalid_arguments_number"));
                    return true;

                }

            } else if(args[0].equalsIgnoreCase("remove")) {

                if(args.length == 3) {

                    if(Bukkit.getPlayer(args[1]) != null) {
                        try {

                            Player target = Bukkit.getPlayer(args[1]);
                            PlayerInfo targetInfo = new PlayerInfo(target);

                            float amount = Float.valueOf(args[2]);

                            targetInfo.removeCoins(amount);

                            player.sendMessage(NoxisAPI.getAPIInstance().getConfigPatch("messages.commands.coins.remove_coins_to_player").replace("<amout>", Float.toString(amount).replace("<target_name>", target.getName())));

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;

                } else {

                    player.sendMessage(NoxisAPI.getAPIInstance().getConfigPatch("messages.commands.invalid_arguments_number"));
                    return true;

                }

            } else if(args[0].equalsIgnoreCase("set")) {

                if(args.length == 3) {

                    if(Bukkit.getPlayer(args[1]) != null) {
                        try {

                            Player target = Bukkit.getPlayer(args[1]);
                            PlayerInfo targetInfo = new PlayerInfo(target);

                            float amount = Float.valueOf(args[2]);

                            targetInfo.setCoins(amount);

                            player.sendMessage(NoxisAPI.getAPIInstance().getConfigPatch("messages.commands.coins.set_coins_to_player").replace("<amout>", Float.toString(amount).replace("<target_name>", target.getName())));

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;

                } else {

                    player.sendMessage(NoxisAPI.getAPIInstance().getConfigPatch("messages.commands.invalid_arguments_number"));
                    return true;

                }

            } else if(args[0].equalsIgnoreCase("look")) {

                if(args.length == 2) {

                    if(Bukkit.getPlayer(args[1]) != null) {

                        try {
                            Player target = Bukkit.getPlayer(args[1]);
                            PlayerInfo targetInfo = new PlayerInfo(target);

                            float targetCoins = targetInfo.getCoins();

                            player.sendMessage(NoxisAPI.getAPIInstance().getConfigPatch("messages.commands.coins.player_look_coins_of_other_player").replace("<target_coins>", Float.toString(targetCoins).replace("<target_name>", target.getName())));

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;

                } else {

                    player.sendMessage(NoxisAPI.getAPIInstance().getConfigPatch("messages.commands.invalid_arguments"));
                    return true;

                }

            } else {

                player.sendMessage(NoxisAPI.getAPIInstance().getConfigPatch("messages.commands.invalid_arguments"));
                return true;

            }

        }

        return false;

    }

}
