package de.opeey.hotbarrefill;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import java.util.logging.Logger;

public class PlayerItemBreakListener implements Listener {
    private final Logger logger;

    public PlayerItemBreakListener(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent event) {
        /* Call ItemReplacer */
        ItemReplacer itemReplacer = new ItemReplacer(event.getPlayer(), this.logger);
        itemReplacer.replace(event.getBrokenItem());
    }
}
