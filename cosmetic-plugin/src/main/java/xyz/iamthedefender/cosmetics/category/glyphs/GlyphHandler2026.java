package xyz.iamthedefender.cosmetics.category.glyphs;

import me.horiciastko.bedwars.BedWars;
import me.horiciastko.bedwars.models.Arena;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.Glyph;
import xyz.iamthedefender.cosmetics.category.victorydance.util.UsefulUtilsVD;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.HashMap;

public class GlyphHandler2026 implements Listener {

    public static HashMap<Location, Integer> glyphs = new HashMap<>();

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {

        boolean isGlyphsEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("glyphs.enabled");
        if (!isGlyphsEnabled) return;

        if (e.getItem().getItemStack().getType() != Material.DIAMOND && e.getItem().getItemStack().getType() != Material.EMERALD) {
            return;
        }

        Player player = e.getPlayer();
        Arena arena = BedWars.getInstance().getArenaManager().getPlayerArena(player);
        if (arena == null || arena.getState() != Arena.GameState.IN_GAME) return;

        String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(player, CosmeticsType.Glyphs);

        for (Block loc : UsefulUtilsVD.getBlocksInRadius(player.getLocation(), 2, false)) {
            if (loc.getType() == Material.DIAMOND_BLOCK || loc.getType() == Material.EMERALD_BLOCK) {
                DebugUtil.addMessage("Executing " + selected + " Glyph for " + player.getDisplayName());
                for (Glyph glyphs1 : StartupUtils.glyphsList) {
                    if (selected.equals(glyphs1.getIdentifier())) {
                        if (glyphs1.getField(FieldsType.RARITY, player) == RarityType.NONE) return;
                        glyphs1.execute(player, loc.getLocation().add(0, 8, 0).subtract(2, 0, 0));
                    }
                }
            }
        }
    }
}
