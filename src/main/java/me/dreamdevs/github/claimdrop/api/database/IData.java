package me.dreamdevs.github.claimdrop.api.database;

import org.bukkit.entity.Player;

public interface IData {

    void connectDatabase();

    void disconnectDatabase();

    void saveData(Player player);

    void loadData(Player player);

}