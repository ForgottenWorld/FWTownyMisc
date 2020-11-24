package it.forgottenworld.fwtownymisc.fwtownymisc.event;

import com.palmergames.bukkit.towny.event.NationUpkeepCalculationEvent;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CustomNationUpkeepCalculationEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Nation nation;
    private double upkeep;
    private boolean cancelled = false;

    public CustomNationUpkeepCalculationEvent(Nation nation, double upkeep) {
        this.nation = nation;
        this.upkeep = upkeep;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public Nation getNation() {
        return nation;
    }

    public double getUpkeep() {
        return upkeep;
    }

    public void setUpkeep(double upkeep) {
        this.upkeep = upkeep;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
