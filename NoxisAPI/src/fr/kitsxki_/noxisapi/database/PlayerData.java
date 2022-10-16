package fr.kitsxki_.noxisapi.database;

import fr.kitsxki_.noxisapi.NoxisAPI;
import fr.kitsxki_.noxisapi.enums.PrefixMode;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerData {

    private final UUID uuid;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public void addCoins(float amount) throws SQLException {

        PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("UPDATE players SET coins = coins + ? WHERE uuid = ?");

        preparedStatement.setFloat(1, amount);
        preparedStatement.setString(2, uuid.toString());
        preparedStatement.executeUpdate();
        preparedStatement.close();

        NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.CONSOLE) + amount + " coins have been added to " + Bukkit.getPlayer(uuid).getName() + ".");

    }

    public void removeCoins(float amount) throws SQLException {

        PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("UPDATE players SET coins = coins - ? WHERE uuid = ?");

        preparedStatement.setFloat(1, amount);
        preparedStatement.setString(2, uuid.toString());
        preparedStatement.executeUpdate();
        preparedStatement.close();

        NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.CONSOLE) + amount + " coins have been withdrawn to " + Bukkit.getPlayer(uuid).getName() + ".");

    }

    public void setCoins(float amount) throws SQLException {

        PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("UPDATE players SET coins = ? WHERE uuid = ?");

        preparedStatement.setFloat(1, amount);
        preparedStatement.setString(2, uuid.toString());
        preparedStatement.executeUpdate();
        preparedStatement.close();

        NoxisAPI.getConsoleLogger().info(NoxisAPI.getAPIInstance().getPrefix(PrefixMode.CONSOLE) + Bukkit.getPlayer(uuid).getName() + "'s coins have been set to " + amount + ".");

    }

    public Float getCoins() throws SQLException {


        PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT coins FROM players WHERE uuid = ?");
        preparedStatement.setString(1, uuid.toString());

        ResultSet resultSet = preparedStatement.executeQuery();
        float coins = 0f;

        while(resultSet.next()) { coins = resultSet.getFloat("coins"); }
        preparedStatement.close();

        return coins;

    }

}
