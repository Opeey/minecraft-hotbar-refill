package de.opeey.hotbarrefill;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemReplacer {
    private final Plugin plugin;
    private final Logger logger;

    private final PlayerInventory playerInventory;
    private final String playerName;

    public ItemReplacer(Plugin plugin, Player player) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();

        this.playerInventory = player.getInventory();
        this.playerName = player.getName();
    }

    /**
     * Finds a replacement ItemStack in the PlayerInventory of the given Material.
     * The replacement item has to be outside the HotBar.
     *
     * @return A map entry containing the slot of the replacement item as key and the ItemStack as value or null if no
     * replacement item could be found.
     */
    public Map.Entry<Integer, ? extends ItemStack> findReplacementItem(Material material) {
        HashMap<Integer, ? extends ItemStack> sameItems = this.playerInventory.all(material);

        if (0 >= sameItems.size() - 1) {
            return null;
        }

        /* Get first item of same type, which is not in the hotbar */
        Optional<? extends Map.Entry<Integer, ? extends ItemStack>> optionalEntry = sameItems.entrySet().stream().filter(entry -> entry.getKey() > 8).findFirst();

        return optionalEntry.orElse(null);
    }

    /**
     * Replaces the given ItemStack in the PlayerInventory with an item of the same Material, which is not in the HotBar.
     * If no replacement ItemStack can be found, nothing is done.
     *
     * The given item is only replaced, if there is only 1 item remaining in the stack.
     */
    public void replace(ItemStack item) {
        Material material = item.getType();
        String materialName = material.name();

        /* Abort if more than 1 item is remaining in stack. */
        if (1 != item.getAmount()) {
            return;
        }

        /* Find item in players inventory. */
        int itemSlot = this.playerInventory.first(item);
        if (-1 == itemSlot) {
            this.logError("Trying to replace non existent item of type " + materialName + ".");

            return;
        }

        /* Abort with error, if given item is not in hotbar. */
        if (8 < itemSlot) {
            this.logError("Item was found in slot " + itemSlot + ", which is not in HotBar.");

            return;
        }

        Map.Entry<Integer, ? extends ItemStack> replacementItemEntry = this.findReplacementItem(material);

        if (null == replacementItemEntry) {
            return;
        }

        Integer replacementItemSlot = replacementItemEntry.getKey();

        scheduleDelayedItemSwap(itemSlot, replacementItemSlot);
    }

    /**
     * Uses the scheduler of the plugin to swap the given slots in the PlayerInventory.
     * The item swap is delayed by 1 tick.
     *
     * Be aware that the swapped ItemStack's are determined after the delay and not when the function is called.
     */
    public void scheduleDelayedItemSwap(int firstSlot, int secondSlot) {
        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            ItemStack firstItem = this.playerInventory.getItem(firstSlot);
            ItemStack secondItem = this.playerInventory.getItem(secondSlot);

            this.playerInventory.setItem(firstSlot, secondItem);
            this.playerInventory.setItem(secondSlot, firstItem);
        }, 1);
    }

    private void logError(String message) {
        this.logger.log(Level.SEVERE, "(" + this.playerName + ") - " + message);
    }
}
