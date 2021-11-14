package me.mrliam2614.events;

import me.mrliam2614.TimeController;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.HashMap;

public class EventCaller {
    private final TimeController plugin;
    private HashMap<String, Long> worldTime;

    public EventCaller(TimeController plugin) {
        this.plugin = plugin;
        eventTimeSkip();
        worldTime = new HashMap<>();
    }

    private void eventTimeSkip() {
        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    String worldName = world.getName();
                    Long time = worldTime.getOrDefault(worldName, world.getTime());
                    if (Math.abs((world.getTime() - time)) > 1) {
                        Bukkit.getPluginManager().callEvent(new EventTimeSkip(world, time));
                    }
                    worldTime.put(worldName, world.getTime());
                }
            }
        }, 1, 1);
    }
}
