package com.jsoft.thread;

/**
 * 关于处理图片的 工厂方法
 * @author ACE
 * @time 2012-6-30
 * @version 1.0
 */
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MyImageFactory {
	public static BufferedImage createReflection(BufferedImage image) {
		int height = image.getHeight();

		BufferedImage result = new BufferedImage(image.getWidth(), height * 2,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = result.createGraphics();

		// Paints original image
		g2.drawImage(image, 0, 0, null);

		// Paints mirrored image
		g2.scale(1.0, -1.0); // 横坐标乘以1，纵坐标乘以-1，实现图像反转
		g2.drawImage(image, 0, -height - height, null);
		g2.scale(1.0, -1.0);

		// Move to the origin of the clone
		g2.translate(0, height);

		// Creates the alpha mask
		GradientPaint mask;
		mask = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, 0.5f), 0,
				height / 2, new Color(1.0f, 1.0f, 1.0f, 0.0f));
		g2.setPaint(mask);

		// Sets the alpha composite
		g2.setComposite(AlphaComposite.DstIn);

		// Paints the mask
		g2.fillRect(0, 0, image.getWidth(), height);

		g2.dispose();

		return result;
	}
}
