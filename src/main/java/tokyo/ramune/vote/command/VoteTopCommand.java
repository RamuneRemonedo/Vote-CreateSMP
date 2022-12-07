package tokyo.ramune.vote.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import tokyo.ramune.vote.util.Chat;
import tokyo.ramune.vote.vote.VoteHandler;

import java.util.*;

public class VoteTopCommand extends Command {
    public VoteTopCommand() {
        super("votetop");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (VoteHandler.getVotes().isEmpty()) {
            Chat.sendMessage(sender, ChatColor.RED + "まだ投票されていません。");
            return;
        }

        StringBuilder message = new StringBuilder(ChatColor.WHITE + "[" + ChatColor.GREEN + "投票" + ChatColor.AQUA + "ランキング" + ChatColor.WHITE + "]");

        Comparator<Integer> comparator = Integer::compareTo;
        Map<Integer, UUID> map = new TreeMap<>(comparator);

        VoteHandler.getVotes().forEach((targetUniqueId, voterUniqueIds) -> map.put(voterUniqueIds.size(), targetUniqueId));

        int ranking = 0;
        for (Map.Entry<Integer, UUID> entry : map.entrySet()) {
            ranking++;
            message.append("\n").append(ChatColor.AQUA).append(ranking).append("位 ").append(ChatColor.GRAY).append(ProxyServer.getInstance().getPlayer(entry.getValue()).getName()).append("さん - ").append(entry.getKey()).append("票");
        }

        Chat.sendMessage(sender, message.toString());
    }
}
