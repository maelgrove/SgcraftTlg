package com.gmail.maelgrove.SgcraftTlg.Server.Events;

import com.gmail.maelgrove.SgcraftTlg.Core.Telegram.Methods.SendMessage;
import com.gmail.maelgrove.SgcraftTlg.Core.Telegram.TelegramBot;
import com.gmail.maelgrove.SgcraftTlg.PluginConfig;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;


/**
 * Represents a listener for entity damage events.
 */
public class EntityDamageListener implements Listener {

    private static final String PLAYER_LAVA = "%s's ass on fire.";

    private static final String PLAYER_SUICIDE = "%s tried to do an hero.";

    private static final String PLAYER_FALLING_BLOCK = "%s: Oof ouch owie my head.";

    private static final String PLAYER_FALL = "%s thought they could fly.";

    private PluginConfig config;
    private TelegramBot bot;

    public EntityDamageListener(PluginConfig config, TelegramBot bot) {
        this.config = config;
        this.bot    = bot;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {

        if (!bot.isReady() || !config.isDamageEventsEnabled())
            return;

        Entity entity = event.getEntity();
        if(!(entity instanceof Player))
            return;

        String messageFormat = "";
        switch (event.getCause()) {
            case FALL:
                messageFormat = PLAYER_FALL;
            case LAVA:
                messageFormat = PLAYER_LAVA;
            case SUICIDE:
                messageFormat = PLAYER_SUICIDE;
            case FALLING_BLOCK:
                messageFormat = PLAYER_FALLING_BLOCK;
            default:
                break;
        }

        String message = String.format(messageFormat, entity.getName());
        bot.sendMessage(new SendMessage()
                .setChatId(config.getEventChatId())
                .setText(message));
    }

}
