package xyz.iamthedefender.cosmetics.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.ConditionFailedException;
import co.aikar.commands.annotation.*;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.handler.ISetupSession;
import xyz.iamthedefender.cosmetics.api.menu.SystemGui;
import xyz.iamthedefender.cosmetics.api.util.ColorUtil;
import xyz.iamthedefender.cosmetics.api.util.Run;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigType;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;
import xyz.iamthedefender.cosmetics.menu.CategoryMenu;
import xyz.iamthedefender.cosmetics.menu.MainMenu;
import xyz.iamthedefender.cosmetics.util.MainMenuUtils;
import xyz.iamthedefender.cosmetics.util.StartupUtils;

@CommandAlias("bwc|bedwarscosmetics|cosmetics|cos|bwcos|bwcosmetic|bwcosmetics")
@Description("Main command for the cosmetics plugin.")
public class BedWarsCosmeticsCommand extends BaseCommand {

    private final CosmeticsPlugin plugin = CosmeticsPlugin.getInstance();

    @Subcommand("help")
    @CommandPermission("bwcosmetics.help")
    @CatchUnknown
    public void helpCommand(Player player) {
        player.sendMessage(ColorUtil.translate("&8&l======================================"));
        player.sendMessage(ColorUtil.translate("&6&l     BedWars 特效装扮 — 命令"));
        player.sendMessage(ColorUtil.translate("&8&l======================================"));
        player.sendMessage(" ");

        send(player, "/bwc reload", "Reloads all YAML files");
        send(player, "/bwc menu", "Opens the Main Menu");
        send(player, "/bwc km", "Opens the Kill Message GUI");
        send(player, "/bwc shopkeeper", "Opens the Shopkeeper Skin GUI");
        send(player, "/bwc sprays", "Opens the Sprays GUI");
        send(player, "/bwc dc", "Opens the Death Cries GUI");
        send(player, "/bwc glyphs", "Opens the Glyphs GUI");
        send(player, "/bwc bbe", "Opens the Bed Break Effect GUI");
        send(player, "/bwc finalke", "Opens the Final Kill Effect GUI");
        send(player, "/bwc pt", "Opens the Projectile Trails GUI");
        send(player, "/bwc vd", "Opens the Victory Dance GUI");
        send(player, "/bwc ws", "Opens the Wood Skins GUI");
        send(player, "/bwc it", "Opens the Island Toppers GUI");
        send(player, "/bwc setIslandTopperPosition <teamName>", "Sets the topper location for a team");
        send(player, "/bwc setupPlayerLocation", "Sets the preview player location");
        send(player, "/bwc setupPreviewLocation", "Sets the preview location");
    }

    private void send(Player player, String cmd, String desc) {
        player.spigot().sendMessage(
                Utility.hoverableClickableMessage(
                        "&6-> &7" + cmd + "    &8- &e点击查看详情",
                        desc,
                        cmd
                )
        );
    }

    @Subcommand("set")
    @CommandPermission("bwcosmetics.admin")
    public void setCommand(CommandSender sender, CosmeticsType cosmeticsType, String cosmeticID, String playerName) {
        Player player = Bukkit.getPlayer(playerName);

        if (player == null || !player.isOnline()) {
            sender.sendMessage(ChatColor.RED + "无效或离线的玩家: " + playerName);
            return;
        }

        plugin.getApi().setSelectedCosmetic(player, cosmeticsType, cosmeticID);
        sender.sendMessage(ColorUtil.translate("&a成功！注意：此命令不会校验 cosmeticsID 是否有效！"));
    }


    @Subcommand("reload")
    @CommandPermission("bwcosmetics.reload")
    public void reloadCommand(CommandSender sender) {
        sender.sendMessage("§a正在重载配置，请稍候...");
        StartupUtils.updateConfigs();
        MainMenuUtils.saveLores();
        for(ConfigType configType : ConfigType.values()){
            ConfigUtils.get(configType).reload();
        }
        sender.sendMessage("§a配置重载完成！");
    }


    @Subcommand("menu")
    public void menuCommand(Player player) {
        if (Utility.isInArena(player)) throw new ConditionFailedException("You cannot do that while in a game!");

        new MainMenu(player).open(player);
    }

    @Subcommand("km")
    public void onKmMenu(Player player) {
        openMenu(player, CosmeticsType.KillMessages);
    }

    @Subcommand("shopkeeper")
    public void onShopkeeperMenu(Player player) {
        openMenu(player, CosmeticsType.ShopKeeperSkins);
    }

    @Subcommand("sprays")
    public void onSpraysMenu(Player player) {
        openMenu(player, CosmeticsType.Sprays);
    }

