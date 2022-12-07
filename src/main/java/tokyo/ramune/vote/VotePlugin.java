package tokyo.ramune.vote;

import it.sauronsoftware.cron4j.Scheduler;
import net.md_5.bungee.api.plugin.Plugin;
import tokyo.ramune.vote.command.VoteCommand;
import tokyo.ramune.vote.command.VoteTopCommand;
import tokyo.ramune.vote.vote.VoteHandler;

public final class VotePlugin extends Plugin {
    private static VotePlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        startAutoResetSchedule();
        registerCommands();

        getLogger().info("The plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin has been disabled.");
    }

    public void startAutoResetSchedule() {
        Scheduler scheduler = new Scheduler();

        scheduler.schedule("0 0 * * *", VoteHandler::resetVote);
        scheduler.start();
    }

    public void registerCommands() {
        getProxy().getPluginManager().registerCommand(this, new VoteCommand());
        getProxy().getPluginManager().registerCommand(this, new VoteTopCommand());
    }

    public static VotePlugin getInstance() {
        return instance;
    }
}
