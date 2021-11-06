package de.opeey.hotbarrefill.listener;

import de.opeey.hotbarrefill.ItemReplacer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class PlayerItemConsumeListener implements Listener {
    private final Plugin plugin;

    public PlayerItemConsumeListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        ItemReplacer itemReplacer = new ItemReplacer(this.plugin, event.getPlayer());
        itemReplacer.replace(event.getItem());
    }
}
