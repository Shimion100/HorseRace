package com.jsoft.thread;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class StaticValue {

	// Map里的Status有Start，running，finish，wait，choose,reset
	// 其他页面听命令执行

	public static List<BufferedImage> baoList = new ArrayList<BufferedImage>();

	public static List<BufferedImage> maoList = new ArrayList<BufferedImage>();

	public static List<BufferedImage> maList = new ArrayList<BufferedImage>();

	public static List<BufferedImage> xiangList = new ArrayList<BufferedImage>();

	public static BufferedImage backImage = null;

	public static BufferedImage galasImage = null;

	public static BufferedImage butImage1 = null;

	public static BufferedImage butImage2 = null;

	// 大照片
	public static BufferedImage maImage = null;

	public static BufferedImage maoImage = null;

	public static BufferedImage baoImage = null;

	public static BufferedImage xiangImage = null;

	// 头像
	public static BufferedImage minimaImage = null;

	public static BufferedImage minimaoImage = null;

	public static BufferedImage minibaoImage = null;

	public static BufferedImage minixiangImage = null;

	public static BufferedImage minimaImage2 = null;

	public static BufferedImage minimaoImage2 = null;

	public static BufferedImage minibaoImage2 = null;

	public static BufferedImage minixiangImage2 = null;

	public static BufferedImage reset = null;

	public static BufferedImage reset1 = null;

	public static BufferedImage start = null;

	public static BufferedImage start1 = null;

	public static BufferedImage southBg = null;
	public static String path = System.getProperty("user.dir");

	public static void init() {
		BufferedImage temp = null;
		for (int i = 1; i <= 8; i++) {
			try {
				temp = ImageIO.read(new File(StaticValue.path + File.separator
						+ "images" + File.separator + "b" + i + ".png"));
				baoList.add(temp);
				temp = ImageIO.read(new File(StaticValue.path + File.separator
						+ "images" + File.separator + "m" + i + ".png"));
				maoList.add(temp);
				temp = ImageIO.read(new File(StaticValue.path + File.separator
						+ "images" + File.separator + "x" + i + ".png"));
				xiangList.add(temp);
				temp = ImageIO.read(new File(StaticValue.path + File.separator
						+ "images" + File.separator + "h" + i + ".png"));
				maList.add(temp);
				StaticValue.backImage = ImageIO.read(new File(StaticValue.path
						+ File.separator + "images" + File.separator
						+ "street.png"));
				StaticValue.galasImage = ImageIO.read(new File(StaticValue.path
						+ File.separator + "images" + File.separator
						+ "glassbg1.jpg"));

				StaticValue.butImage2 = ImageIO.read(new File(StaticValue.path
						+ File.separator + "images" + File.separator
						+ "button1.png"));

				StaticValue.butImage1 = ImageIO.read(new File(StaticValue.path
						+ File.separator + "images" + File.separator
						+ "button2.png"));
				StaticValue.maImage = ImageIO.read(new File(StaticValue.path
						+ File.separator + "images" + File.separator
						+ "未标题-1.png"));
				StaticValue.maoImage = ImageIO.read(new File(StaticValue.path
						+ File.separator + "images" + File.separator
						+ "未标题-2.png"));

				StaticValue.baoImage = ImageIO.read(new File(StaticValue.path
						+ File.separator + "images" + File.separator
						+ "未标题-5.png"));

				StaticValue.xiangImage = ImageIO.read(new File(StaticValue.path
						+ File.separator + "images" + File.separator
						+ "未标题-3.png"));

				StaticValue.minimaImage = ImageIO.read(new File(
						StaticValue.path + File.separator + "images"
								+ File.separator + "未标题-11.png"));
				StaticValue.minimaoImage = ImageIO.read(new File(
						StaticValue.path + File.separator + "images"
								+ File.separator + "未标题-21.png"));

				StaticValue.minibaoImage = ImageIO.read(new File(
						StaticValue.path + File.separator + "images"
								+ File.separator + "未标题-31.png"));

				StaticValue.minixiangImage = ImageIO.read(new File(
						StaticValue.path + File.separator + "images"
								+ File.separator + "未标题-41.png"));

				StaticValue.minimaImage2 = ImageIO.read(new File(
						StaticValue.path + File.separator + "images"
								+ File.separator + "未标题-12.png"));
				StaticValue.minimaoImage2 = ImageIO.read(new File(
						StaticValue.path + File.separator + "images"
								+ File.separator + "未标题-22.png"));

				StaticValue.minibaoImage2 = ImageIO.read(new File(
						StaticValue.path + File.separator + "images"
								+ File.separator + "未标题-32.png"));

				StaticValue.minixiangImage2 = ImageIO.read(new File(
						StaticValue.path + File.separator + "images"
								+ File.separator + "未标题42.png"));

				StaticValue.reset = ImageIO.read(new File(StaticValue.path
						+ File.separator + "images" + File.separator
						+ "reset.png"));
				StaticValue.reset1 = ImageIO.read(new File(StaticValue.path
						+ File.separator + "images" + File.separator
						+ "reset1.png"));

				StaticValue.start = ImageIO.read(new File(StaticValue.path
						+ File.separator + "images" + File.separator
						+ "start.png"));

				StaticValue.start1 = ImageIO.read(new File(StaticValue.path
						+ File.separator + "images" + File.separator
						+ "start1.png"));

				StaticValue.southBg = ImageIO.read(new File(StaticValue.path
						+ File.separator + "images" + File.separator
						+ "southbg.png"));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			// From file
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(
					StaticValue.path + File.separator + "sounds"
							+ File.separator + "201006121004426443.mid"));

			// From URL
			// stream = AudioSystem.getAudioInputStream(new
			// URL("http://hostname/audiofile"));

			// At present, ALAW and ULAW encodings must be converted
			// to PCM_SIGNED before it can be played
			AudioFormat format = stream.getFormat();
			if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
				format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
						format.getSampleRate(),
						format.getSampleSizeInBits() * 2, format.getChannels(),
						format.getFrameSize() * 2, format.getFrameRate(), true); // big
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
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		} catch (LineUnavailableException e) {
		} catch (UnsupportedAudioFileException e) {
		}
	}
}
