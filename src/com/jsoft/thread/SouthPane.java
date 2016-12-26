package com.jsoft.thread;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SouthPane extends JPanel implements ActionListener {

	private static final long serialVersionUID = -7233319081569717852L;

	private HashMap<String, String> map = null;

	private MyImageButton reset = null;

	private MyImageButton start = null;

	private Timer timer = null;

	private JLabel lab = null;

	public SouthPane(HashMap<String, String> map) {
		super();

		this.setPreferredSize(new Dimension(960, 100));

		this.setBorder(BorderFactory.createLineBorder(new Color(0xFEBC30), 5,
				true));

		this.map = map;

		this.reset = new MyImageButton("reset", StaticValue.reset,
				StaticValue.reset1);

		this.start = new MyImageButton("start", StaticValue.start,
				StaticValue.start1);

		this.setLayout(new GridLayout(1, 6));
		this.setLab(new JLabel(new ImageIcon(StaticValue.minimaImage)));
		this.add(new JLabel("              "));
		this.add(new JLabel("              "));
		this.add(this.lab);

		this.add(reset);

		this.add(start);

		this.start.addActionListener(this);

		this.reset.addActionListener(this);

		this.timer = new Timer(60, this);

		this.timer.start();
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(StaticValue.southBg, 0, 0, this);
		super.paintComponents(g);
		super.paintBorder(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (null != map && null != map.get("guess")) {
			String show = map.get("guess");
			if (show.equals("ma")) {
				this.lab.setIcon(new ImageIcon(StaticValue.minimaImage));
			} else if (show.equals("mao")) {
				this.lab.setIcon(new ImageIcon(StaticValue.minimaoImage));
			} else if (show.equals("bao")) {
				this.lab.setIcon(new ImageIcon(StaticValue.minibaoImage));
			} else if (show.equals("xiang")) {
				this.lab.setIcon(new ImageIcon(StaticValue.minixiangImage));
			}

		}
		if (e.getSource() == this.start) {
			this.map.remove("status");
			this.map.put("status", "start");
			try {
				// From file
				AudioInputStream stream = AudioSystem
						.getAudioInputStream(new File(StaticValue.path
								+ File.separator + "sounds" + File.separator
								+ "launch_select2.wav"));

				// From URL
				// stream = AudioSystem.getAudioInputStream(new
				// URL("http://hostname/audiofile"));

				// At present, ALAW and ULAW encodings must be converted
				// to PCM_SIGNED before it can be played
				AudioFormat format = stream.getFormat();
				if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
					format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
							format.getSampleRate(),
							format.getSampleSizeInBits() * 2,
							format.getChannels(), format.getFrameSize() * 2,
							format.getFrameRate(), true); // big
															// endian
					stream = AudioSystem.getAudioInputStream(format, stream);
				}

				// Create the clip
				DataLine.Info info = new DataLine.Info(Clip.class,
						stream.getFormat(),
						((int) stream.getFrameLength() * format.getFrameSize()));
				Clip clip = (Clip) AudioSystem.getLine(info);

				// This method does not return until the audio file is
				// completely
				// loaded
				clip.open(stream);

				// Start playing
				clip.start();
			} catch (MalformedURLException e1) {
			} catch (IOException e2) {
			} catch (LineUnavailableException e3) {
			} catch (UnsupportedAudioFileException e4) {
			}
		}
		if (e.getSource() == this.reset) {
			map.remove("status");
			map.put("status", "choose");
			try {
				// From file
				AudioInputStream stream = AudioSystem
						.getAudioInputStream(new File(StaticValue.path
								+ File.separator + "sounds" + File.separator
								+ "launch_select2.wav"));

				// From URL
				// stream = AudioSystem.getAudioInputStream(new
				// URL("http://hostname/audiofile"));

				// At present, ALAW and ULAW encodings must be converted
				// to PCM_SIGNED before it can be played
				AudioFormat format = stream.getFormat();
				if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
					format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
							format.getSampleRate(),
							format.getSampleSizeInBits() * 2,
							format.getChannels(), format.getFrameSize() * 2,
							format.getFrameRate(), true); // big
															// endian
					stream = AudioSystem.getAudioInputStream(format, stream);
				}

				// Create the clip
				DataLine.Info info = new DataLine.Info(Clip.class,
						stream.getFormat(),
						((int) stream.getFrameLength() * format.getFrameSize()));
				Clip clip = (Clip) AudioSystem.getLine(info);

				// This method does not return until the audio file is
				// completely
				// loaded
				clip.open(stream);

				// Start playing
				clip.start();
			} catch (MalformedURLException e5) {
			} catch (IOException e6) {
			} catch (LineUnavailableException e7) {
			} catch (UnsupportedAudioFileException e8) {
			}
		}
	}

	public JLabel getLab() {
		return lab;
	}

	public void setLab(JLabel lab) {
		this.lab = lab;
	}
}
