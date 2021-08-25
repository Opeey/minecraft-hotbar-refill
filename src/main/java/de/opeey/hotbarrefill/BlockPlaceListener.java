package de.opeey.hotbarrefill;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;

public class BlockPlaceListener implements Listener {
    private final Logger logger;

    public BlockPlaceListener(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        ItemStack itemInHand = event.getItemInHand();

        /* Ignore items which have a durability, they are handled through PlayerItemBreakListener */
        if (0 < itemInHand.getType().getMaxDurability()) {
            return;
        }

        /* Call ItemReplacer */
        ItemReplacer itemReplacer = new ItemReplacer(event.getPlayer(), this.logger);
        itemReplacer.replace(event.getItemInHand());
    }
}
