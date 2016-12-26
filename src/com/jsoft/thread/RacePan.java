package com.jsoft.thread;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RacePan extends JPanel implements Runnable, ActionListener {

	private static final long serialVersionUID = -1241991204992953344L;

	private HashMap<String, String> map = null;

	public static int DEFAULT_WIDTH = 960;

	public static int DEFAULT_HEIGHT = 500;

	private List<Racer> racerList = new ArrayList<Racer>();

	private Thread t = null;

	private BackGround bg = null;

	private Timer timer = null;

	private boolean isRunning = true;

	public RacePan(HashMap<String, String> map) {
		super();

		this.setPreferredSize(new Dimension(RacePan.DEFAULT_WIDTH,
				RacePan.DEFAULT_HEIGHT));

		this.setMap(map);

		this.setTimer(new Timer(60, this));

		this.timer.start();

		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		this.racerList.add(new Racer(60, 30, 1, createSpeed()));

		this.racerList.add(new Racer(60, 120, 2, createSpeed()));

		this.racerList.add(new Racer(60, 220, 3, createSpeed()));

		this.racerList.add(new Racer(60, 350, 4, createSpeed()));

		this.bg = new BackGround(0, 0, 5, this.map);

		this.t = new Thread(this);

	}

	public void paint(Graphics g) {
		super.paint(g);
		BufferedImage image = new BufferedImage(960, 500,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics g2 = image.getGraphics();
		g2.drawImage(bg.getShowImage(), bg.getX(), 0, this);
		Iterator<Racer> it = this.racerList.iterator();
		while (it.hasNext()) {
			Racer rac = it.next();
			g2.drawImage(rac.getShowImage(), rac.getX(), rac.getY(), this);
		}
		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		barek_mark: while (true) {
			this.repaint();
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Iterator<Racer> it = this.racerList.iterator();
			while (it.hasNext()) {
				Racer rac = it.next();
				if (rac.isFinish()) {
					map.remove("status");
					map.put("status", "finish");
					if (null != this.map.get("guess")) {
						this.winOrLose(rac.getType());
					}

					break barek_mark;
				}
			}
		}
	}

	public void winOrLose(int winType) {
		String guess = map.get("guess");
		int type = 0;
		if (guess.equals("ma")) {
			type = 1;
		} else if (guess.equals("mao")) {
			type = 2;
		} else if (guess.equals("bao")) {
			type = 3;
		} else if (guess.equals("xiang")) {
			type = 4;
		}
		if (winType == type) {
			JOptionPane.showMessageDialog(this, "恭喜，你赢了！");
		} else {
			JOptionPane.showMessageDialog(this, "你输了，再来一次吧！");
		}
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public int createSpeed() {
		return (int) Math.ceil((Math.random() * 9));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (this.map.get("status").equals("start")) {
			if (!this.t.isAlive()) {
				this.t = new Thread(this);
				this.t.start();
			}
			if (!this.isRunning) {
				Iterator<Racer> it = this.racerList.iterator();
				while (it.hasNext()) {
					Racer rac = it.next();
					rac.restart();
				}
				this.isRunning = true;
			}
		} else if (this.map.get("status").equals("reset")) {
			Iterator<Racer> it = this.racerList.iterator();
			while (it.hasNext()) {
				Racer rac = it.next();
				rac.reset();
			}
			this.isRunning = false;
		}
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
