package com.matt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Surface extends JFrame implements MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;
	JPanel panel;
	Color backgroundColor = new Color(200, 200, 200);
	Color menuColor = new Color(100, 100, 100, 60);
	
	public Surface(int width, int height) {
		super("");
		this.setSize(width, height);
		//this.setUndecorated(true);
		this.setTitle("Visual Metronome");
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override public void paintComponent(Graphics g) {
				g.setColor(backgroundColor);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				
				g.setColor(Color.black); //Draw the Border
				g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
				
				for (Line line: MetronomeApp.lines) {
					line.draw(g);  //Draw all the lines
				}
				
				MetronomeApp.metronome.draw(g);
			}
		};
		this.add(panel);
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
	}
	
	@Override public void repaint() {this.panel.repaint();}
	
	//Mouse Listener Methods
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override 
	public void mousePressed(MouseEvent e) {
		MetronomeApp.metronome.hit();
	}
	
	//Key Listener Methods
	@Override public void keyReleased(KeyEvent e) {}
	@Override public void keyTyped(KeyEvent e) {}
	@Override public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:  //Quit the Metronome
			System.out.println("Exiting Metronome..");
			System.exit(0);
			break;
		case KeyEvent.VK_R: //Reset button
			MetronomeApp.mode = 'w';
			MetronomeApp.reset();
			break;
		case KeyEvent.VK_SPACE:  //Go to Note Mode, then pause/continue scroll
			switch (MetronomeApp.mode) {
			case 'n':
				//System.out.println("Going to Pause mode from Noting");
				MetronomeApp.mode = 'p';
				break;
			case 's':
			case 'p':
				//System.out.println("Going to Noting mode");
				MetronomeApp.mode = 'n';
				new Thread () {
					@Override public void run() {
						MetronomeApp.metronome.startBeating();
					}
				}.start();
				break;
			}
			break;
		}
	}
}
