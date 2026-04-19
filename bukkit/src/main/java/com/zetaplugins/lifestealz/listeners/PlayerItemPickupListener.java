package com.zetaplugins.lifestealz.listeners;

import com.zetaplugins.zetacore.annotations.AutoRegisterListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import com.zetaplugins.lifestealz.LifeStealZ;
import com.zetaplugins.lifestealz.util.CooldownManager;
import com.zetaplugins.lifestealz.util.MessageUtils;
import com.zetaplugins.lifestealz.util.customitems.CustomItemManager;
import com.zetaplugins.lifestealz.util.customitems.CustomItemType;

import static com.zetaplugins.lifestealz.util.MessageUtils.formatTime;

@AutoRegisterListener
public final class PlayerItemPickupListener implements Listener {
    private final LifeStealZ plugin;

    public PlayerItemPickupListener(LifeStealZ plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ItemStack itemStack = event.getItem().getItemStack();

        if (CustomItemManager.isForbiddenItem(itemStack)) {
            event.getItem().remove();
            event.setCancelled(true);
            return;
        }

        boolean heartGainCooldownEnabled = plugin.getConfig().getBoolean("heartGainCooldown.enabled");
        long heartGainCooldown = plugin.getConfig().getLong("heartGainCooldown.cooldown");
        boolean heartGainCooldownPreventPickup = plugin.getConfig().getBoolean("heartGainCooldown.preventPickup");

        if (!CustomItemType.HEART.is(itemStack)) return;

        if (
                heartGainCooldownEnabled && heartGainCooldownPreventPickup
                        && CooldownManager.lastHeartGain.get(player.getUniqueId()) != null
                        && CooldownManager.lastHeartGain.get(player.getUniqueId()) + heartGainCooldown > System.currentTimeMillis()
        ) {
            event.setCancelled(true);

            if (
                    CooldownManager.lastHeartPickupMessage.get(player.getUniqueId()) == null
                            || CooldownManager.lastHeartPickupMessage.get(player.getUniqueId()) + 1000 < System.currentTimeMillis()
            ) {
                long timeLeft = (CooldownManager.lastHeartGain.get(player.getUniqueId()) + heartGainCooldown - System.currentTimeMillis()) / 1000;
                player.sendMessage(MessageUtils.getAndFormatMsg(
                        false,
                        "heartGainCooldown",
                        "<red>You have to wait before gaining another heart!",
                        new MessageUtils.Replaceable("%time%", formatTime(timeLeft))
                ));
                CooldownManager.lastHeartPickupMessage.put(player.getUniqueId(), System.currentTimeMillis());
            }

            return;
        }

        CooldownManager.lastHeartGain.put(player.getUniqueId(), System.currentTimeMillis());
    }
}
