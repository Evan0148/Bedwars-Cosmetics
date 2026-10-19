package xyz.iamthedefender.cosmetics.api.cosmetics;


import lombok.Getter;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.*;
import xyz.iamthedefender.cosmetics.api.util.Utility;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;

import java.util.List;

@Getter
public enum CosmeticsType {
    FinalKillEffects("最终击杀特效", ConfigUtils.getFinalKillEffects(), Utility.getApi().getFinalKillList(), "finalkill-effect", "finalkilleffect", FinalKillEffect.class),
    ProjectileTrails("弹道特效", ConfigUtils.getProjectileTrails(), Utility.getApi().getProjectileTrailList(), "projectile-trails", "projectiletrail", ProjectileTrail.class),
    BedBreakEffects("破床特效", ConfigUtils.getBedDestroys(), Utility.getApi().getBedDestroyList(), "bed-destroy", "beddestroy", BedDestroy.class),
    Glyphs("字符图案", ConfigUtils.getGlyphs(), Utility.getApi().getGlyphsList(), "glyph", "glyph", Glyph.class),
    DeathCries("死亡音效", ConfigUtils.getDeathCries(), Utility.getApi().getDeathCryList(), "death-cry", "deathcry", DeathCry.class),
    VictoryDances("胜利舞", ConfigUtils.getVictoryDances(), Utility.getApi().getVictoryDanceList(), "victory-dance", "victorydance", VictoryDance.class),
    WoodSkins("木块皮肤", ConfigUtils.getWoodSkins(), Utility.getApi().getWoodSkinList(), "wood-skins", "woodskin", WoodSkin.class),
    Sprays("喷雾", ConfigUtils.getSprays(), Utility.getApi().getSprayList(), "sprays", "spray", Spray.class),
    KillMessages("击杀信息", ConfigUtils.getKillMessages(), Utility.getApi().getKillMessageList(), "kill-message", "killmessage", KillMessage.class),
    ShopKeeperSkins("商店老板皮肤", ConfigUtils.getShopKeeperSkins(), Utility.getApi().getShopKeeperSkinList(), "shopkeeper-skins", "shopkeeperskin", ShopKeeperSkin.class),
    IslandToppers("岛屿顶饰", ConfigUtils.getIslandToppers(), Utility.getApi().getIslandTopperList(), "island-topper", "islandtopper", IslandTopper.class);

    private final String formatedName;
    private final ConfigManager configManager;
    private final List<?> itemsList;
    private final String sectionKey;
    private final String permissionFormat;
    private final Class<?> cosmeticsClass;

    CosmeticsType(String formatedName, ConfigManager configManager, List<?> itemsList, String sectionKey, String permissionFormat, Class<?> cosmeticsClass) {
        this.formatedName = formatedName;
        this.configManager = configManager;
        this.itemsList = itemsList;
        this.sectionKey = sectionKey;
        this.permissionFormat = permissionFormat;
        this.cosmeticsClass = cosmeticsClass;
    }

    public ConfigManager getConfig() {
        return configManager;
    }

    public static CosmeticsType fromName(String name) {
        String normalized = name.replace("-", "").replace("_", "");
        for (CosmeticsType type : values()) {
            if (type.name().replace("-", "").replace("_", "").equalsIgnoreCase(normalized)) {
                return type;
            }
        }
        return null;
    }

}
