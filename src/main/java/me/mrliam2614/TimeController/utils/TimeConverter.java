package me.mrliam2614.TimeController.utils;

public class TimeConverter {
    public TimeConverter(){

    }
    public long TimeToInt(String time){
        long tick;
        String hours = time.split(":")[0].substring(0, 2);
        String minutes = time.split(":")[1].substring(0, 2);
        int h = Integer.parseInt(hours);
        int m = Integer.parseInt(minutes);

        if (h >= 24) h = 0;
        if (m >= 60) m = 0;
        tick = 18000;
        tick += m * 16L;
        tick += h * 1000L;

        if (tick > 24000) {
            tick -= 24000;
        }

        return tick;
    }
    
    public String IntToTime(long ticks){
        String time = "";
        ticks += 6000;
        if(ticks > 24000){
            ticks -= 24000;
        }
        long hours = ticks / 1000L;
        long rem = ticks % 1000L;
        long minutes = rem / 16L;
        time = (hours < 10 ? "0" : "")  + hours + ":" + (minutes < 10 ? "0" : "") + minutes;

        return time;
    }
}
