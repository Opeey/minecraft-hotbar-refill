package de.opeey.hotbarrefill;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemReplacer {
    private final PlayerInventory playerInventory;
    private final String playerName;
    private final Logger logger;

    public ItemReplacer(Player player, Logger logger) {
        this.playerInventory = player.getInventory();
        this.playerName = player.getName();
        this.logger = logger;
    }

    public Map.Entry<Integer, ? extends ItemStack> findReplacementItem(Material material) {
        HashMap<Integer, ? extends ItemStack> sameItems = this.playerInventory.all(material);

        if (0 >= sameItems.size() - 1) {
            return null;
        }

        /* Get first item of same type, which is not in the hotbar */
        Optional<? extends Map.Entry<Integer, ? extends ItemStack>> optionalEntry = sameItems.entrySet().stream().filter(entry -> entry.getKey() > 8).findFirst();

        return optionalEntry.orElse(null);
    }

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
        ItemStack replacementItemStack = replacementItemEntry.getValue();

        /* Put found item in hotbar slot and empty original slot */
        this.playerInventory.setItem(itemSlot, replacementItemStack);
        this.playerInventory.setItem(replacementItemSlot, null);
    }

    private void logError(String message) {
        this.logger.log(Level.SEVERE, "(" + this.playerName + ") - " + message);
    }
}
