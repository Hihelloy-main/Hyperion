package me.moros.hyperion.util;

import net.md_5.bungee.api.ChatColor;

public class HexColor {

    /**
     * Returns a ChatColor from a hex string like "#FF0000".
     *
     * @param hex the hex color string
     * @return ChatColor instance
     */
    public static ChatColor of(String hex) {
        if (hex == null || !hex.matches("^#([A-Fa-f0-9]{6})$")) {
            throw new IllegalArgumentException("Invalid hex color format: " + hex);
        }
        return ChatColor.of(hex);
    }

    // Classic rainbow color constants
    public static final ChatColor RED = of("#FF0000");
    public static final ChatColor ORANGE = of("#FF7F00");
    public static final ChatColor YELLOW = of("#FFFF00");
    public static final ChatColor GREEN = of("#00FF00");
    public static final ChatColor BLUE = of("#0000FF");
    public static final ChatColor INDIGO = of("#4B0082");
    public static final ChatColor VIOLET = of("#8F00FF");

    /**
     * Special rainbow color (can be used as a placeholder).
     * Since ChatColor doesn't support multi-color, this is just a bright yellow to represent rainbow.
     */
    public static final ChatColor RAINBOW = YELLOW;

    /**
     * Builds a rainbow-colored string by coloring each character with a different rainbow hex.
     *
     * @param text the input text
     * @return rainbow-colored string
     */
    public static String rainbowify(String text) {
        String[] rainbow = {
                "#FF0000", // red
                "#FF7F00", // orange
                "#FFFF00", // yellow
                "#00FF00", // green
                "#0000FF", // blue
                "#4B0082", // indigo
                "#8F00FF"  // violet
        };

        StringBuilder result = new StringBuilder();
        int colorIndex = 0;

        for (char c : text.toCharArray()) {
            result.append(ChatColor.of(rainbow[colorIndex % rainbow.length])).append(c);
            colorIndex++;
        }

        return result.toString();
    }
}
