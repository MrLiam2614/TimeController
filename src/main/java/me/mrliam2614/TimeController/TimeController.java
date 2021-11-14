package me.mrliam2614.TimeController;

import me.mrliam2614.FacilitisAPI;
import me.mrliam2614.TimeController.commands.Commands;
import me.mrliam2614.TimeController.controls.controller;
import me.mrliam2614.TimeController.controls.timeEvent;
import me.mrliam2614.TimeController.controls.weatherEvent;
import me.mrliam2614.TimeController.events.EventCaller;
import me.mrliam2614.TimeController.utils.TimeConverter;
import me.mrliam2614.config.FConfig;
import org.bukkit.plugin.java.JavaPlugin;


public class TimeController extends JavaPlugin {

    public FacilitisAPI facilitisAPI;
    public FConfig world;
    public controller controller;
    public TimeConverter timeConverter;
    private EventCaller eventCaller;

    @Override
    public void onEnable() {
        facilitisAPI = FacilitisAPI.getInstance();

        saveConfig();
        saveDefaultConfig();
        facilitisAPI.messages.EnableMessage(this);
        world = new FConfig(this, "worlds.yml");

        controller = new controller(this);
        timeConverter = new TimeConverter();
        eventCaller = new EventCaller(this);

        Commands commands = new Commands(this);

        getCommand("timecontrol").setExecutor(commands);

        getServer().getPluginManager().registerEvents(new weatherEvent(this), this);
        getServer().getPluginManager().registerEvents(new timeEvent(this), this);

        controller.setTime();
    }

    @Override
    public void onDisable() {
        facilitisAPI.messages.DisableMessage(this);
    }
}
