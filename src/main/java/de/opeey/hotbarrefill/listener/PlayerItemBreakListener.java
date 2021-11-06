package de.opeey.hotbarrefill.listener;

import de.opeey.hotbarrefill.ItemReplacer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.plugin.Plugin;

public class PlayerItemBreakListener implements Listener {
    private final Plugin plugin;

    public PlayerItemBreakListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent event) {
        /* Call ItemReplacer */
        ItemReplacer itemReplacer = new ItemReplacer(this.plugin, event.getPlayer());
        itemReplacer.replace(event.getBrokenItem());
    }
}
