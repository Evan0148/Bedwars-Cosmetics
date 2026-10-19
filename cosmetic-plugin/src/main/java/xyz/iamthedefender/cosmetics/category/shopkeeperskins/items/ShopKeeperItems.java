package xyz.iamthedefender.cosmetics.category.shopkeeperskins.items;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.FieldsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.ShopKeeperSkin;
import xyz.iamthedefender.cosmetics.category.shopkeeperskins.utils.ShopKeeperSkinsUtils;
import xyz.iamthedefender.cosmetics.util.StartupUtils;
import xyz.iamthedefender.cosmetics.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShopKeeperItems {
    private static final java.util.Map<String, String> NAMES = new java.util.HashMap<>();
    static {
        NAMES.put("none", "无");
        NAMES.put("random", "随机");
        NAMES.put("skeleton", "骷髅老板");
        NAMES.put("pigmen", "猪人老板");
        NAMES.put("zombie", "僵尸老板");
        NAMES.put("snowman", "雪人老板");
        NAMES.put("blaze", "烈焰老板");
        NAMES.put("creeper", "苦力怕老板");
        NAMES.put("witch", "女巫老板");
        NAMES.put("miner", "矿工老板");
        NAMES.put("bed-salesman", "床铺商人");
        NAMES.put("holiday-barteneder", "节日酒保");
        NAMES.put("magic-vendor", "魔法商贩");
        NAMES.put("bao", "包老板");
        NAMES.put("li", "李老板");
        NAMES.put("chen", "陈老板");
        NAMES.put("wei", "魏老板");
        NAMES.put("Xiu", "秀老板");
        NAMES.put("zhao", "赵老板");
        NAMES.put("king-of-beds", "床中之王");
        NAMES.put("killer", "杀手老板");
        NAMES.put("warden", "监守者老板");
    }


    public void registerItems() {
        ConfigurationSection section = CosmeticsType.ShopKeeperSkins.getConfig().getYml().getConfigurationSection(CosmeticsType.ShopKeeperSkins.getSectionKey());
        if (section == null) return;
        ConfigManager config = CosmeticsType.ShopKeeperSkins.getConfig();
        for (String id : section.getKeys(false)) {
            String path = CosmeticsType.ShopKeeperSkins.getSectionKey() + "." + id + ".";
            ShopKeeperSkin shopKeeperSkin = new ShopKeeperSkin() {
                @Override
                public ItemStack getItem() {
                    return config.getItemStack(path + "item");
                }

                @Override
                public String base64() {
                    return null;
                }

                @Override
                public String getIdentifier() {
                    return id;
                }

                @Override
                public String getDisplayName() {
                    String custom = NAMES.get(id);
            return custom != null ? custom : StringUtils.replaceHyphensAndCaptalizeFirstLetter(id);
                }

                @Override
                public List<String> getLore() {
                    if (getRarity() == RarityType.NONE) {
                        return List.of("&7选择此选项将停用", "&7你的商店老板皮肤。");
                    }
                    return List.of("&7选择" + getDisplayName() + "作为你的", "&7商店老板皮肤！");
                }

                @Override
                public int getPrice() {
                    return config.getInt(path + "price");
                }

                @Override
                public RarityType getRarity() {
                    return RarityType.valueOf(config.getString(path + "rarity").toUpperCase());
                }

                @Override
                public void execute(Player player, List<Location> spawnLocations) {
                    if (getField(FieldsType.RARITY, player) == RarityType.RANDOM) {
                        List<ShopKeeperSkin> shopKeeperSkins = new ArrayList<>();
                        for (ShopKeeperSkin shopKeeperSkin : StartupUtils.shopKeeperSkinList) {
                            if (player.hasPermission(CosmeticsType.ShopKeeperSkins.getPermissionFormat() + "." + shopKeeperSkin.getIdentifier())) {
                                shopKeeperSkins.add(shopKeeperSkin);
                            }
                        }


                        if (shopKeeperSkins.isEmpty()) {
                            // ShopKeeperSkin#getDefault should not return null!

                            spawnLocations.forEach(loc -> ShopKeeperSkinsUtils.spawnShopKeeperNPC(player, loc, ShopKeeperSkin.getDefault(player).getIdentifier()));
                        } else {
                            ShopKeeperSkin shopKeeperSkin1 = shopKeeperSkins.get(new Random().nextInt(shopKeeperSkins.size()));

                            spawnLocations.forEach(loc -> ShopKeeperSkinsUtils.spawnShopKeeperNPC(player, loc, shopKeeperSkin1.getIdentifier()));
                        }
                        return;
                    }

                    spawnLocations.forEach(loc -> ShopKeeperSkinsUtils.spawnShopKeeperNPC(player, loc, getIdentifier()));
                }
            };
            shopKeeperSkin.register();
        }
    }
}
