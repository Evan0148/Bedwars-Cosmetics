package xyz.iamthedefender.cosmetics.category.islandtoppers;

import me.horiciastko.bedwars.api.events.BedWarsGameStartEvent;
import me.horiciastko.bedwars.models.Arena;
import me.horiciastko.bedwars.models.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.IslandTopper;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.util.DebugUtil;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.io.File;
import java.util.Random;

public class IslandTopper2026 implements Listener {

    @EventHandler
    public void onGameStart(BedWarsGameStartEvent event) {

        boolean isIslandToppersEnabled = CosmeticsPlugin.getInstance().getConfig().getBoolean("island-toppers.enabled");
        if (!isIslandToppersEnabled) return;

        Run.delayed(() -> {
            Arena arena = event.getArena();

            for (Team team : arena.getTeams()) {
                Player player = null;
                for (Player p : team.getMembers()) {
                    if (!CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(p, CosmeticsType.IslandToppers).equals("none")) {
                        player = p;

                        if (new Random().nextDouble() < 0.5)
                            break;
                    }
                }

                if (player == null) return;
                String pathForLocation = "Team." + team.getName() + ".IslandTopper.";
                File file = new File(CosmeticsPlugin.getInstance().getDataFolder(), ".data/arenas/" + arena.getName() + ".yml");

                if (!file.exists() || !file.getAbsolutePath().endsWith(".yml")) {
                    DebugUtil.addMessage("Failed to find the file required for IslandToppers for player " + player.getName() + ": " + file.getAbsolutePath());
                    return;
                }

                YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

                String locationString = configuration.getString(pathForLocation + "location");

                if (locationString == null) {
                    DebugUtil.addMessage("Failed to find IslandTopper team location for player " + player.getName() + " and team " + team.getName() + " in " + arena.getName());
                    return;
                }

                Location location = null;

                try {
                    String[] data = locationString.replace("[", "").replace("]", "").split(",");
                    location = new Location(Bukkit.getWorld(data[5]), Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Float.parseFloat(data[3]), Float.parseFloat(data[4]));
                } catch (Exception ignored) {
                }

                if (location == null) {
                    DebugUtil.addMessage("Invalid or no location found for team " + team.getName() + ", player " + player.getName() + " in " + arena.getName());
                    return;
                }

                String selected = CosmeticsPlugin.getInstance().getApi().getSelectedCosmetic(player, CosmeticsType.IslandToppers);
                for (IslandTopper islandTopper : StartupUtils.islandTopperList) {
                    if (islandTopper.getIdentifier().equals(selected)) {
                        if (islandTopper.getField(FieldsType.RARITY, player) != RarityType.NONE) {
                            islandTopper.execute(player, location, selected);
                        }
                    }
                }
            }
        }, 20L);
    }
}
