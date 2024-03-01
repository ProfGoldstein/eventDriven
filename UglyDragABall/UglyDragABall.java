
// going to be lazy about imports in these examples...
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A program to demonstrate a simple dragging operation.
 * 
 * @author Jim Teresco
 * @version Spring 2022
 */

public class UglyDragABall extends MouseAdapter implements Runnable {

	// ball size
	public static final int SIZE = 50;

	// current coordinates of the upper left corner of the circle
	private Point upperLeft = new Point(50, 50);

	// are we currently dragging?
	private boolean dragging = false;

	private JPanel panel;

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
		JFrame frame = new JFrame("UglyDragABall");
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

				// redraw our circle at its current position
				g.fillOval(upperLeft.x, upperLeft.y, SIZE, SIZE);
			}
		};
		frame.add(panel);
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);

		// display the window we've created
		frame.pack();
		frame.setVisible(true);
	}

	// a method to determine if the given point is within the
	// circle as currently drawn
	protected boolean circleContains(Point p) {

		Point circleCenter = new Point(upperLeft.x + SIZE / 2, upperLeft.y + SIZE / 2);
		return circleCenter.distance(p) <= SIZE / 2;
	}

	@Override
	public void mousePressed(MouseEvent e) {

		// if we pressed within the circle, set up for dragging
		dragging = circleContains(e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		// if we are dragging (mouse press was in the circle), update
		// its position to be where the mouse was just released
		if (dragging) {
			upperLeft = e.getPoint();
			panel.repaint();
		}

		// think: why do we not need to set dragging to false here?
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		// if we are dragging (mouse press was in the circle), update
		// its position to be where the mouse is now
		if (dragging) {
			upperLeft = e.getPoint();
			panel.repaint();
		}
	}

	public static void main(String args[]) {

		// The main method is responsible for creating a thread (more
		// about those later) that will construct and show the graphical
		// user interface.
		javax.swing.SwingUtilities.invokeLater(new UglyDragABall());
	}
}
