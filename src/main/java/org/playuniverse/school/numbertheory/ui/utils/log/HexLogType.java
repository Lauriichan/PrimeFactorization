package org.playuniverse.school.numbertheory.ui.utils.log;

import java.awt.Color;

import com.syntaxphoenix.syntaxapi.logging.color.ColorProcessor;
import com.syntaxphoenix.syntaxapi.logging.color.LogType;

public class HexLogType extends LogType {

	public static final ColorProcessor PROCESSOR = (flag, type) -> type.asColorString(flag);

	/*
	 * 
	 */

	private String color;

	/*
	 * 
	 * 
	 * 
	 */

	public HexLogType(String id) {
		this(id, "#000000");
	}

	public HexLogType(String id, String color) {
		super(id);
		this.color = color;
	}

	public HexLogType(String id, Color color) {
		super(id);
		this.color = HexTool.asHex(color);
	}

	public HexLogType(String id, String name, String color) {
		super(id, name);
		this.color = color;
	}

	public HexLogType(String id, String name, Color color) {
		super(id, name);
		this.color = HexTool.asHex(color);
	}

	/*
	 * 
	 * 
	 * 
	 */

	public void setColor(String color) {
		this.color = color == null ? this.color : color;
	}

	public void setColor(Color color) {
		setColor(HexTool.asHex(color));
	}

	/*
	 * 
	 * 
	 * 
	 */

	@Override
	public ColorProcessor getColorProcessor() {
		return PROCESSOR;
	}

	@Override
	public Color asColor() {
		return HexTool.parse(color);
	}

	@Override
	public String asColorString() {
		return color + '|';
	}

	@Override
	public String asColorString(boolean stream) {
		return stream ? asColorString() : "";
	}

}
