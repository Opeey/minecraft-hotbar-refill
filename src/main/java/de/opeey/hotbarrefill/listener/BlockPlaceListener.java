package de.opeey.hotbarrefill.listener;

import de.opeey.hotbarrefill.ItemReplacer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BlockPlaceListener implements Listener {
    private final Plugin plugin;

    public BlockPlaceListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        ItemStack itemInHand = event.getItemInHand();

        /* Ignore items which have a durability, they are handled through PlayerItemBreakListener */
        if (0 < itemInHand.getType().getMaxDurability()) {
            return;
        }

        /* Call ItemReplacer */
        ItemReplacer itemReplacer = new ItemReplacer(this.plugin, event.getPlayer());
        itemReplacer.replace(event.getItemInHand());
    }
}
