package xyz.iamthedefender.cosmetics.support.bedwars.handler.bedwars2026;

import me.horiciastko.bedwars.BedWars;
import me.horiciastko.bedwars.models.Arena;
import me.horiciastko.bedwars.models.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.handler.*;
import xyz.iamthedefender.cosmetics.category.bedbreakeffects.BedDestroy2026;
import xyz.iamthedefender.cosmetics.category.deathcries.DeathCry2026;
import xyz.iamthedefender.cosmetics.category.finalkilleffects.FinalKillEffect2026;
import xyz.iamthedefender.cosmetics.category.glyphs.GlyphHandler2026;
import xyz.iamthedefender.cosmetics.category.islandtoppers.IslandTopper2026;
import xyz.iamthedefender.cosmetics.category.killmessage.KillMessages2026;
import xyz.iamthedefender.cosmetics.category.projectiletrails.ProjectileHandler;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.ShopKeeper2026;
import xyz.iamthedefender.cosmetics.category.sprays.Sprays2026;
import xyz.iamthedefender.cosmetics.category.victorydance.VictoryDance2026;
import xyz.iamthedefender.cosmetics.category.woodskin.WoodSkin2026;
import xyz.iamthedefender.cosmetics.support.language.LanguageImpl;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BedWars2026Handler implements IHandler {

    private final File dataFolder = new File(BedWars.getInstance().getDataFolder(),
            "Addons/" + CosmeticsPlugin.getInstance().getDescription().getName());
    private final LanguageImpl language = new LanguageImpl(new File(getAddonPath(), "messages.yml"));

    @Override
    public void register() {
        StartupUtils.registerListeners(new ProjectileHandler(CosmeticsPlugin.getInstance()));

        if (StartupUtils.citizensEnabled) {
            StartupUtils.registerListeners(
                    new WoodSkin2026(),
                    new VictoryDance2026(),
                    new Sprays2026(),
                    new ShopKeeper2026(),
                    new KillMessages2026(),
                    new FinalKillEffect2026(),
                    new BedDestroy2026(),
                    new DeathCry2026(),
                    new IslandTopper2026(),
                    new GlyphHandler2026()
            );
        } else {
            StartupUtils.registerListeners(
                    new WoodSkin2026(),
                    new VictoryDance2026(),
                    new Sprays2026(),
                    new KillMessages2026(),
                    new FinalKillEffect2026(),
                    new BedDestroy2026(),
                    new DeathCry2026(),
                    new IslandTopper2026(),
                    new GlyphHandler2026()
            );
            Bukkit.getLogger().warning("Can't find Citizens! ShopKeeperSkins will not load!");
        }
    }

    @Override
    public ISetupSession getSetupSession(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) return null;

        Arena arena = BedWars.getInstance().getArenaManager().getPlayerArena(player);

        if (arena == null) return null;

        File file = new File(CosmeticsPlugin.getInstance().getDataFolder(), ".data/arenas/" + arena.getName() + ".yml");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                CosmeticsPlugin.getInstance().getLogger().warning("Failed to create configuration file: " + file.getAbsolutePath());
                e.printStackTrace();
                return null;
            }
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        return new ISetupSession() {
            @Override
            public UUID getPlayerUUID() {
                return playerUUID;
            }

            @Override
            public FileConfiguration getConfig() {
                return config;
            }

            @Override
            public void saveConfigLoc(String path, Location value) {
                String data = value.getX() + "," + value.getY() + "," + value.getZ() + "," + value.getYaw() + "," + value.getPitch() + "," + value.getWorld().getName();
                config.set(path, data);
                saveConfig();
            }

            @Override
            public void saveConfig() {
                try {
                    config.save(file);
                } catch (IOException e) {
                    CosmeticsPlugin.getInstance().getLogger().warning("Failed to save configuration file: " + file.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public IScoreboardUtil getScoreboardUtil() {
        return new IScoreboardUtil() {
            @Override
            public void giveScoreboard(Player player, boolean b) {
                // BedWars2026 manages its own scoreboard
            }

            @Override
            public void removePlayerScoreboard(Player player) {
                // BedWars2026 manages its own scoreboard
            }
        };
    }

    @Override
    public IArenaUtil getArenaUtil() {
        return player -> {
            Arena arena = BedWars.getInstance().getArenaManager().getPlayerArena(player);

            if (arena == null) return null;

            return player1 -> {
                Team team = BedWars.getInstance().getGameManager().getPlayerTeam(arena, player1);
                if (team == null) return null;

                return new ITeamHandler() {
                    @Override
                    public Location getBed() {
                        return team.getBedLocation();
                    }

                    @Override
                    public List<Player> getPlayers() {
                        return team.getMembers();
                    }

                    @Override
                    public String getName() {
                        return team.getName();
                    }

                    @Override
                    public List<Location> getStoreLocations() {
                        List<Location> storeLocations = new ArrayList<>();
                        if (team.getShopLocation() != null) storeLocations.add(team.getShopLocation());
                        if (team.getUpgradeLocation() != null) storeLocations.add(team.getUpgradeLocation());
                        return storeLocations;
                    }

                    @Override
                    public Location getSpawn() {
                        return team.getSpawnLocation();
                    }

                    @Override
                    public int getSize() {
                        return team.getMembers().size();
                    }
                };
            };
        };
    }

    @Override
    public ILanguage getLanguageUtil() {
        return language;
    }

    @Override
    public String getAddonPath() {

        if (!dataFolder.exists()) {
            dataFolder.getParentFile().mkdirs();
            dataFolder.mkdirs();
        }

        return dataFolder.getAbsolutePath();
    }

    @Override
    public HandlerType getHandlerType() {
        return HandlerType.MULTIARENA;
    }
}
