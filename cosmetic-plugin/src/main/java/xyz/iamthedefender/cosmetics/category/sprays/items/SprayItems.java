package xyz.iamthedefender.cosmetics.category.sprays.items;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.Spray;
import xyz.iamthedefender.cosmetics.category.sprays.util.SpraysUtil;
import xyz.iamthedefender.cosmetics.util.StringUtils;

import java.util.List;

public class SprayItems {
    private static final java.util.Map<String, String> NAMES = new java.util.HashMap<>();
    static {
        NAMES.put("server-logo", "服务器标徽");
        NAMES.put("diamond", "钻石");
        NAMES.put("creeper", "苦力怕");
        NAMES.put("thanks", "感谢");
        NAMES.put("invisibility-potion", "隐形药水");
        NAMES.put("disco-pumpkin", "迪斯科南瓜");
        NAMES.put("reveillion", "圣诞晚宴");
        NAMES.put("christmas-tree", "圣诞树");
        NAMES.put("easter-eggs", "复活节彩蛋");
        NAMES.put("careful-santa", "小心圣诞老人");
        NAMES.put("bunny-GG", "兔子GG");
        NAMES.put("fireworks", "烟花");
        NAMES.put("year-of-the-dog", "狗年");
        NAMES.put("year-of-the-pig", "猪年");
        NAMES.put("lantern", "花灯");
        NAMES.put("year-of-the-rat", "鼠年");
        NAMES.put("year-of-the-ox", "牛年");
        NAMES.put("lion-Dancer", "舞狮");
        NAMES.put("year-of-the-tiger", "虎年");
        NAMES.put("sorry", "抱歉");
        NAMES.put("enderman", "末影人");
    }


    public static void registerConfigItems(){
        ConfigurationSection section = CosmeticsType.Sprays.getConfig().getYml().getConfigurationSection(CosmeticsType.Sprays.getSectionKey());
        if (section == null) return;
        ConfigManager config = CosmeticsType.Sprays.getConfig();
        for(String id : section.getKeys(false)) {
            String path = CosmeticsType.Sprays.getSectionKey() + "." + id + ".";
            Spray spray = new Spray() {
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
                        return List.of("&7选择此选项将停用", "&7你的喷雾。");
                    }
                    return List.of("&7选择" + getDisplayName() + "作为你的", "&7喷雾！");
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
                public void execute(Player player, ItemFrame frame) {
                    SpraysUtil.spawnSprays(player, frame, false, this);
                }
            };
            spray.register();
        }
    }
}
