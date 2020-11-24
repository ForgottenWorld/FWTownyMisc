package it.forgottenworld.fwtownymisc.fwtownymisc.listener;

import com.palmergames.bukkit.towny.event.TownUpkeepCalculationEvent;
import it.forgottenworld.fwtownymisc.fwtownymisc.FWTownyMisc;
import it.forgottenworld.fwtownymisc.fwtownymisc.event.CustomTownUpkeepCalculationEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class TownListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTownUpkeepCalculated(TownUpkeepCalculationEvent event) {
        ConfigurationSection townsTaxes = FWTownyMisc.getInstance().getCustomUpkeepList().getConfig().getConfigurationSection("towns-list");
        assert townsTaxes != null;
        if (townsTaxes.contains(event.getTown().getName())) {
            ConfigurationSection townSection = townsTaxes.getConfigurationSection(event.getTown().getName());
            double newUpkeep = event.getUpkeep() * townSection.getDouble("upkeep");
            CustomTownUpkeepCalculationEvent upkeepCalculationEvent = new CustomTownUpkeepCalculationEvent(event.getTown(), newUpkeep);
            Bukkit.getScheduler().callSyncMethod(FWTownyMisc.getInstance(),()->{
                Bukkit.getPluginManager().callEvent(upkeepCalculationEvent);
                return this;
            });
            if(!upkeepCalculationEvent.isCancelled()){
                event.setUpkeep(newUpkeep);
            }
        }
    }

}
