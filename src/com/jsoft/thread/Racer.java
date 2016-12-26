package com.jsoft.thread;

import java.awt.image.BufferedImage;

/**
 * 
 * @author ACE 代表每一个运动员
 */
public class Racer implements Runnable {

	private int type;

	private int x;

	private int y;

	private int speed;

	private BufferedImage showImage = null;

	private int showIndex = 0;

	private Thread t = null;

	private boolean isRunning = true;

	private boolean isFinish = false;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Racer(int x, int y, int type, int speed) {
		this.setX(x);

		this.setY(y);

		this.type = type;

		this.speed = speed;

		this.t = new Thread(this);

		this.t.start();
	}

	public void run() {
		while (this.isRunning) {
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.speed = createSpeed();
			if (this.getX() > 810) {
				this.isRunning = false;
				this.isFinish = true;
			}

			this.setX(this.getX() + this.speed);

			if (this.type == 1) {
				this.setShowImage(StaticValue.maList.get(this.showIndex));
			} else if (this.type == 2) {
				this.setShowImage(StaticValue.maoList.get(this.showIndex));
			} else if (this.type == 3) {
				this.setShowImage(StaticValue.baoList.get(this.showIndex));
			} else if (this.type == 4) {
				this.setShowImage(StaticValue.xiangList.get(this.showIndex));
			}
			this.showIndex++;
			if (this.showIndex == 8) {
				this.showIndex = 0;
			}
		}
	}

	public int createSleep() {
		return (int) Math.ceil((Math.random() * 120));
	}

	public int createSpeed() {
		return (int) Math.ceil((Math.random() * 15));
	}

	public int createMiniSpeed() {
		return (int) Math.ceil((Math.random() * 4));
	}

	public void restart() {
		this.setX(0);
		this.setX(1);
		this.isRunning = true;
		this.speed = createSpeed();
		this.isFinish = false;
		if (!this.t.isAlive()) {
			this.t = new Thread(this);
			this.t.start();
		}
	}

	public void reset() {
		this.setX(0);
		this.setX(1);
		if (this.type == 1) {
			this.setShowImage(StaticValue.maList.get(1));
		} else if (this.type == 2) {
			this.setShowImage(StaticValue.maoList.get(7));
		} else if (this.type == 3) {
			this.setShowImage(StaticValue.baoList.get(2));
		} else if (this.type == 4) {
			this.setShowImage(StaticValue.xiangList.get(2));
		}
		this.isRunning = false;
	}

	public BufferedImage getShowImage() {
		return showImage;
	}

	public void setShowImage(BufferedImage showImage) {
		this.showImage = showImage;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}
}
