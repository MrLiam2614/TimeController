package me.mrliam2614.TimeController;

import me.mrliam2614.FacilitisAPI.FacilitisAPI;
import me.mrliam2614.FacilitisAPI.config.FConfig;
import me.mrliam2614.TimeController.commands.Commands;
import me.mrliam2614.TimeController.controls.controller;
import me.mrliam2614.TimeController.controls.timeEvent;
import me.mrliam2614.TimeController.controls.weatherEvent;
import me.mrliam2614.TimeController.utils.TimeConverter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Time;


public class TimeController extends JavaPlugin {

    public FacilitisAPI facilitisAPI = (FacilitisAPI) Bukkit.getServer().getPluginManager().getPlugin("FacilitisAPI");
    public FConfig world;
    public controller controller;
    public TimeConverter timeConverter;

    @Override
    public void onEnable() {

        saveConfig();
        saveDefaultConfig();
        facilitisAPI.messages.EnableMessage(this);
        world = new FConfig(this, "worlds.yml");
        world.saveConfig();

        controller = new controller(this);
        timeConverter = new TimeConverter();

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
