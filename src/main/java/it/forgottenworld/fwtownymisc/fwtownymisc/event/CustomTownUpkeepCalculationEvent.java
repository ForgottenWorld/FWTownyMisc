package it.forgottenworld.fwtownymisc.fwtownymisc.event;

import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CustomTownUpkeepCalculationEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Town town;
    private double upkeep;
    private boolean cancelled = false;

    public CustomTownUpkeepCalculationEvent(Town town, double upkeep) {
        this.town = town;
        this.upkeep = upkeep;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public Town getTown() {
        return town;
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
