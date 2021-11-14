package me.mrliam2614.controls;

import me.mrliam2614.TimeController;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class controller {
    private final TimeController plugin;
    public List<String> weatherType;
    public List<String> weatherWorldList;
    public List<String> timeWorldList;

    public controller(TimeController plugin) {
        this.plugin = plugin;
        weatherType = new ArrayList<>();
        timeWorldList = new ArrayList<>();

        weatherType.add("clean");
        weatherType.add("sun");
        weatherType.add("rain");
        weatherType.add("thunder");
        weatherWorldList = new ArrayList<>();
    }

    public void setTime() {
        if(!plugin.world.getConfig().isSet("worlds"))
            return;
        Set<String> worlds = plugin.world.getConfig().getConfigurationSection("worlds").getKeys(false);
        for (String world : worlds) {
            if (weatherWorldList.contains(world))
                weatherWorldList.remove(world);

            if(timeWorldList.contains(world))
                timeWorldList.remove(world);

            World selWorld = Bukkit.getWorld(world);

            //Set time
            if (plugin.world.getConfig().isSet("worlds." + world + ".time")) {
                long time = plugin.world.getConfig().getLong("worlds." + world + ".time");
                selWorld.setTime(time);
                selWorld.setGameRuleValue("doDaylightCycle", "false");
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    timeWorldList.add(world);
                }, 2);
            }


            //Set weather
            if (plugin.world.getConfig().isSet("worlds." + world + ".weather")) {
                String weather = plugin.world.getConfig().getString(("worlds." + world + ".weather")).toLowerCase();

                if (weatherType.contains(weather)) {
                    if (weather.equalsIgnoreCase("rain")) {
                        selWorld.setStorm(true);
                        selWorld.setThundering(false);
                    }
                    if (weather.equalsIgnoreCase("thunder")) {
                        selWorld.setStorm(true);
                        selWorld.setThundering(true);
                    }
                    if (weather.equalsIgnoreCase("sun") || weather.equalsIgnoreCase("clean")) {
                        selWorld.setStorm(false);
                        selWorld.setThundering(false);
                    }
                }
                weatherWorldList.add(world);
            }
        }
    }
}
