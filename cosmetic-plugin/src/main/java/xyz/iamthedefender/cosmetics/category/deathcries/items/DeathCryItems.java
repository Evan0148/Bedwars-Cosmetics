package xyz.iamthedefender.cosmetics.category.deathcries.items;

import com.cryptomorin.xseries.XSound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.DeathCry;
import xyz.iamthedefender.cosmetics.api.util.config.ConfigUtils;
import xyz.iamthedefender.cosmetics.util.StringUtils;

import java.util.List;

public class DeathCryItems {
    private static final java.util.Map<String, String> NAMES = new java.util.HashMap<>();
    static {
        NAMES.put("none", "无");
        NAMES.put("warden", "监守者音波");
        NAMES.put("thunder", "雷霆之声");
        NAMES.put("bazinga", "Bazinga!");
        NAMES.put("enderman", "末影人");
        NAMES.put("pig", "猪哼哼");
        NAMES.put("bat", "蝙蝠尖啸");
        NAMES.put("skeleton", "骷髅碎响");
        NAMES.put("wolf", "恶犬咆哮");
        NAMES.put("burp", "打嗝");
        NAMES.put("firework", "烟花咻咻");
        NAMES.put("splash", "入水扑通");
        NAMES.put("sad-puppy", "悲伤小狗");
        NAMES.put("sad-moo", "伤感的哞");
        NAMES.put("plop", "噗通");
        NAMES.put("ding", "叮~");
        NAMES.put("fireball", "火球轰");
        NAMES.put("explode", "爆炸");
        NAMES.put("zombie", "僵尸低吼");
        NAMES.put("breath", "喘息");
        NAMES.put("click", "咔哒");
        NAMES.put("creepy", "阴森低语");
    }


    public void registerConfigItems(){
        FileConfiguration config = ConfigUtils.getDeathCries().getYml();
        ConfigManager configManager = ConfigUtils.getDeathCries();
        ConfigurationSection deathCrySection = config.getConfigurationSection("death-cry");

        for (String identifier : deathCrySection.getKeys(false)) {
            String path = "death-cry." + identifier + ".";
            DeathCry deathCry = new DeathCry() {
                @Override
                public ItemStack getItem() {
                    return configManager.getItemStack(path + "item");
                }

                @Override
                public String base64() {
                    String[] base64 = configManager.getString(path + "item").split(":", 3);
                    return base64[2];
                }

                @Override
                public String getIdentifier() {
                    return identifier;
                }

                @Override
                public String getDisplayName() {
                    String custom = NAMES.get(identifier);
            return custom != null ? custom : StringUtils.replaceHyphensAndCaptalizeFirstLetter(identifier);
                }

                @Override
                public List<String> getLore() {
                    if (getRarity() == RarityType.NONE){
                        return List.of("&7选择此选项将停用", "&7你的死亡音效。");
                    }
                    return List.of("&7选择" + getDisplayName() + "作为你的死亡音效");
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
                public XSound getSound() {
                    return XSound.matchXSound(config.getString(path + "sound")).get();
                }


                @Override
                public float getPitch() {
                    return Float.parseFloat(config.getString(path + "pitch"));
                }

                @Override
                public float getVolume() {
                    return Float.parseFloat(config.getString(path + "volume"));
                }
            };
            deathCry.register();
        }
    }
}
