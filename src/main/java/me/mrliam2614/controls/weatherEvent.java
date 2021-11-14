package me.mrliam2614.controls;

import me.mrliam2614.TimeController;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class weatherEvent implements Listener {
    private final TimeController plugin;

    public weatherEvent(TimeController plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        World worldReq = e.getWorld();
        if (plugin.controller.weatherWorldList.contains(worldReq.getName())) {
            e.setCancelled(true);
        }
    }
}
