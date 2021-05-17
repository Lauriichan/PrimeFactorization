package org.playuniverse.school.numbertheory.window.utils;

import static org.playuniverse.school.numbertheory.window.WindowHandler.getLogger;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageCache {

	private static HashMap<String, Image> images = new HashMap<>();

	public static Image getImage(String id, File file) {
		return getImage(id, file, false, 0);
	}

	public static Image getImage(String id, InputStream stream) {
		return getImage(id, stream, false, 0);
	}

	public static Image getImage(String id, URL url) {
		return getImage(id, url, false, 0);
	}

	public static Image getImage(String id, String url) {
		return getImage(id, url, false, 0);
	}

	public static Image getImageFromResources(String id, String path) {
		return getImageFromResources(id, path, false, 0);
	}

	public static Image getImage(String id, File file, boolean crop, int scale) {
		if (images.containsKey(id))
			return images.get(id);
		Image image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			getLogger().log(e);
		}
		if (image != null && crop) {
			image = crop((BufferedImage) image);
			if (scale > 0)
				image = image.getScaledInstance(scale, scale, 0);
		}
		if (image != null)
			images.put(id, image);
		return image;
	}

	public static Image getImage(String id, InputStream stream, boolean crop, int scale) {
		if (images.containsKey(id))
			return images.get(id);
		Image image = null;
		try {
			image = ImageIO.read(stream);
		} catch (IOException e) {
			getLogger().log(e);
		}
		if (image != null && crop) {
			image = crop((BufferedImage) image);
			if (scale > 0)
				image = image.getScaledInstance(scale, scale, 0);
		}
		if (image != null)
			images.put(id, image);
		return image;
	}

	public static Image getImage(String id, URL url, boolean crop, int scale) {
		if (images.containsKey(id))
			return images.get(id);
		Image image = null;
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			getLogger().log(e);
		}
		if (image != null && crop) {
			image = crop((BufferedImage) image);
			if (scale > 0)
				image = image.getScaledInstance(scale, scale, 0);
		}
		if (image != null)
			images.put(id, image);
		return image;
	}

	public static Image getImage(String id, String url, boolean crop, int scale) {
		if (images.containsKey(id))
			return images.get(id);
		try {
			return getImage(id, new URL(url), crop, scale);
		} catch (MalformedURLException e) {
			getLogger().log(e);
		}
		return null;
	}

	public static Image getImageFromResources(String id, String path, boolean crop, int scale) {
		if (images.containsKey(id))
			return images.remove(id);
		InputStream stream = ImageCache.class.getClassLoader().getResourceAsStream(path);
		return getImage(id, stream, crop, scale);
	}

	/*
	 * 
	 */

	public static Image getImage(String id) {
		if (images.containsKey(id))
			return images.get(id);
		return null;
	}

	public static BufferedImage crop(BufferedImage cover) {
		int width = cover.getWidth(null);
		int height = cover.getHeight(null);
		if (width == height) {
			return cover;
		}
		int size = width > height ? height : width;
		BufferedImage output = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graph = output.createGraphics();
		if (width > height) {
			graph.drawImage(cover.getSubimage(((width - height) / 2) - 1, 0, height, height), 0, 0, null);
		} else {
			graph.drawImage(cover.getSubimage(0, ((height - width) / 2) - 1, width, width), 0, 0, null);
		}
		cover.flush();
		return output;
	}

	public static void deleteImage(String id) {
		Image image = images.remove(id);
		if (image != null)
			image.flush();
	}

}
