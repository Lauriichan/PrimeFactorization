package org.playuniverse.school.numbertheory.window.utils;

import static org.playuniverse.school.numbertheory.window.WindowHandler.getLogger;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class FontCache {

	private static HashMap<String, Font> fonts = new HashMap<>();

	public static Font getFont(String id) {
		if (fonts.containsKey(id))
			return fonts.get(id);
		return null;
	}

	public static Font getFont(String id, InputStream stream) {
		if (fonts.containsKey(id))
			return fonts.get(id);
		Font font = null;
		try {
			font = Font.createFont(Font.TYPE1_FONT, stream);
		} catch (IOException | FontFormatException e) {
			getLogger().log(e);
		}
		if (font != null)
			fonts.put(id, font);
		return font;
	}

	public static Font getFontFromResources(String id, String path) {
		if (fonts.containsKey(id))
			return fonts.remove(id);
		InputStream stream = FontCache.class.getClassLoader().getResourceAsStream(path);
		return getFont(id, stream);
	}

}
