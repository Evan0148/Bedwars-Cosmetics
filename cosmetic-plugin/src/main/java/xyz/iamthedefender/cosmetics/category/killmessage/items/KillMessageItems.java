package xyz.iamthedefender.cosmetics.category.killmessage.items;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.iamthedefender.cosmetics.CosmeticsPlugin;
import xyz.iamthedefender.cosmetics.api.configuration.ConfigManager;
import xyz.iamthedefender.cosmetics.api.cosmetics.CosmeticsType;
import xyz.iamthedefender.cosmetics.api.cosmetics.RarityType;
import xyz.iamthedefender.cosmetics.api.cosmetics.category.KillMessage;
import xyz.iamthedefender.cosmetics.util.StringUtils;

import java.util.List;

public class KillMessageItems {
    private static final java.util.Map<String, String> NAMES = new java.util.HashMap<>();
    static {
        NAMES.put("default", "默认");
        NAMES.put("thunder", "雷霆套装");
        NAMES.put("fire", "烈火焚身");
        NAMES.put("western", "西部牛仔");
        NAMES.put("honourable", "绅士");
        NAMES.put("love", "甜蜜爱心");
        NAMES.put("bbq", "烧烤派对");
        NAMES.put("woof-woof", "汪汪小狗");
        NAMES.put("santas-workshop", "圣诞工坊");
        NAMES.put("primal", "原始狩猎");
        NAMES.put("squeak", "吱吱老鼠");
        NAMES.put("pirate", "加勒比海盗");
        NAMES.put("spooked", "惊魂夜");
        NAMES.put("memed", "梗王");
        NAMES.put("buzz", "蜂群嗡嗡");
        NAMES.put("dramatic", "悲剧剧场");
        NAMES.put("oink", "哼哼小猪");
    }


    public static void registerConfigItems(){
        ConfigurationSection section = CosmeticsType.KillMessages.getConfig().getYml().getConfigurationSection(CosmeticsType.KillMessages.getSectionKey());
        if (section == null) return;
        ConfigManager config = CosmeticsType.KillMessages.getConfig();

        for(String id : section.getKeys(false)){
            try {
                String path = CosmeticsType.KillMessages.getSectionKey() + "." + id + ".";
                KillMessage killMessage = new KillMessage() {
                    // Do nothing because API currently doesn't support it
                    @Override
                    public String execute(Player player) {
                        return null;
                    }

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
                        return List.of("&7选择 " + getDisplayName() + " 击杀信息，", "&7替换游戏内聊天栏", "&7的击杀消息！");
                    }

                    @Override
                    public int getPrice() {
                        return config.getInt(path + "price");
                    }

                    @Override
                    public RarityType getRarity() {
                        return RarityType.valueOf(config.getString(path + "rarity").toUpperCase());
                    }
                };
                killMessage.register();
            }catch (Exception e){
                CosmeticsPlugin.getInstance().getLogger().warning("Error while loading KillMessage: " + id);
            }
        }
    }
}
