package me.dreamdevs.github.claimdrop.utils;

import me.dreamdevs.github.claimdrop.ClaimDropMain;

public class Settings {

    public static boolean useDatabase;
    public static int databaseTime;
    public static String databaseHost;
    public static int databasePort;
    public static String databaseUser;
    public static String databaseName;
    public static String databaseType;
    public static String databasePassword;
    public static String messageType;

    public static void loadVars() {
        useDatabase = ClaimDropMain.getInstance().getConfig().getBoolean("database.use");
        databaseType = ClaimDropMain.getInstance().getConfig().getString("database.type");
        databaseHost = ClaimDropMain.getInstance().getConfig().getString("database.host");
        databasePort = ClaimDropMain.getInstance().getConfig().getInt("database.port");
        databasePassword = ClaimDropMain.getInstance().getConfig().getString("database.password");
        databaseUser = ClaimDropMain.getInstance().getConfig().getString("database.user");
        databaseName = ClaimDropMain.getInstance().getConfig().getString("database.database");
        databaseTime = ClaimDropMain.getInstance().getConfig().getInt("database.auto-save-time");
        messageType = ClaimDropMain.getInstance().getConfig().getString("message-type");
    }

}