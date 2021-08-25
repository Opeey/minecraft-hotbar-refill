package de.opeey.hotbarrefill;

import de.opeey.hotbarrefill.listener.BlockPlaceListener;
import de.opeey.hotbarrefill.listener.PlayerItemBreakListener;
import de.opeey.hotbarrefill.listener.PlayerItemConsumeListener;
import org.bukkit.plugin.java.JavaPlugin;

public class HotbarRefill extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerItemBreakListener(this.getLogger()), this);
        getServer().getPluginManager().registerEvents(new PlayerItemConsumeListener(this.getLogger()), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this.getLogger()), this);
    }
}
