package org.playuniverse.school.numbertheory.window.utils;

import java.awt.FontMetrics;

public final class DrawTools {

	private DrawTools() {}

	/*
	 * 
	 */

	public static String shrinkString(String input, FontMetrics metrics) {
		return shrinkString(input, metrics, true);
	}

	public static String shrinkString(String input, FontMetrics metrics, boolean dots) {
		return shrinkString(input, metrics, dots ? 280 - (metrics.charWidth('.') * 3) : 280, dots);
	}

	public static String shrinkString(String input, FontMetrics metrics, int size, boolean dots) {
		String output = input;
		int width = metrics.stringWidth(input);
		if (width > size) {
			output = output.substring(0, output.length() - 1);
			return dots ? shrinkString(output.trim(), metrics, size, false) + "..." : shrinkString(output.trim(), metrics, size, false);
		}
		return output;
	}

}
