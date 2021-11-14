package me.mrliam2614.events;

import org.bukkit.World;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EventTimeSkip extends Event implements Cancellable {
    public static HandlerList HANDLERS_LIST = new HandlerList();
    private World world;
    private Long time;

    public EventTimeSkip(World world, Long time){
        this.world = world;
        this.time = time;
    }

    @Override
    public boolean isCancelled() {
        return true;
    }

    @Override
    public void setCancelled(boolean cancel) {
        getWorld().setTime(time);
    }

    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS_LIST;
    }

    public World getWorld(){
        return world;
    }
}
