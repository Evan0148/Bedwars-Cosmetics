package xyz.iamthedefender.cosmetics.category.projectiletrails.items;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.ProjectileTrail;
import xyz.iamthedefender.cosmetics.util.StringUtils;

import java.util.List;

public class ProjectileTrailItems {
    private static final java.util.Map<String, String> NAMES = new java.util.HashMap<>();
    static {
        NAMES.put("none", "无");
        NAMES.put("slime", "史莱姆");
        NAMES.put("ender", "末影");
        NAMES.put("lava", "熔岩");
        NAMES.put("potion", "药水");
        NAMES.put("water", "水流");
        NAMES.put("black-smoke", "黑烟");
        NAMES.put("white-smoke", "白烟");
        NAMES.put("note", "音符");
        NAMES.put("blue-dust", "蓝色微粒");
        NAMES.put("red-dust", "红色微粒");
        NAMES.put("purple-dust", "紫色微粒");
        NAMES.put("fire", "火焰");
        NAMES.put("magic", "魔法");
        NAMES.put("firework", "烟花");
        NAMES.put("angry-villager", "愤怒村民");
        NAMES.put("happy-villager", "欢乐村民");
        NAMES.put("portal", "传送门粒子");
    }


    public void registerConfigItems(){
        ConfigurationSection section = CosmeticsType.ProjectileTrails.getConfig().getYml().getConfigurationSection(CosmeticsType.ProjectileTrails.getSectionKey());
        if (section == null) return;
        ConfigManager config = CosmeticsType.ProjectileTrails.getConfig();
        for(String id : section.getKeys(false)){
            String path = CosmeticsType.ProjectileTrails.getSectionKey() + "." + id + ".";
            ProjectileTrail trails = new ProjectileTrail() {
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
                    if (getRarity() == RarityType.NONE){
                        return List.of("&7选择此选项将停用", "&7你的弹道特效。");
                    }
                    return List.of("&7选择" + getDisplayName() + "作为你的", "&7弹道特效！");
                }

                @Override
                public int getPrice() {
                    return config.getInt(path + "price");
                }

                @Override
                public RarityType getRarity() {
                    return RarityType.valueOf(config.getString(path + "rarity").toUpperCase().toUpperCase());
                }

                // Does nothing because API doesn't support it yet.
                @Override
                public String execute(Player player) {
                    return null;
                }
            };
            trails.register();
        }
    }
}
