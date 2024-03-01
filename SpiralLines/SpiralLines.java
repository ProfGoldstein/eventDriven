
// going to be lazy about imports in these examples...
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * This program draws a "Spiral of Lines". When the mouse is pressed
 * then dragged, a series of lines are drawn from the press point to
 * the current location.
 * 
 * @author Jim Teresco
 * @version Spring 2022
 */

public class SpiralLines extends MouseAdapter implements Runnable {

	// a list of pairs of points to keep track of the start and end coordinates
	// where we need to draw lines
	private ArrayList<Point[]> lines = new ArrayList<>();

	// press point for the current spiral
	private Point pressPoint;

	private JPanel panel;

	// this method is called by the paintComponent method of
	// the anonymous extension of JPanel, to keep that method
	// from getting too long
	protected void redraw(Graphics g) {

		// draw all of the lines in the list
		for (Point[] p : lines) {
			g.drawLine(p[0].x, p[0].y, p[1].x, p[1].y);
		}
	}

	/**
	 * The run method to set up the graphical user interface
	 */
	@Override
	public void run() {

		// set up the GUI "look and feel" which should match
		// the OS on which we are running
		JFrame.setDefaultLookAndFeelDecorated(true);

		// create a JFrame in which we will build our very
		// tiny GUI, and give the window a name
		JFrame frame = new JFrame("SpiralLines");
		frame.setPreferredSize(new Dimension(500, 500));

		// tell the JFrame that when someone closes the
		// window, the application should terminate
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// JPanel with a paintComponent method
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {

				// first, we should call the paintComponent method we are
				// overriding in JPanel
				super.paintComponent(g);

				// redraw our graphics items
				redraw(g);
			}
		};
		frame.add(panel);
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);

		// display the window we've created
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {

		pressPoint = e.getPoint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		Point p[] = new Point[2];
		p[0] = pressPoint;
		p[1] = e.getPoint();
		lines.add(p);
		panel.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {

		lines.clear();
		panel.repaint();
	}

	public static void main(String args[]) {

		// The main method is responsible for creating a thread (more
		// about those later) that will construct and show the graphical
		// user interface.
		javax.swing.SwingUtilities.invokeLater(new SpiralLines());
	}
}
