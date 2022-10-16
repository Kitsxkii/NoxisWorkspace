package fr.kitsxki_.noxisapi;

import fr.kitsxki_.noxisapi.listeners.ConnectionsListener;
import fr.kitsxki_.noxisapi.managers.TitleManager;
import fr.kitsxki_.noxisapi.objects.NoxisPlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.kitsxki_.noxisapi.commands.CoinsCommand;
import fr.kitsxki_.noxisapi.commands.PingCommand;
import fr.kitsxki_.noxisapi.database.DatabaseManager;
import fr.kitsxki_.noxisapi.enums.PrefixMode;

import java.util.ArrayList;
import java.util.logging.Logger;

public class NoxisAPI extends JavaPlugin {

    private static NoxisAPI APIInstance;

    private static DatabaseManager databaseManager;

    private static TitleManager titleManager;

    private static PluginManager pluginManager;

    private static final ArrayList<NoxisPlayer> NoxisPlayers = new ArrayList<>();

    private static final Logger consoleLogger = Logger.getLogger("Minecraft");

    @Override
    public void onLoad() {

        APIInstance = this;

        titleManager = new TitleManager();

        pluginManager = getServer().getPluginManager();

        databaseManager = new DatabaseManager("localhost", "root", "", "noxisapi", 3306);
        databaseManager.connect();

        saveDefaultConfig();

        super.onLoad();

    }

    @Override
    public void onEnable() {

        registerEvents(getPluginManager());
        registerCommands();

        super.onEnable();

    }

    @Override
    public void onDisable() {

        databaseManager.disconnect();

        super.onDisable();

    }

    private void registerEvents(PluginManager pluginManager) {

        pluginManager.registerEvents(new ConnectionsListener(), this);

    }

    private void registerCommands() {

        getCommand("coins").setExecutor(new CoinsCommand());
        getCommand("ping").setExecutor(new PingCommand());

    }

    public String getPrefix(PrefixMode prefixMode) {
        if(prefixMode == PrefixMode.IG) {
            return "§8[§bNoxisAPI§8] §r";
        } else if(prefixMode == PrefixMode.CONSOLE) {
            return "[NoxisAPI] ";
        } else if(prefixMode == PrefixMode.DATABASEMANAGER) {
          return "[DatabaseManager] ";
        } else {
            return null;
        }
    }
    public String getConfigPatch(String patch) { return getConfig().getString(patch).replace("&", "§").replace("<retour>", "\n").replace("<e1>", "é").replace("<e2>", "è").replace("<e3>", "ê").replace("<s1>","'").replace("<ligne>", "§8§m--------§r").replace("<arrow>", "»"); }

    public static synchronized NoxisAPI getAPIInstance() { return APIInstance; }

    public static synchronized DatabaseManager getDatabaseManager() { return databaseManager; }

    public static synchronized TitleManager getTitleManager() { return titleManager; }

    public static synchronized PluginManager getPluginManager() { return pluginManager; }

    public static synchronized ArrayList<NoxisPlayer> getNoxisPlayers() { return NoxisPlayers; }

    public static synchronized Logger getConsoleLogger() { return consoleLogger; }

}
