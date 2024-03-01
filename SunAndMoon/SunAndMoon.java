
// going to be lazy about imports in these examples...
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A program to animate the rising and setting of the sun and moon.
 * But controlled by mouse moving and dragging.
 * 
 * @author Jim Teresco
 * @version Spring 2022
 */

public class SunAndMoon extends MouseAdapter implements Runnable {

	// sun/moon size
	public static final int SIZE = 50;

	// current y coordinate location of the "orb" (sun or moon)
	private int orbPoint;

	// is it day or night?
	private boolean isDay = true;

	private JPanel panel;

	// this method is called by the paintComponent method of
	// the anonymous extension of JPanel, to keep that method
	// from getting too long
	protected void redraw(Graphics g, int width, int height) {

		// draw the sky
		if (isDay) {
			g.setColor(Color.BLUE);
		} else {
			g.setColor(Color.BLACK);
		}
		g.fillRect(0, 0, width, height);

		// draw the sun/moon
		if (isDay) {
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.WHITE);
		}
		g.fillOval(SIZE, orbPoint, SIZE, SIZE);

		// draw the field, bottom third of screen
		// this is intentionally drawn last so it's on
		// top of the other items, making the sun/moon
		// "set" behind the horizon
		if (isDay) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.GRAY);
		}
		g.fillRect(0, 2 * height / 3, width, height);
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
		JFrame frame = new JFrame("SunAndMoon");
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
				redraw(g, getWidth(), getHeight());
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

		isDay = false;
		panel.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		isDay = true;
		panel.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		orbPoint += 1;
		panel.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		orbPoint -= 1;
		panel.repaint();
	}

	public static void main(String args[]) {

		// The main method is responsible for creating a thread (more
		// about those later) that will construct and show the graphical
		// user interface.
		javax.swing.SwingUtilities.invokeLater(new SunAndMoon());
	}
}
