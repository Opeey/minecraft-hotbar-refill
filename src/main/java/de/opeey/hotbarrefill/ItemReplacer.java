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
    private final Player player;
    private final String playerName;
    private final Logger logger;

    public ItemReplacer(Player player, Logger logger) {
        this.player = player;
        this.playerName = player.getName();
        this.logger = logger;
    }

    public void replace(ItemStack item) {
        PlayerInventory playerInventory = player.getInventory();

        Material material = item.getType();
        String materialName = material.name();

        int remainingItems = item.getAmount() - 1;

        /* Abort if item stack is not depleted. */
        if (0 < remainingItems) {
            return;
        }

        /* Find item in players inventory. */
        int itemSlot = playerInventory.first(item);
        if (-1 == itemSlot) {
            this.logError("Trying to replace non existent item of type " + materialName + ".");

            return;
        }

        /* Abort with error, if given item is not in hotbar. */
        if (8 < itemSlot) {
            this.logError("Item was found in slot " + itemSlot + ", which is not in HotBar.");

            return;
        }

        /* Find other items of same type in inventory */
        HashMap<Integer, ? extends ItemStack> sameItems = playerInventory.all(material);

        if (0 >= sameItems.size() - 1) {
            return;
        }

        /* Get first item of same type, which is not in the hotbar */
        Optional<? extends Map.Entry<Integer, ? extends ItemStack>> optionalEntry = sameItems.entrySet().stream().filter(entry -> entry.getKey() > 8).findFirst();
        if (!optionalEntry.isPresent()) {
            return;
        }

        Map.Entry<Integer, ? extends ItemStack> replacementItemEntry = optionalEntry.get();

        Integer replacementItemSlot = replacementItemEntry.getKey();
        ItemStack replacementItemStack = replacementItemEntry.getValue();

        /* Put found item in hotbar slot and empty original slot */
        playerInventory.setItem(itemSlot, replacementItemStack);
        playerInventory.setItem(replacementItemSlot, null);
    }

    private void logError(String message) {
        this.logger.log(Level.SEVERE, "(" + this.playerName + ") - " + message);
    }
}
