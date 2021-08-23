package de.opeey.hotbarrefill;

import org.bukkit.plugin.java.JavaPlugin;

public class HotbarRefill extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerItemBreakListener(this.getLogger()), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this.getLogger()), this);
    }
}