    @Subcommand("dc")
    public void onDcMenu(Player player) {
        openMenu(player, CosmeticsType.DeathCries);
    }

    @Subcommand("glyphs")
    public void onGlyphsMenu(Player player) {
        openMenu(player, CosmeticsType.Glyphs);
    }

    @Subcommand("bbe")
    public void onBbeMenu(Player player) {
        openMenu(player, CosmeticsType.BedBreakEffects);
    }

    @Subcommand("finalke")
    public void onFinalkeMenu(Player player) {
        openMenu(player, CosmeticsType.FinalKillEffects);
    }

    @Subcommand("pt")
    public void onPtMenu(Player player) {
        openMenu(player, CosmeticsType.ProjectileTrails);
    }

    @Subcommand("vd")
    public void onVdMenu(Player player) {
        openMenu(player, CosmeticsType.VictoryDances);
    }

    @Subcommand("ws")
    public void onWsMenu(Player player) {
        openMenu(player, CosmeticsType.WoodSkins);
    }

    @Subcommand("it")
    public void onItMenu(Player player) {
        openMenu(player, CosmeticsType.IslandToppers);
    }

    @Subcommand("setIslandTopperPosition")
    @CommandPermission("bwcosmetics.admin")
    public void onSetIslandTopperPosition(Player player, String teamName) {
        ISetupSession setupSession = plugin.getHandler().getSetupSession(player.getUniqueId());

        if (setupSession == null) {
            player.sendMessage(ChatColor.RED + "使用该命令前需要先进入搭建模式！");
            return;
        }

        ConfigurationSection section = setupSession.getConfig().getConfigurationSection("Team." + teamName);
        if (section == null) {
            player.sendMessage(ColorUtil.translate("&c你需要先完成队伍设置才能使用该命令！"));
            return;
        }

        String configPath = "Team." + teamName;
        setupSession.saveConfigLoc(configPath + ".IslandTopper.location", player.getLocation());

        ArmorStand hologramStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, 3, 0), EntityType.ARMOR_STAND);
        hologramStand.setGravity(false);
        hologramStand.setVisible(false);
        hologramStand.setCustomNameVisible(true);

        ChatColor color = getColorForTeam(section, teamName);

        hologramStand.setCustomName(color + teamName + " " + ChatColor.GOLD + "ISLAND TOPPER SET" + ChatColor.GRAY + " (HOLOGRAM WILL DISAPPEAR IN 5 SECONDS)");

        Run.delayed(hologramStand::remove, 5 * 20);
        player.sendMessage(ChatColor.GREEN + "成功！已将当前位置保存为队伍 " + teamName);
    }

    @Subcommand("setupPreviewLocation")
    @CommandPermission("bwcosmetics.admin")
    public void onSetPreviewLocation(Player player) {
        saveLocation(player, "cosmetic-preview.cosmetic-location");
        player.sendMessage(ChatColor.GREEN + "成功！已将当前位置保存为特效预览地点。");
    }

    @Subcommand("setupPlayerLocation")
    @CommandPermission("bwcosmetics.admin")
    public void onSetPlayerLocation(Player player) {
        saveLocation(player, "cosmetic-preview.player-location");
        player.sendMessage(ChatColor.GREEN + "成功！已将当前位置保存为预览时的玩家站位。");
    }

    private void openMenu(Player player, CosmeticsType cosmeticsType) {
        if (Utility.isInArena(player)) {
            player.sendMessage(ChatColor.RED + "在游戏中不能这样操作！");
            return;
        }

        String title = cosmeticsType.getFormatedName();
        if (CosmeticsPlugin.isPlaceholderAPI()) {
            title = PlaceholderAPI.setPlaceholders(player, title);
        }

        SystemGui inv = new CategoryMenu(cosmeticsType, title);
        inv.open(player);
    }

    private ChatColor getColorForTeam(ConfigurationSection section, String teamName) {
        if (teamName.equalsIgnoreCase("Pink")) {
            return ChatColor.LIGHT_PURPLE;
        }

        try {
            return ChatColor.valueOf(section.getString("Color"));
        } catch (Exception e) {
            return ChatColor.GOLD;
        }
    }

    private void saveLocation(Player player, String path) {
        Location location = player.getLocation();
        ConfigManager config = ConfigUtils.getMainConfig();
        config.set(path + ".world", location.getWorld().getName());
        config.set(path + ".x", location.getX());
        config.set(path + ".y", location.getY());
        config.set(path + ".z", location.getZ());
        config.set(path + ".yaw", location.getYaw());
        config.set(path + ".pitch", location.getPitch());
        config.save();
        config.reload();
    }

}