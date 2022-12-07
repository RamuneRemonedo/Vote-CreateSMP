package tokyo.ramune.vote.util;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.annotation.Nonnull;

public class Chat {
    public static void sendMessage(@Nonnull ProxiedPlayer player, @Nonnull String message) {
        player.sendMessage(new ComponentBuilder(message).create());
    }

    public static void sendMessage(@Nonnull CommandSender sender, @Nonnull String message) {
        sender.sendMessage(new ComponentBuilder(message).create());
    }
}
