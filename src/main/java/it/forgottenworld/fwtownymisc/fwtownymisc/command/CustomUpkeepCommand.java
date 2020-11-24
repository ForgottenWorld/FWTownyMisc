package it.forgottenworld.fwtownymisc.fwtownymisc.command;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import it.forgottenworld.fwtownymisc.fwtownymisc.FWTownyMisc;
import it.forgottenworld.fwtownymisc.fwtownymisc.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class  CustomUpkeepCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            if(!sender.isOp() || !sender.hasPermission("ctu.admin")){
                sender.sendMessage(ChatColor.RED + "You don't have the permissions to run that command!");
                return true;
            }
        }
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("reload")){
                try{
                    FWTownyMisc.getInstance().loadConfig();
                    sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
                }catch (IOException e){
                    sender.sendMessage(ChatColor.DARK_RED + "Error while reloading the config. See console for further information");
                    e.printStackTrace();
                }
            }else if(args[0].equalsIgnoreCase("set")){
                if(args.length >= 4){
                    if(args[1].equalsIgnoreCase("town")){
                        try {
                            Town town = TownyAPI.getInstance().getDataSource().getTown(args[2]);
                            Config config = FWTownyMisc.getInstance().getCustomUpkeepList();
                            ConfigurationSection townConfig = config.getConfig().getConfigurationSection("towns-list").createSection(town.getName());
                            townConfig.set("upkeep", Double.parseDouble(args[3]));
                            if(args.length > 4){
                                townConfig.set("override", Boolean.parseBoolean(args[4]));
                            }
                            config.save();
                        } catch (NotRegisteredException e) {
                            sender.sendMessage(ChatColor.RED + "Invalid town name");
                        }
                    }else if(args[1].equalsIgnoreCase("nation")){
                        try{
                            Nation nation = TownyAPI.getInstance().getDataSource().getNation(args[2]);
                            Config config = FWTownyMisc.getInstance().getCustomUpkeepList();
                            ConfigurationSection nationConfig = config.getConfig().getConfigurationSection("nations-list").createSection(nation.getName());
                            nationConfig.set("upkeep", Double.parseDouble(args[3]));
                            if(args.length > 4){
                                nationConfig.set("override", Boolean.parseBoolean(args[4]));
                            }
                            config.save();
                        }catch (NotRegisteredException e){
                            sender.sendMessage(ChatColor.RED + "Invalid nation name");
                        }
                    }else{
                        if(sender instanceof Player) sendHelp((Player)sender);
                    }
                }
            }else{
                if(sender instanceof Player) sendHelp((Player)sender);
            }
        }else{
            if(sender instanceof Player) sendHelp((Player)sender);
        }
        return true;
    }

    private void sendHelp(Player player){
        player.sendMessage(ChatColor.DARK_GREEN + "------{ CustomTownyUpkeep }------");
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "/ctu reload" + ChatColor.RESET + "" +
                ChatColor.GREEN + ": reloads the config");
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "/ctu set town <town> <multiplier> [override]" + ChatColor.RESET + "" +
                ChatColor.GREEN + ": sets a custom upkeep");
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "/ctu set nation <nation> <multiplier> [override]" + ChatColor.RESET + "" +
                ChatColor.GREEN + ": sets a custom upkeep");
        player.sendMessage(ChatColor.DARK_GREEN + "---------------------------------");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 1){
            return Arrays.asList("reload","set");
        }else if(args.length == 2 && args[0].equalsIgnoreCase("set")){
            return Arrays.asList("town", "nation");
        }else if(args.length == 3) {
            if (args[1].equalsIgnoreCase("town")) {
                return Arrays.asList(TownyAPI.getInstance().getDataSource().getTowns().stream().map(Town::getName).toArray(String[]::new));
            } else if (args[1].equalsIgnoreCase("nation")) {
                return Arrays.asList(TownyAPI.getInstance().getDataSource().getNations().stream().map(Nation::getName).toArray(String[]::new));
            }
        }else if(args.length == 4){
            return Collections.singletonList("1");
        }else if(args.length == 5){
            return Arrays.asList("false", "true");
        }
        return Collections.singletonList("");
    }
}