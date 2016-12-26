package com.jsoft.thread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.Timer;

public class BackGround implements Runnable, ActionListener {

	private int x;

	private int y;

	private int speed;

	private BufferedImage showImage = null;

	private Thread t = null;

	private boolean isRunning = false;

	private Timer timer = null;

	private HashMap<String, String> map = null;

	private int addCount = 0;

	public BackGround(int x, int y, int speed, HashMap<String, String> map) {

		this.showImage = StaticValue.backImage;

		this.setMap(map);

		this.speed = speed;

		this.setT(new Thread(this));

		this.timer = new Timer(60, this);

		this.timer.start();
	}

	public void run() {
		while (this.isRunning) {
			if (this.getX() <= -4000) {
				this.isRunning = false;
				break;
			}
			this.setX(this.x - this.speed);

			if (createBool() > 8) {
				this.addCount++;
			}
			this.speed = 5 + this.addCount;
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int createBool() {
		return (int) Math.ceil((Math.random() * 10));
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public BufferedImage getShowImage() {
		return showImage;
	}

	public void setShowImage(BufferedImage showImage) {
		this.showImage = showImage;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// û�ж�map���в������
		if (this.map.get("status").equals("start")) {
			this.restart();
		} else if (this.map.get("status").equals("reset")) {
			this.setX(0);
		}
		// ��������ڶ����ܵ��յ����������
		if (this.map.get("status").equals("finish")) {
			this.isRunning = false;
		}
	}

	// ���¿�ʼ��һ�α���
	public void restart() {
		if (this.isRunning == false) {
			System.out.println("���¿�ʼ");
			this.isRunning = true;
			this.setX(0);
			this.speed = 5;
			this.addCount = 5;
			if (!this.t.isAlive()) {
				this.t = new Thread(this);
				this.t.start();
			}
		}
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public Thread getT() {
		return t;
	}

	public void setT(Thread t) {
		this.t = t;
	}

	public int getAddCount() {
		return addCount;
	}

	public void setAddCount(int addCount) {
		this.addCount = addCount;
	}

}
