package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.Camera;
import model.Game;
import model.User;
import model.levels.Level;

public class BeamerPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Game game;

	public BeamerPanel(Game game)
	{
		super();
		this.game = game;
		setBackground(Color.BLACK);
		new Timer(1000 / 30, this).start();
	}
	
	private void drawText(Graphics2D g2, String text, Color color, int size, Point2D p2)
	{
		AffineTransform transform = new AffineTransform();
		transform.translate(p2.getX(), p2.getY());
		drawText(g2, text, color, size, transform);
	}
	
	private void drawText(Graphics2D g2, String text, Color color, int size, AffineTransform transform)
	{
		Font font = new Font("Arial", Font.BOLD, size);
		FontRenderContext frc = g2.getFontRenderContext();
		GlyphVector gv = font.createGlyphVector(frc, text);
		Shape outline = gv.getOutline(0, 0);
		if(transform != null){
			outline = transform.createTransformedShape(outline);
		}
		g2.setColor(color);
		g2.fill(outline);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		g2.draw(outline);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		double scaleFactor = (double) getHeight() / (double) Camera.VIEW_HEIGHT;

		g2.translate((getWidth() - (Camera.VIEW_WIDTH * scaleFactor)) / 2, 0);
		g2.scale(scaleFactor, scaleFactor);

		Level level = game.getLevel();
		
		if (level != null)
		{
			level.getGroundRenderer().draw(g2);
		}
		
		if(!game.getCamera().getUsers().isEmpty()){
			Color[] colors = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.WHITE, Color.YELLOW, Color.LIGHT_GRAY};
			ArrayList<User> copyUsers = new ArrayList<User>();
			copyUsers.addAll(game.getCamera().getUsers());
			Collections.sort(copyUsers);
			int x = 300;
			int scoreWidth = (game.getCamera().VIEW_WIDTH - x) / copyUsers.size();
	
			for(User u: copyUsers)
			{
				if (u.isVisible()){
					drawText(g2, ""+u.getScore(), colors[(u.getId() - 1)%colors.length], 25, new Point2D.Double(u.getHead().getX(), 25));
				}
				x+=scoreWidth;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		repaint();
	}
}
