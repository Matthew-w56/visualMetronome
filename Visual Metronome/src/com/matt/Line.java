package com.matt;

import java.awt.Color;
import java.awt.Graphics;

public class Line {
	private double x_pos = 10;
	private Color color = new Color(0, 0, 0);
	
	public Line(double x_pos, Color color) {
		this.x_pos = x_pos;
		this.color = color;
	}
	
	public void inc() {
		this.x_pos -= MetronomeApp.lineSpeed;
		if (this.x_pos < 0) {
			MetronomeApp.flagLineRemoval(this);
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.drawRect((int)this.x_pos, 0, 2, 401);
	}
}
