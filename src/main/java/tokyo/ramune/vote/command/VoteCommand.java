package tokyo.ramune.vote.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import tokyo.ramune.vote.VotePlugin;
import tokyo.ramune.vote.util.Chat;
import tokyo.ramune.vote.vote.VoteHandler;

import java.util.Set;
import java.util.UUID;

public class VoteCommand extends Command {
    public VoteCommand() {
        super("vote");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            VotePlugin.getInstance().getLogger()
                    .warning("oops. You cannot send /vote command from here." +
                            "\nPlease send as " + ProxiedPlayer.class.getName());
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length < 1) {
            Chat.sendMessage(player, ChatColor.RED + "コマンド引数が正しくありません。 正しくは\"/vote <プレイヤー名>\"です");
            return;
        }

        ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(args[0]);

        if (targetPlayer == null) {
            Chat.sendMessage(player, ChatColor.RED + "投票するプレイヤーが見つかりませんでした。 サーバーに参加したことがない、もしくは存在しないプレイヤー名です。");
            return;
        }

        if (VoteHandler.isVoted(player.getUniqueId())) {
            Chat.sendMessage(player, ChatColor.RED + "※注意 一人一つの投票しかできません。以前投票したプレイヤーへの投票は無くなります。");
        }


        VoteHandler.vote(player.getUniqueId(), targetPlayer.getUniqueId());

        Set<UUID> voters = VoteHandler.getVoters(targetPlayer.getUniqueId());
        Chat.sendMessage(player, ChatColor.GREEN + "そのプレイヤーへの投票が完了しました。 現在のそのプレイヤーの投票数は" + (voters == null ? -1 : voters.size()) + "です。");
    }
}
