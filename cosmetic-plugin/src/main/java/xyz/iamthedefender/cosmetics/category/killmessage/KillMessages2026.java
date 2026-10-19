package xyz.iamthedefender.cosmetics.category.killmessage;

import me.horiciastko.bedwars.api.events.BedWarsPlayerKillEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.category.killmessage.util.KillMessageUtils;
import xyz.iamthedefender.cosmetics.util.DebugUtil;

public class KillMessages2026 implements Listener {

    @EventHandler
    public void onPlayerKill(BedWarsPlayerKillEvent e) {

        boolean isKillMessagesEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("kill-messages.enabled");
        if (!isKillMessagesEnabled) return;

        Player killer = e.getKiller();
        Player victim = e.getVictim();

        if (killer == null || victim == null) return;
        if (e.getKillerTeam() == null || e.getVictimTeam() == null) return;

        // KILL MESSAGES!
        if (CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(killer, CosmeticsType.KillMessages).equals("Default")) {
            return;
        }

        boolean isFinalKill = e.isFinalKill();

        ChatColor killerColor = e.getKillerTeam().getColor();
        ChatColor victimColor = e.getVictimTeam().getColor();

        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(killer, CosmeticsType.KillMessages);

        DebugUtil.addMessage("Playing Kill message for " + killer.getName());

        String reason = e.getKillReason() == null ? "" : e.getKillReason().toLowerCase();
        String type;
        if (reason.contains("shot by")) {
            type = "Shoot";
        } else if (reason.contains("blown up by") || reason.contains("fireballed by") || reason.contains("was detonated")) {
            type = "Explosion";
        } else if (reason.contains("void") || reason.contains("fell")) {
            type = "Void";
        } else {
            type = "PvP";
        }

        if (KillMessageUtils.exists(selected, type) && !KillMessageUtils.isNone(selected)) {
            String message = KillMessageUtils.sendKillMessage(victim.getName(), killer, isFinalKill, victimColor, killerColor, type);
            if (message != null) {
                e.setCustomMessage(message);
            }
        }
    }
}
