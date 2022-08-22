package com.matt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Metronome {
	private long startTime = 0L;
	private long lastClickTime = 0L;
	
	private int totalSetLines = 0;
	private boolean currentlyBeating = false;
	
	public Color black = new Color(0, 0, 0);
	public Color red = new Color(200, 0, 0);
	Font font = new Font("Serif", Font.PLAIN, 12);
	
	private long pace = 0L;
	
	public Metronome() {}
	
	public void hit() { //User has clicked
		switch(MetronomeApp.mode) {
		case 'n':
			MetronomeApp.flagNewLine(1001, red);
			break;
		case 'w':
			MetronomeApp.mode = 's';
			this.startTime = System.currentTimeMillis();
		case 's':
			MetronomeApp.flagNewLine(1001, black);
			lastClickTime = System.currentTimeMillis();
			if (totalSetLines != 0) {this.pace = (lastClickTime - startTime) / totalSetLines;}
			this.totalSetLines++;
			break;
		}
	}
	
	public void startBeating() {
		//if (totalSetLines != 0) {this.pace = (lastClickTime - this.startTime) / this.totalSetLines;}
		//System.out.println("Started Beating the Metronome");
		if (!currentlyBeating) {
			currentlyBeating = true;
			try {Thread.sleep(this.pace - (System.currentTimeMillis() - lastClickTime));} catch (InterruptedException e) {}
			catch (IllegalArgumentException e) {
				//Wait until the next natural beat
			}
			
			while (MetronomeApp.mode == 'n') {
				MetronomeApp.flagNewLine(1001, black);
				try {Thread.sleep(this.pace + (long).5);} catch (InterruptedException e) {}
			}
		}
		currentlyBeating = false;
		System.out.println("Exiting the Metronome beats");
	}
	
	public void draw(Graphics g) {
		g.setColor(black);
		if (MetronomeApp.mode != 'w' && totalSetLines > 1) {
			try {g.drawString("Current BPM: " + (int)(60000f / this.pace), 15, 20);} catch (ArithmeticException e) {
				g.drawString("Current BPM: 0", 15, 20);
			}
		} else {
			g.drawString("Current BPM: 0", 15, 20);
		}
	}
}
