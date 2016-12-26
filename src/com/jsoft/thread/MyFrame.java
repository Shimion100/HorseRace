package com.jsoft.thread;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MyFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = -5835718242965181919L;

	private String guess = "";

	public static int DEFAULT_WIDTH = 960;

	public static int DEFAULT_HEIGHT = 630;

	private RacePan pan = null;

	private GlassPane glassPane = null;

	private SouthPane southPane = null;

	private ChoosePalyer choose = null;

	private int status = 0;

	private Timer timer = null;

	private float alpha = 0;

	private HashMap<String, String> map = new HashMap<String, String>();

	public MyFrame() {
		super("动物赛跑！");

		map.put("status", "start");
		
		this.setPan(new RacePan(map));

		this.glassPane = new GlassPane();

		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		this.setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(this.pan, BorderLayout.CENTER);

		this.setGlassPane(this.glassPane);

		this.glassPane.setVisible(true);

		this.southPane = new SouthPane(map);

		this.choose = new ChoosePalyer();

		this.add(southPane, BorderLayout.SOUTH);

		this.timer = new Timer(60, this);

		this.timer.start();
	}

	public RacePan getPan() {
		return pan;
	}

	public void setPan(RacePan pan) {
		this.pan = pan;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (this.map.get("status").equals("choose")) {
			this.getGlassPane().setVisible(true);
			//选完之后会将status = 3；
		}
		if (this.status == 1) {
			// 模糊第一个GlassPane的代码
			if (this.alpha <= 0.94) {
				this.alpha += 0.05;
				this.glassPane.repaint();
			} else {
				this.alpha = 0;
				this.status = 2;
				this.glassPane.setVisible(false);
				this.setGlassPane(this.choose);
				this.choose.setVisible(true);
			}
		} else if (this.status == 2) {
			// 使第二个GlassPane清晰
			if (this.alpha <= 0.96) {
				this.alpha += 0.04;
				this.choose.repaint();
			} else {
				this.alpha = 1;
				this.status = -1;
			}
		} else if (this.status == 3) {
			//选择角色完成，静茹游戏
			this.getGlassPane().setVisible(false);
			if (null != map.get("guess")) {
				map.remove("guess");
				map.put("guess", this.guess);
			} else {
				map.put("guess", this.guess);
			}
			map.remove("status");
			map.put("status", "reset");
			this.status = -1; 			//-1代表现在什么都不做
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ChoosePalyer getChoose() {
		return choose;
	}

	public void setChoose(ChoosePalyer choose) {
		this.choose = choose;
	}

	public String getGuess() {
		return guess;
	}

	public void setGuess(String guess) {
		this.guess = guess;
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	// 内部类
	class GlassPane extends JComponent implements ActionListener {

		private static final long serialVersionUID = -571577652364895409L;

		private MyImageButton but = null;

		private BufferedImage img = null;

		public GlassPane() {

			super();

			this.setLayout(null);

			this.img = StaticValue.galasImage;

			this.but = new MyImageButton("Race", StaticValue.butImage2,
					StaticValue.butImage1);

			this.but.setBounds(350, 470, StaticValue.butImage1.getWidth(),
					StaticValue.butImage1.getHeight());

			this.add(but);

			this.addMouseListener(new MouseAdapter() {
			});

			this.addMouseMotionListener(new MouseMotionAdapter() {
			});

			this.addKeyListener(new KeyAdapter() {
			});

			// 设置监听获得鼠标
			this.addComponentListener(new ComponentAdapter() {

				@Override
				public void componentShown(ComponentEvent e) {
					// TODO Auto-generated method stub
					requestFocusInWindow();
				}

			});

			// 设置鼠标键盘通过、通过玻璃面板操作为false
			this.setFocusTraversalKeysEnabled(false);

			this.but.addActionListener(this);

		}

		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub

			Graphics2D g2d = (Graphics2D) g;
			Rectangle clip = g.getClipBounds();

			Color alphaWhite = new Color(1.0f, 1.0f, 1.0f, 0.5f);

			g.setColor(alphaWhite);

			g.fillRect(clip.x, clip.y, clip.width, clip.height);
			if (status == 1) {
				g2d.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1 - alpha));
			}
			g2d.drawImage(MyImageFactory.createReflection(this.img),
					(this.getWidth() - this.img.getWidth()) / 2,
					(this.getHeight() - this.img.getHeight()) / 2 - 70, this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == this.but) {
				setStatus(1);
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
		}
	}

	// 第二个内部类
	class ChoosePalyer extends JPanel implements ActionListener {

		private static final long serialVersionUID = 2127116944196923044L;

		private static final int WIDTH = 600;

		private JPanel north = null;

		private JPanel south = null;

		private MyImageButton ma = null;

		private MyImageButton mao = null;

		private MyImageButton bao = null;

		private MyImageButton xiang = null;

		private CardLayout card = null;

		public ChoosePalyer() {
			super();

			this.setOpaque(false);

			this.setLayout(null);

			this.addMouseListener(new MouseAdapter() {
			});

			this.addMouseMotionListener(new MouseMotionAdapter() {
			});

			this.addKeyListener(new KeyAdapter() {
			});

			// 设置监听获得鼠标
			this.addComponentListener(new ComponentAdapter() {

				@Override
				public void componentShown(ComponentEvent e) {
					// TODO Auto-generated method stub
					requestFocusInWindow();
				}

			});

			// 设置鼠标键盘通过、通过玻璃面板操作为false
			this.setFocusTraversalKeysEnabled(false);

			north = new JPanel();

			south = new JPanel();

			north.setOpaque(false);

			south.setOpaque(false);

			north.setBounds((MyFrame.DEFAULT_WIDTH - ChoosePalyer.WIDTH) / 2,
					50, 600, 100);

			north.setLayout(new GridLayout(1, 4, 3, 0));

			this.ma = new MyImageButton("马", StaticValue.minimaImage,
					StaticValue.minimaImage2);

			north.add(ma);

			this.mao = new MyImageButton("猫", StaticValue.minimaoImage,
					StaticValue.minimaoImage2);

			north.add(mao);

			this.bao = new MyImageButton("暴", StaticValue.minibaoImage,
					StaticValue.minibaoImage2);

			north.add(bao);

			this.xiang = new MyImageButton("象", StaticValue.minixiangImage,
					StaticValue.minixiangImage2);

			north.add(xiang);

			this.ma.addActionListener(this);
			this.mao.addActionListener(this);
			this.bao.addActionListener(this);
			this.xiang.addActionListener(this);
			this.ma.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseEntered(e);
					card.show(south, "ma");
					try {
						// From file
						AudioInputStream stream = AudioSystem
								.getAudioInputStream(new File(StaticValue.path
										+ File.separator + "sounds" + File.separator
										+ "launch_upmenu1.wav"));

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
			});
			this.mao.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseEntered(e);
					card.show(south, "mao");
					try {
						// From file
						AudioInputStream stream = AudioSystem
								.getAudioInputStream(new File(StaticValue.path
										+ File.separator + "sounds" + File.separator
										+ "launch_upmenu1.wav"));

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
			});
			this.bao.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseEntered(e);
					card.show(south, "bao");
					try {
						// From file
						AudioInputStream stream = AudioSystem
								.getAudioInputStream(new File(StaticValue.path
										+ File.separator + "sounds" + File.separator
										+ "launch_upmenu1.wav"));

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
			});
			this.xiang.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseEntered(e);
					card.show(south, "xiang");
					try {
						// From file
						AudioInputStream stream = AudioSystem
								.getAudioInputStream(new File(StaticValue.path
										+ File.separator + "sounds" + File.separator
										+ "launch_upmenu1.wav"));

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
			});

			south.setBounds((MyFrame.DEFAULT_WIDTH - ChoosePalyer.WIDTH) / 2,
					north.getY() + north.getHeight() + 10, 600, 350);

			this.add(north, BorderLayout.NORTH);

			this.add(south, BorderLayout.CENTER);

			card = new CardLayout();

			south.setLayout(card);

			south.add(new OpaquePanel(StaticValue.maImage, "马"), "ma");

			south.add(new OpaquePanel(StaticValue.maoImage, "马"), "mao");

			south.add(new OpaquePanel(StaticValue.baoImage, "马"), "bao");

			south.add(new OpaquePanel(StaticValue.xiangImage, "马"), "xiang");

			card.show(south, "mao");
		}

		@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			Graphics2D g2d = (Graphics2D) g;
			Rectangle clip = g.getClipBounds();

			Color alphaWhite = new Color(1.0f, 1.0f, 1.0f, 0.5f);

			g.setColor(alphaWhite);
			g.fillRect(clip.x, clip.y, clip.width, clip.height);
			g2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, alpha));
			super.paint(g);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == this.ma) {
				guess = "ma";
			} else if (e.getSource() == this.mao) {
				guess = "mao";
			} else if (e.getSource() == this.bao) {
				guess = "bao";
			} else if (e.getSource() == this.xiang) {
				guess = "xiang";
			}
			status = 3;
		}
	}
}
