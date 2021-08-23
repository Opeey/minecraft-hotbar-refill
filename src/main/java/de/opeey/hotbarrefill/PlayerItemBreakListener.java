package de.opeey.hotbarrefill;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import java.util.logging.Logger;

public record PlayerItemBreakListener(Logger logger) implements Listener {
    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent event) {
        /* Call ItemReplacer */
        ItemReplacer itemReplacer = new ItemReplacer(event.getPlayer(), this.logger);
        itemReplacer.replace(event.getBrokenItem());
    }
}
