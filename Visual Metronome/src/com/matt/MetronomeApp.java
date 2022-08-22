package com.matt;

import java.awt.Color;
import java.util.ArrayList;

public class MetronomeApp {
	//Modes: w=Waiting, s=Setting pace, n=Note mode, p=Paused
	public static char mode = 'w';
	public static double lineSpeed = 2.7;
	public static Metronome metronome = new Metronome();
	public static ArrayList<Line> lines = new ArrayList<Line>();
	public static Surface surface = new Surface(1000, 400);
	
	private static boolean newLine = false;
	private static int newLineXPos = 0;
	private static Color newLineColor = null;
	private static boolean lineRemoval = false;
	private static Line lineToRemove = null;
	
	public static void main(String[] args) {
		surface.setVisible(true);
		
		while (true) {
			
			if (MetronomeApp.mode == 's' || MetronomeApp.mode == 'n') {
				if (newLine) {
					MetronomeApp.lines.add(new Line(newLineXPos, newLineColor));
					newLine = false;
				}
				if (lineRemoval) {
					MetronomeApp.lines.remove(lineToRemove);
					lineRemoval = false;
				}
				for (Line line: MetronomeApp.lines) {
					line.inc();
				}
				surface.repaint();
			}
			
			try {Thread.sleep(17);} catch (InterruptedException e) {} //Wait to time the fps at 60
		}
	}
	
	public static void flagNewLine(int x_pos, Color color) {
		if (color != null) {
			newLine = true;
			newLineXPos = x_pos;
			newLineColor = color;
		}
	}
	
	public static void flagLineRemoval(Line line) {
		if (line != null) {
			lineRemoval = true;
			lineToRemove = line;
		}
	}
	
	public static void reset() {
		MetronomeApp.lines = new ArrayList<Line>();
		metronome = new Metronome();
		newLine = false;
		lineRemoval = false;
		surface.repaint();
	}
}
