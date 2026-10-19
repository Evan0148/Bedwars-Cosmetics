package xyz.iamthedefender.cosmetics.api.handler;

import org.bukkit.entity.Player;

import java.util.List;

public interface ILanguage {

    String getMessage(Player player, String path);
    List<String> getMessageList(Player player, String path);
    void saveIfNotExists(String path, Object data);

    /**
     * Clears all cosmetic language entries when the stored
     * version differs from the current default-language version,
     * so missing entries are regenerated from code defaults.
     */
    default void resetForLanguageVersion(int version) {
    }
}
