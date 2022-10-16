package fr.kitsxki_.noxisapi.database;

import fr.kitsxki_.noxisapi.NoxisAPI;
import fr.kitsxki_.noxisapi.enums.PrefixMode;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;

public class DatabaseManager {

    private final String host;
    private final String user;
    private final String pass;
    private final String databaseName;

    private final int port;

    private static Connection connection;

    public DatabaseManager(String host, String user, String pass, String databaseName, int port) {

        this.host = host;
        this.user = user;
        this.pass = pass;
        this.databaseName = databaseName;

        this.port = port;

    }

    public void connect() {

        if(!isOnline()) {

            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + databaseName, this.user, this.pass);
                System.out.println(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.DATABASEMANAGER) + "The database has been connected.");
            } catch (SQLException e) { e.printStackTrace(); }

        }

    }

    public void disconnect() {

        if(isOnline()) {

            try {
                connection.close();
                System.out.println(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.DATABASEMANAGER) + "The database has been disconnected.");
            } catch (SQLException e) { e.printStackTrace(); }

        }

    }

    public void createAccount(UUID uuid) {

        try {

            if(hasAccount(uuid, true)) {

                NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.DATABASEMANAGER) + "The player " + Bukkit.getPlayer(uuid).getName() + " does not already have an account.");

                String stringUUID = uuid.toString();
                String pseudo = Bukkit.getPlayer(uuid).getName();

                PreparedStatement createAccount = getConnection().prepareStatement("INSERT INTO players (uuid, pseudo, coins) VALUES (?, ?, ?)");

                createAccount.setString(1, stringUUID);
                createAccount.setString(2, pseudo);
                createAccount.setFloat(3, 500F);
                createAccount.execute();
                createAccount.close();

                NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.DATABASEMANAGER) + "An account has been created.");

                PreparedStatement getID = getConnection().prepareStatement("SELECT id FROM players WHERE uuid = ?");
                getID.setString(1, stringUUID);

                int ID = 0;

                ResultSet resultSet = getID.executeQuery();

                while(resultSet.next()) {
                    ID = resultSet.getInt("id");
                }
                getID.close();

                NoxisAPI.getConsoleLogger().info("ID: " + ID + ", PSEUDO: " + pseudo + ", COINS: 500.");

            } else {
                updateAccount(uuid);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateAccount(UUID uuid) {

        String actualPseudo = Bukkit.getPlayer(uuid).getName();

        try {

            if(hasAccount(uuid, false)) {
                NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.DATABASEMANAGER) + "The player " + actualPseudo + " does not already have an account, so it is not possible to update his account.");
            } else {

                PreparedStatement updateAccount = getConnection().prepareStatement("UPDATE players SET pseudo = ? WHERE uuid = ?");
                updateAccount.setString(1, Bukkit.getPlayer(uuid).getName());
                updateAccount.setString(2, uuid.toString());
                updateAccount.executeUpdate();
                updateAccount.close();

                NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.DATABASEMANAGER) + "The nickname of " + Bukkit.getPlayer(uuid).getName() + " has been updated.");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean hasAccount(UUID uuid, boolean returnMessage) {

        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT coins FROM players WHERE uuid = ?");
            preparedStatement.setString(1, uuid.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                if(returnMessage) {
                    NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.DATABASEMANAGER) + "Player " + Bukkit.getPlayer(uuid).getName() + " already has an account.");
                }
                return false;
            }
            if(returnMessage) {
                NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.DATABASEMANAGER) + "The player " + Bukkit.getPlayer(uuid).getName() + " does not already have an account.");
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(returnMessage) {
            NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.DATABASEMANAGER) + "The player " + Bukkit.getPlayer(uuid).getName() + " does not already have an account.");
        }
        return true;

    }

    public boolean isOnline() {

        try {
            if((connection == null) || (connection.isClosed())) {
                NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.DATABASEMANAGER) + "The database is not online.");
                return false;
            }
            NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.DATABASEMANAGER) + "The database is online.");
            return true;
        } catch (SQLException e) { e.printStackTrace(); }
        NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.DATABASEMANAGER) + "The database is not online.");
        return false;

    }

    public static Connection getConnection() { return connection; }

}
