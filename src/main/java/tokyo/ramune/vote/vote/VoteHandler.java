package tokyo.ramune.vote.vote;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class VoteHandler {
    private static HashMap<UUID, Set<UUID>> votes = new HashMap<>();

    public static void vote(@Nonnull UUID voterUniqueId, @Nonnull UUID targetPlayerUniqueId) {
        UUID votedPlayerUniqueId = getVoted(voterUniqueId);

        if (votedPlayerUniqueId != null) {
            Set<UUID> voters = getVoters(targetPlayerUniqueId);
            if (voters == null) voters = new HashSet<>();

            voters.remove(voterUniqueId);

            votes.replace(votedPlayerUniqueId, voters);
        }

        if (votes.containsKey(targetPlayerUniqueId)) {
            Set<UUID> voterUniqueIds = votes.get(targetPlayerUniqueId);
            voterUniqueIds.add(voterUniqueId);

            votes.replace(targetPlayerUniqueId, voterUniqueIds);
        } else {
            Set<UUID> voterUniqueIds = new HashSet<>();
            voterUniqueIds.add(voterUniqueId);

            votes.put(targetPlayerUniqueId, voterUniqueIds);
        }
    }

    public static HashMap<UUID, Set<UUID>> getVotes() {
        return votes;
    }

    @Nullable
    public static Set<UUID> getVoters(@Nonnull UUID targetUniqueId) {
        if (!votes.containsKey(targetUniqueId))
            return null;

        return votes.get(targetUniqueId);
    }

    @Nullable
    public static UUID getVoted(@Nonnull UUID voterUniqueId) {
        AtomicReference<UUID> targetUniqueId = new AtomicReference<>();

        votes.forEach((targetedUniqueId, voterUniqueIds) -> {
            if (voterUniqueIds.contains(voterUniqueId))
                targetUniqueId.set(targetedUniqueId);
        });

        return targetUniqueId.get();
    }

    public static boolean isVoted(@Nonnull UUID voterUniqueId) {
        for (Set<UUID> voterUniqueIds : votes.values()) {
            for (UUID uniqueId : voterUniqueIds) {
                if (uniqueId.equals(voterUniqueId)) return true;
            }
        }

        return false;
    }

    public static void resetVote() {
        votes = new HashMap<>();
    }
}
