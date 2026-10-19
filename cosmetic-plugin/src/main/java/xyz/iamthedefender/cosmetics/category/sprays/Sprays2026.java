package xyz.iamthedefender.cosmetics.category.sprays;

import com.cryptomorin.xseries.XMaterial;
import me.horiciastko.bedwars.api.events.BedWarsGameStartEvent;
import me.horiciastko.bedwars.models.Arena;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.Spray;
import xyz.iamthedefender.cosmetics.api.util.Messages;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

public class Sprays2026 implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent e) {

        boolean isSpraysEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("sprays.enabled");
        if (!isSpraysEnabled) return;

        Player p = e.getPlayer();
        if (e.getRightClicked() instanceof ItemFrame) {
            ItemFrame itemFrame = (ItemFrame) e.getRightClicked();
            if (itemFrame.getItem() == null) return;
            String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(p, CosmeticsType.Sprays);
            XMaterial material = XMaterial.matchXMaterial(itemFrame.getItem());
            // AIR, MAP, FILLED_MAP
            if (material == XMaterial.AIR || material == XMaterial.MAP || material == XMaterial.FILLED_MAP) {
                for (Spray spray : StartupUtils.sprayList) {
                    if (spray.getIdentifier().equals(selected)) {
                        spray.execute(p, itemFrame);
                    }
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onGameStart(BedWarsGameStartEvent event) {

        boolean isSpraysEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("sprays.enabled");
        if (!isSpraysEnabled) return;

        Arena arena = event.getArena();
        String worldName = arena.getWorldName();
        if (worldName == null) return;

        org.bukkit.World world = org.bukkit.Bukkit.getWorld(worldName);
        if (world == null) return;

        DebugUtil.addMessage("Setting up sprays for arena " + arena.getName());

        for (final Entity e : world.getEntities()) {
            if (e.getType() == EntityType.ITEM_FRAME) {
                ItemFrame itemFrame = (ItemFrame) e;
                if (itemFrame.getItem() == null || itemFrame.getItem().getType() == Material.AIR) {
                    ArmorStand stand = (ArmorStand) e.getWorld().spawnEntity(e.getLocation().subtract(0.0, 0.9, 0.0), EntityType.ARMOR_STAND);
                    stand.setVisible(false);
                    stand.setGravity(false);
                    stand.setCustomName(Messages.SPRAY_CLICK.value(null));
                    stand.setMetadata("HOLO_ITEM_FRAME", new FixedMetadataValue(CosmeticsPlugin.getInstance(), ""));
                    stand.setCustomNameVisible(true);
                    stand.setMarker(true);
                    stand.setSmall(true);
                }
            }
        }
    }
}
