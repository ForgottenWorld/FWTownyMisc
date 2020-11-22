package it.forgottenworld.fwtownymisc.fwtownymisc;

import it.forgottenworld.fwtownymisc.fwtownymisc.listener.ResidentListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class FWTownyMisc extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ResidentListener(), this);
    }

}
