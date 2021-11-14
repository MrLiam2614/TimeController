package me.mrliam2614.TimeController.controls;

import me.mrliam2614.TimeController.TimeController;
import me.mrliam2614.TimeController.events.EventTimeSkip;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class timeEvent implements Listener {

    private final TimeController plugin;

    public timeEvent(TimeController plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTimeChangeEvent(EventTimeSkip e) {
        World worldReq = e.getWorld();
        if (plugin.controller.timeWorldList.contains(worldReq.getName())) {
            e.setCancelled(true);
        }
    }
}
