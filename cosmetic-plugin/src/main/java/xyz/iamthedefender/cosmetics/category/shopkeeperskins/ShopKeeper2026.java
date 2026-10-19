package xyz.iamthedefender.cosmetics.category.shopkeeperskins;

import me.horiciastko.bedwars.api.events.BedWarsGameEndEvent;
import me.horiciastko.bedwars.api.events.BedWarsGameStartEvent;
import me.horiciastko.bedwars.models.Arena;
import me.horiciastko.bedwars.models.Team;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.ShopKeeperSkin;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ShopKeeper2026 implements Listener {

    public static final Map<String, Boolean> arenas = new HashMap<>();

    @EventHandler
    public void onGameStart(BedWarsGameStartEvent event) {

        boolean isShopkeepersEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("shopkeeper-skins.enabled");
        if (!isShopkeepersEnabled) return;

        Arena arena = event.getArena();
        arenas.put(arena.getName(), true);
        DebugUtil.addMessage("Executing ShopKeeper Skins for arena " + arena.getName());

        // Copy of the teams' data — capture before a later runnable runs.
        java.util.List<Team> teams = new java.util.ArrayList<>(arena.getTeams());

        new BukkitRunnable() {
            public void run() {
                for (Team team : teams) {
                    if (team.getMembers().isEmpty()) continue; // Skip empty teams

                    Location shopLocation = team.getShopLocation();
                    Location upgradeLocation = team.getUpgradeLocation();

                    if (shopLocation == null || upgradeLocation == null) continue;

                    DebugUtil.addMessage("Executing ShopKeeper Skins for team " + team.getName());

                    // Choose random player from the team
                    Player player = team.getMembers().get(new Random().nextInt(team.getMembers().size()));
                    String skin = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(player, CosmeticsType.ShopKeeperSkins);
                    DebugUtil.addMessage("Selected skin: " + skin);

                    for (ShopKeeperSkin skins : StartupUtils.shopKeeperSkinList) {
                        if (skin.equals(skins.getIdentifier())) {
                            try {
                                skins.execute(player, shopLocation, upgradeLocation);
                            } catch (Exception ignored) {
                            }
                        }
                    }
                }
            }
        }.runTaskLater(CosmeticsPlugin.getInstance(), 30L);
    }

    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent e) {

        boolean isShopkeepersEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("shopkeeper-skins.enabled");
        if (!isShopkeepersEnabled) return;

        if (e.getPlayer().hasMetadata("NPC2")) {
            e.setCancelled(true);

            Run.delayed(() -> {
                if (e.getPlayer().isOnline()) {
                    net.citizensnpcs.api.CitizensAPI.getNPCRegistry().getNPC(e.getPlayer()).despawn();
                }
            }, 2L);
        }
    }

    @EventHandler
    public void onGameEnd(BedWarsGameEndEvent e) {
        boolean isShopkeepersEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("shopkeeper-skins.enabled");
        if (!isShopkeepersEnabled) return;

        Run.delayed(() -> ShopKeeper2026.arenas.remove(e.getArena().getName()), 300L);
    }
}
