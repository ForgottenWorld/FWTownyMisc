package it.forgottenworld.fwtownymisc.fwtownymisc.listener;

import com.earth2me.essentials.Essentials;
import com.palmergames.bukkit.towny.event.TownRemoveResidentEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ResidentListener implements Listener {

    @EventHandler
    public void onResidentLeave(TownRemoveResidentEvent event){
        if(event.getResident().getTownRanks().contains("elite")){
            Essentials.getPlugin(Essentials.class).getOfflineUser("goldr31").addMail(event.getResident().getName() + " ha abbandonato la città ed era un elitè");
        }
    }

}
