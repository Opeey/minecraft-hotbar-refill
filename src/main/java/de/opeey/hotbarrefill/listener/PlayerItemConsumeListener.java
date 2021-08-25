package de.opeey.hotbarrefill.listener;

import de.opeey.hotbarrefill.ItemReplacer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerItemConsumeListener implements Listener {
    private final Logger logger;

    public PlayerItemConsumeListener(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        ItemReplacer itemReplacer = new ItemReplacer(player, this.logger);

        ItemStack consumedItem = event.getItem();

        /* Check if only 1 consumable is remaining */
        if (1 != consumedItem.getAmount()) {
            return;
        }

        Map.Entry<Integer, ? extends ItemStack> replacementItemEntry = itemReplacer.findReplacementItem(consumedItem.getType());

        if (null == replacementItemEntry) {
            return;
        }

        Integer replacementItemSlot = replacementItemEntry.getKey();
        ItemStack replacementItemStack = replacementItemEntry.getValue();

        /* Increment amount, because it will be lowered by one after consuming */
        replacementItemStack.setAmount(replacementItemStack.getAmount() + 1);

        event.setItem(replacementItemStack);
        playerInventory.setItem(replacementItemSlot, null);
    }
}
