package me.dreamdevs.github.claimdrop.api.database;

import java.util.UUID;

public abstract class DatabaseConnector {

    public abstract void connect();

    public abstract void disconnect();

    public abstract void saveData();

    public abstract void loadData();

    public abstract boolean isAccount(UUID account);

    public abstract void insertData(UUID account);

}