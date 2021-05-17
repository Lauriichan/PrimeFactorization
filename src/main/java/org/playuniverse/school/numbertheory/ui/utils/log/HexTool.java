package org.playuniverse.school.numbertheory.ui.utils.log;

import java.awt.Color;
import java.util.HashMap;

import com.syntaxphoenix.syntaxapi.logging.color.ColorTools;

public final class HexTool {

	private static final HashMap<String, Color> HEX_CACHE = new HashMap<>();

	private HexTool() {}

	/*
	 * 
	 */

	public static String asHex(Color color) {
		return '#' + Integer.toHexString(color.getRed()) + Integer.toHexString(color.getGreen()) + Integer.toHexString(color.getBlue());
	}

	public static Color parse(String hex) {
		return HEX_CACHE.computeIfAbsent(hex = hex.replace("#", ""), hexHash -> ColorTools.hex2rgb(hexHash));
	}

}
