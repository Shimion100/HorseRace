package com.jsoft.thread;

/**
 * 自己做的Button，
 * 透明，可以有图片
 */
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

public class MyImageButton extends JButton {

	private static final long serialVersionUID = 1901430872154380955L;

	private BufferedImage image = null;

	private BufferedImage pressed = null;

	public MyImageButton(String title, BufferedImage img, BufferedImage pressed) {

		super(title);

		this.image = img;

		this.pressed = pressed;

		this.setText(title);

		this.setBorderPainted(false);

		this.setOpaque(false);

		this.setPreferredSize(new Dimension(this.image.getWidth(),
				(int) (this.image.getHeight())));

	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(this.image,
				this.getWidth() / 2 - this.image.getWidth() / 2, 0, null);
		Graphics2D g2d = (Graphics2D) g;

		if (this.getModel().isRollover()) {
			g2d.drawImage(this.pressed,
					this.getWidth() / 2 - this.image.getWidth() / 2, 0, null);
		}

		if (this.getModel().isPressed()) {
			g.drawImage(MyImageFactory.createReflection(pressed),
					this.getWidth() / 2 - this.image.getWidth() / 2, 0, null);
		}

	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getPressed() {
		return pressed;
	}

	public void setPressed(BufferedImage pressed) {
		this.pressed = pressed;
	}
}
