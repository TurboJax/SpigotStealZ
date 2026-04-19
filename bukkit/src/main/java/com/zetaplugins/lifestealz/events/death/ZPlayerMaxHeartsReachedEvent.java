package com.zetaplugins.lifestealz.events.death;

import com.zetaplugins.lifestealz.events.ZPlayerDeathEventBase;
import com.zetaplugins.lifestealz.util.MessageUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ZPlayerMaxHeartsReachedEvent extends ZPlayerDeathEventBase {
    @Getter
    private final Player killer;
    
    @Getter
    private final double maxHeartsLimit;
    
    @Getter @Setter
    private boolean shouldDropHeartsInstead;
    
    @Getter @Setter
    private String maxHeartsMessage;

    public ZPlayerMaxHeartsReachedEvent(PlayerDeathEvent originalEvent, Player killer, double maxHearts) {
        super(originalEvent);
        this.killer = killer;
        this.maxHeartsLimit = maxHearts;
        this.shouldDropHeartsInstead = false;
        this.maxHeartsMessage = MessageUtils.getAndFormatMsg(
                false,
                "maxHeartLimitReached",
                "<red>You already reached the limit of %limit% hearts!",
                new MessageUtils.Replaceable("%limit%", String.valueOf((int) (maxHearts / 2)))
        );
    }
}