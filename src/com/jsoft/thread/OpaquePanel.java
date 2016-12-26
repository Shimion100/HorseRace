package com.jsoft.thread;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

public class OpaquePanel extends JButton {

	private static final long serialVersionUID = -3531878986197572422L;

	private JLabel textLab = null;

	private BufferedImage img = null;

	public OpaquePanel(BufferedImage img, String text) {

		super();

		this.img = img;

		this.setOpaque(false);

		this.textLab = new JLabel(text);

		this.setLayout(new BorderLayout());

		this.add(textLab, BorderLayout.SOUTH);

		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));
		
		this.setBorderPainted(true);

	}

	public JLabel getTextLab() {
		return textLab;
	}

	public void setTextLab(JLabel textLab) {
		this.textLab = textLab;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Rectangle clip = g.getClipBounds();
		Color alphaWhite = new Color(1.0f, 1.0f, 1.0f, 0.5f);

		g.setColor(alphaWhite);
		g.fillRect(clip.x, clip.y, clip.width, clip.height);
		g.drawImage(this.img, 0, 0, this);
		super.paintComponents(g2d);
		super.paintBorder(g2d);
	}

}
