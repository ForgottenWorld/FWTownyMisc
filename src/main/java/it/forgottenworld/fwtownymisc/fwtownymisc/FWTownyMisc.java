package it.forgottenworld.fwtownymisc.fwtownymisc;

import it.forgottenworld.fwtownymisc.fwtownymisc.command.CustomUpkeepCommand;
import it.forgottenworld.fwtownymisc.fwtownymisc.config.Config;
import it.forgottenworld.fwtownymisc.fwtownymisc.listener.NationListener;
import it.forgottenworld.fwtownymisc.fwtownymisc.listener.ResidentListener;
import it.forgottenworld.fwtownymisc.fwtownymisc.listener.TownListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public final class FWTownyMisc extends JavaPlugin {

    private static FWTownyMisc INSTANCE;
    private Config customUpkeepList;

    @Override
    public void onEnable() {
        registerListeners();
        registerCommands();
    }

    public static FWTownyMisc getInstance(){
        return INSTANCE;
    }

    public void loadConfig() throws IOException {
        customUpkeepList = new Config("custom-upkeep-list.yml", this);
    }

    public Config getCustomUpkeepList() {
        return customUpkeepList;
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new ResidentListener(), this);
        getServer().getPluginManager().registerEvents(new TownListener(), this);
        getServer().getPluginManager().registerEvents(new NationListener(), this);
    }

    private void registerCommands(){
        Objects.requireNonNull(getCommand("tcu")).setExecutor(new CustomUpkeepCommand());
    }
}
