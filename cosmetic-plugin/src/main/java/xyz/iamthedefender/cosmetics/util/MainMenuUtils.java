

package xyz.iamthedefender.cosmetics.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.CosmeticsAPI;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.util.ColorUtil;
import xyz.iamthedefender.cosmetics.api.util.Constants;
import xyz.iamthedefender.cosmetics.api.util.Messages;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;
import xyz.iamthedefender.cosmetics.data.PlayerOwnedData;
import xyz.iamthedefender.cosmetics.menu.CategoryMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainMenuUtils {

    public static void saveLores() {
        List<String> sprays = Arrays.asList("&7选择一个喷雾，", "&7在地图上到处涂鸦！", "&7喷雾使用点位于", "&7每个出生岛和部分中心岛。", "", "&7已解锁:&a {ownedSpray}", "&7当前使用:", "{spray}", "", "&e点击查看。");
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Sprays.lore", sprays);
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Sprays.name", "&a喷雾");

        List<String> pts = Arrays.asList("&7更换你的弹道粒子", "&7拖尾特效。", "", "&7已解锁:&a {ownedpt}", "&7当前使用:", "{projectile}", "", "&e点击查看。");
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Projectile-Trails.lore", pts);
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Projectile-Trails.name", "&a弹道特效");

        List<String> finalke = Arrays.asList("&7各种不同的特效，", "&7每当你最终击杀敌人", "&7时就会触发！", "", "&7已解锁:&a {ownedfinalkill}", "&7当前使用:", "&a" + "{finalkill}", "", "&e点击查看。");
        Utility.saveIfNotExistsLang("cosmetics.main-menu.FinalKill-Effects.lore", finalke);
        Utility.saveIfNotExistsLang("cosmetics.main-menu.FinalKill-Effects.name", "&a最终击杀特效");

        List<String> km = Arrays.asList("&7选择一个击杀信息套餐，", "&7替换击杀玩家时", "&7聊天栏显示的消息。", "", "&7已解锁:&a {ownedkm}", "&7当前使用:", "{killmsg}", "", "&e点击查看。");
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Kill-Messages.lore", km);
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Kill-Messages.name", "&a击杀信息");

        List<String> gly = Arrays.asList("&7选择一个字符图案，", "&7捡起钻石和绿宝石时", "&7就会显现！", "", "&7已解锁:&a {ownedgly}", "&7当前使用:", "{glyphs}", "", "&e点击查看。");
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Glyphs.lore", gly);
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Glyphs.name", "&a字符图案");

        List<String> bbe = Arrays.asList("&7选择一种破床特效，", "&7当你破坏床时就会触发！", "", "&7已解锁:&a {ownedbbe}", "&7当前使用:", "{bedbreak}", "", "&e点击查看。");
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Bed-Destroys.lore", bbe);
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Bed-Destroys.name", "&a破床特效");

        List<String> ws = Arrays.asList("&7更换游戏内", "&7木块的皮肤。", "", "&7已解锁:&a {ownedws}", "&7当前使用:", "{woodskin}", "", "&e点击查看。");
        Utility.saveIfNotExistsLang("cosmetics.main-menu.WoodSkins.lore", ws);
        Utility.saveIfNotExistsLang("cosmetics.main-menu.WoodSkins.name", "&a木块皮肤");

        List<String> vd = Arrays.asList("&7获胜时炫耀庆祝，", "&7向其他玩家", "&7展示你的胜利舞！", "", "&7已解锁:&a {ownedvd}", "&7当前使用:", "{victory}", "", "&e点击查看。");
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Victory-Dances.lore", vd);
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Victory-Dances.name", "&a胜利舞");

        List<String> islandtoppers = Arrays.asList("&7选择一个岛屿顶饰，", "&7装饰你的岛屿！在双人", "&7和团队模式中，随机选取", "&7每队一名玩家的选择。", "", "&7已解锁:&a {ownedit}", "&7当前使用:", "&a{islandtopper}", "", "&e点击选择。");
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Island-Toppers.lore", islandtoppers);
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Island-Toppers.name", "&a岛屿顶饰");

        List<String> shopkeepers = Arrays.asList("&7选择一个商店老板皮肤，", "&7替换游戏内商店老板", "&7的形象！在双人和团队", "&7模式中，随机选取每队", "&7一名玩家的选择。", "", "&7已解锁:&a {ownedshopkeeper}", "&7当前使用:", "{shopkeeper}", "", "&e点击查看。");
        Utility.saveIfNotExistsLang("cosmetics.main-menu.ShopKeeperSkins.lore", shopkeepers);
        Utility.saveIfNotExistsLang("cosmetics.main-menu.ShopKeeperSkins.name", "&a商店老板皮肤");

        List<String> dc = Arrays.asList("&7每次阵亡时，让别人", "&7知道你的眼泪有多咸，", "&7挑选一个死亡音效吧！", "", "&7已解锁:&a {owneddc}", "&7当前使用:", "{deathcries}", "", "&e点击查看。");
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Death-Cries.lore", dc);
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Death-Cries.name", "&a死亡音效");

        Utility.saveIfNotExistsLang("cosmetics.main-menu.Back.name", "&a返回");
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Back.lore", List.of("&7点击返回！"));

        Utility.saveIfNotExistsLang("cosmetics.main-menu.Balance.name", "&6余额");
        Utility.saveIfNotExistsLang("cosmetics.main-menu.Balance.lore", List.of("&7余额: &6%vault_eco_balance_formatted%"));

        List<String> defaultList = new ArrayList<>(List.of("Sprays", "Projectile-Trails", "FinalKill-Effects", "Kill-Messages", "Glyphs", "Bed-Destroys", "Wood-Skins", "Victory-Dances", "Island-Toppers", "ShopKeeperSkins", "Death-Cries", "Back", "Balance"));
        ConfigManager config = ConfigUtils.getMainConfig();
        ConfigurationSection section = config.getYml().getConfigurationSection("Main-Menu");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                Utility.saveIfNotExistsLang("cosmetics.main-menu." + key + ".name", "&c未设置名称!");
                Utility.saveIfNotExistsLang("cosmetics.main-menu." + key + ".lore", List.of("&c未设置描述!"));
            }
        }
        config.save();
        config.reload();
    }


    public static List<String> formatLore(List<String> lores, Player p) {
        CosmeticsAPI api = CosmeticsPlugin.getInstance().getApi();
        PlayerOwnedData ownedData = CosmeticsPlugin.getInstance().getPlayerManager().getPlayerOwnedData(p.getUniqueId());

        try {
            lores = lores.stream()
                    .map(s -> s.replace("{islandtopper}", "&a" + StringUtils.replaceHyphensAndCaptalizeFirstLetter(api.getSelectedCosmetic(p, CosmeticsType.IslandToppers))))
                    .map(s -> s.replace("{spray}", "&a" + StringUtils.replaceHyphensAndCaptalizeFirstLetter(api.getSelectedCosmetic(p, CosmeticsType.Sprays))))
                    .map(s -> s.replace("{killmsg}", "&a" + StringUtils.replaceHyphensAndCaptalizeFirstLetter(api.getSelectedCosmetic(p, CosmeticsType.KillMessages))))
                    .map(s -> s.replace("{shopkeeper}", "&a" + StringUtils.replaceHyphensAndCaptalizeFirstLetter(api.getSelectedCosmetic(p, CosmeticsType.ShopKeeperSkins))))
                    .map(s -> s.replace("{woodskin}", "&a" + StringUtils.replaceHyphensAndCaptalizeFirstLetter(api.getSelectedCosmetic(p, CosmeticsType.WoodSkins))))
                    .map(s -> s.replace("{victory}", "&a" + StringUtils.replaceHyphensAndCaptalizeFirstLetter(api.getSelectedCosmetic(p, CosmeticsType.VictoryDances))))
                    .map(s -> s.replace("{deathcries}", "&a" + StringUtils.replaceHyphensAndCaptalizeFirstLetter(api.getSelectedCosmetic(p, CosmeticsType.DeathCries))))
                    .map(s -> s.replace("{glyphs}", "&a" + StringUtils.replaceHyphensAndCaptalizeFirstLetter(api.getSelectedCosmetic(p, CosmeticsType.Glyphs))))
                    .map(s -> s.replace("{bedbreak}", "&a" + StringUtils.replaceHyphensAndCaptalizeFirstLetter(api.getSelectedCosmetic(p, CosmeticsType.BedBreakEffects))))
                    .map(s -> s.replace("{projectile}", "&a" + StringUtils.replaceHyphensAndCaptalizeFirstLetter(api.getSelectedCosmetic(p, CosmeticsType.ProjectileTrails))))
                    .map(s -> s.replace("{finalkill}", "&a" + StringUtils.replaceHyphensAndCaptalizeFirstLetter(api.getSelectedCosmetic(p, CosmeticsType.FinalKillEffects))))
                    .map(s -> s.replace("{islandtopper}", "&a" + StringUtils.replaceHyphensAndCaptalizeFirstLetter(api.getSelectedCosmetic(p, CosmeticsType.IslandToppers))))
                    .map(s -> s.replace("{ownedSpray}", "&a" + ownedData.getSpray() + "/" + StartupUtils.sprayList.size() + " &8(" + ((ownedData.getSpray() / StartupUtils.sprayList.size()) * 100) + "%)"))
                    .map(s -> s.replace("{ownedpt}", "&a" + ownedData.getProjectileTrail() + "/" + StartupUtils.projectileTrailList.size() + " &8(" + ((ownedData.getProjectileTrail() / StartupUtils.projectileTrailList.size()) * 100) + "%)"))
                    .map(s -> s.replace("{ownedfinalkill}", "&a" + ownedData.getFinalKillEffect() + "/" + StartupUtils.finalKillList.size() + " &8(" + ((ownedData.getFinalKillEffect() / StartupUtils.finalKillList.size()) * 100) + "%)"))
                    .map(s -> s.replace("{ownedkm}", "&a" + ownedData.getKillMessage() + "/" + StartupUtils.killMessageList.size() + " &8(" + ((ownedData.getKillMessage() / StartupUtils.killMessageList.size()) * 100) + "%)"))
                    .map(s -> s.replace("{ownedgly}", "&a" + ownedData.getGlyph() + "/" + StartupUtils.glyphsList.size() + " &8(" + ((ownedData.getGlyph() / StartupUtils.glyphsList.size()) * 100) + "%)"))
                    .map(s -> s.replace("{ownedbbe}", "&a" + ownedData.getBedDestroy() + "/" + StartupUtils.bedDestroyList.size() + " &8(" + ((ownedData.getBedDestroy() / StartupUtils.bedDestroyList.size()) * 100) + "%)"))
                    .map(s -> s.replace("{ownedws}", "&a" + ownedData.getWoodSkin() + "/" + StartupUtils.woodSkinsList.size() + " &8(" + ((ownedData.getWoodSkin() / StartupUtils.woodSkinsList.size()) * 100) + "%)"))
                    .map(s -> s.replace("{ownedvd}", "&a" + ownedData.getVictoryDance() + "/" + StartupUtils.victoryDancesList.size() + " &8(" + ((ownedData.getVictoryDance() / StartupUtils.victoryDancesList.size()) * 100) + "%)"))
                    .map(s -> s.replace("{ownedit}", "&a" + ownedData.getIslandTopper() + "/" + StartupUtils.islandTopperList.size() + " &8(" + ((ownedData.getIslandTopper() / StartupUtils.islandTopperList.size()) * 100) + "%)"))
                    .map(s -> s.replace("{ownedshopkeeper}", "&a" + ownedData.getShopkeeperSkin() + "/" + StartupUtils.shopKeeperSkinList.size() + " &8(" + ((ownedData.getShopkeeperSkin() / StartupUtils.shopKeeperSkinList.size()) * 100) + "%)"))
                    .map(s -> s.replace("{owneddc}", "&a" + ownedData.getDeathCry() + "/" + StartupUtils.deathCryList.size() + " &8(" + ((ownedData.getDeathCry() / StartupUtils.deathCryList.size()) * 100) + "%)"))
                    .map(s -> PlaceholderAPI.setPlaceholders(p, s))
                    .map(ColorUtil::translate)
                    .collect(Collectors.toList());
        } catch (Exception ignored) {
            // Cuz I guess only IslandTopper have this issue
            Bukkit.getLogger().severe("Please install world edit, without world edit plugin will not work!");
        }
        return lores;
    }

    public static void openMenus(Player p, String name) {
        String title;

        CosmeticsType cosmeticsType = CosmeticsType.fromName(name);

        if (name.equals("Back")) {
            String command = CosmeticsPlugin.getInstance().menuData.getString("Main-Menu.Back.custom-command");
            if (command == null) {
                p.getOpenInventory().close();
            } else {
                Bukkit.dispatchCommand(p, command);
            }
        }

        if (cosmeticsType == null) return;

        title = Messages.of(
                        cosmeticsType.name().toLowerCase() + Constants.Key.COSMETICS_MENU_TITLES_SUFFIX, cosmeticsType.getFormatedName()
                )
                .value(p);

        new CategoryMenu(cosmeticsType, title).open(p);

    }
}
