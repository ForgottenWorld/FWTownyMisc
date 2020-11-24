package it.forgottenworld.fwtownymisc.fwtownymisc.listener;

import com.palmergames.bukkit.towny.event.NationUpkeepCalculationEvent;
import it.forgottenworld.fwtownymisc.fwtownymisc.FWTownyMisc;
import it.forgottenworld.fwtownymisc.fwtownymisc.event.CustomNationUpkeepCalculationEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class NationListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onNationUpkeepCalculated(NationUpkeepCalculationEvent event) {
        ConfigurationSection nationsTaxes = FWTownyMisc.getInstance().getCustomUpkeepList().getConfig().getConfigurationSection("nations-list");
        assert nationsTaxes != null;
        if (nationsTaxes.contains(event.getNation().getName())) {
            ConfigurationSection nationSection = nationsTaxes.getConfigurationSection(event.getNation().getName());
            double newUpkeep = event.getUpkeep() * nationSection.getDouble("upkeep");
            CustomNationUpkeepCalculationEvent upkeepCalculationEvent = new CustomNationUpkeepCalculationEvent(event.getNation(), newUpkeep);
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
