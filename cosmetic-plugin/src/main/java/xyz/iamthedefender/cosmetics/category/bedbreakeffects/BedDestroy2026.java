package xyz.iamthedefender.cosmetics.category.bedbreakeffects;

import me.horiciastko.bedwars.api.events.BedWarsBedBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.BedDestroy;
import xyz.iamthedefender.cosmetics.api.event.BedBreakEffectExecuteEvent;
import xyz.iamthedefender.cosmetics.api.handler.ITeamHandler;
import xyz.iamthedefender.cosmetics.util.BedWarsWrapper;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedDestroy2026 implements Listener {

    @EventHandler
    public void onBedDestroy(BedWarsBedBreakEvent event) {
        Player player = event.getAttacker();
        if (player == null) return;

        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(player, CosmeticsType.BedBreakEffects);
        BedBreakEffectExecuteEvent bedDestroyEvent = new BedBreakEffectExecuteEvent(player);
        Bukkit.getServer().getPluginManager().callEvent(bedDestroyEvent);

        if (bedDestroyEvent.isCancelled()) return;

        boolean isBedDestroysEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("bed-break-effects.enabled");
        if (!isBedDestroysEnabled) return;

        DebugUtil.addMessage("Executing " + selected + " Bed Break Effect for " + player.getDisplayName());
        Location loc = event.getVictimTeam().getBedLocation();

        if (loc == null) return;

        ITeamHandler handler = BedWarsWrapper.wrap(event.getVictimTeam());

        for (BedDestroy bedDestroy : StartupUtils.bedDestroyList) {
            if (!selected.equals(bedDestroy.getIdentifier())) continue;

            if (bedDestroy.getField(FieldsType.RARITY, player) == RarityType.NONE) continue;

            bedDestroy.execute(player, loc, handler);
        }
    }
}
