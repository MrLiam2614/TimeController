package me.mrliam2614.TimeController.commands;

import me.mrliam2614.TimeController.TimeController;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Commands implements CommandExecutor, TabCompleter {
    private final TimeController plugin;

    public Commands(TimeController plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player && sender.hasPermission("timecontroller.admin")) {
            if (args.length >= 2) {
                if (args[1].equalsIgnoreCase("lock")) {
                    if (args.length >= 3) {
                        String type = args[0].toLowerCase();
                        String value = args[2].toLowerCase();
                        String world = ((Player) sender).getWorld().getName();
                        if (args.length >= 4) {
                            world = args[3];
                        }
                        if (type.equalsIgnoreCase("time")) {
                            long tick = 0;
                            if (value.contains(":")) {
                                tick = plugin.timeConverter.TimeToInt(value);
                            } else {
                                tick = Integer.parseInt(value);
                            }
                            plugin.world.getConfig().set("worlds." + world + "." + type, tick);
                            plugin.world.saveConfig();
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Time for &c" + world + " &6Setted to: &c"+tick + "&6 | &c" + plugin.timeConverter.IntToTime(tick)));
                            Objects.requireNonNull(Bukkit.getWorld(world)).setTime(tick);
                            plugin.controller.setTime();
                        }
                        if (type.equalsIgnoreCase("weather")) {
                            if (plugin.controller.weatherType.contains(value.toLowerCase())) {

                                plugin.world.getConfig().set("worlds." + world + "." + type, value);
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Weather for &c" + world + " &6Setted to: &c"+value));
                                plugin.world.saveConfig();
                                plugin.controller.setTime();
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6" + value + " &bis not a valid type of &6weather &bUse:"));
                                for (String types : plugin.controller.weatherType) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + types));
                                }
                            }
                        }
                    }
                }
                if (args[1].equalsIgnoreCase("unlock")) {
                    String type = args[0];
                    String world = ((Player) sender).getWorld().getName();
                    Objects.requireNonNull(Bukkit.getWorld(world)).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                    if (args.length >= 3) {
                        world = args[2];
                    }
                    if (type.equalsIgnoreCase("weather") || type.equalsIgnoreCase("time")) {
                        plugin.world.getConfig().set("worlds." + world + "." + type, null);
                        plugin.world.saveConfig();
                        plugin.controller.setTime();
                    }
                }
            }

        } else {
            plugin.facilitisAPI.console.sendMessage(plugin, "&4You cannot run this command from the &6Console", "info");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("timecontroller.admin")) {
                commands.add("time");
                commands.add("weather");
            }
            StringUtil.copyPartialMatches(args[0], commands, completions);
        } else if (args.length == 2) {
            if (sender.hasPermission("timecontroller.admin")) {
                commands.add("lock");
                commands.add("unlock");
            }
            StringUtil.copyPartialMatches(args[1], commands, completions);
        } else if (args.length == 4 && args[1] == "lock") {
            if (sender.hasPermission("timecontroller.admin")) {
                for (World world : Bukkit.getWorlds()) {
                    commands.add(world.getName());
                }
            }
            StringUtil.copyPartialMatches(args[3], commands, completions);
        } else if (args.length == 3 && args[1] == "unlock") {
            if (sender.hasPermission("timecontroller.admin")) {
                for (World world : Bukkit.getWorlds()) {
                    commands.add(world.getName());
                }
            }
            StringUtil.copyPartialMatches(args[2], commands, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
