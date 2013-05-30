package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.Camera;
import model.Game;
import model.User;
import model.entities.Entity;
import control.Song;

public class SenzingPanel extends JPanel implements ActionListener
{
	private static final  long serialVersionUID = 1L;
	private Game game;
	public SenzingPanel(Game game)
	{
		super();
		this.game = game;
		setBackground(Color.BLACK);
		new Timer(1000/30, this).start();
	}
	
	
	private void drawText(Graphics2D g2, String text, Color color, int size, Point2D p2)
	{
		Font font = new Font("Arial", Font.BOLD, size);
		FontRenderContext frc = g2.getFontRenderContext();
		GlyphVector gv = font.createGlyphVector(frc, text);
		Shape outline = gv.getOutline((int)p2.getX(), (int)p2.getY());
		g2.setColor(color);
		g2.fill(outline);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		g2.draw(outline);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		double scaleFactor = (double)getHeight() / (double)Camera.VIEW_HEIGHT;
		g2.translate((getWidth() - (Camera.VIEW_WIDTH * scaleFactor)) / 2, 0);
		g2.scale(scaleFactor, scaleFactor);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.drawImage(game.getBackground(), 0, 0, null);
		g2.drawImage(game.getCamera().getImage(), null, 0, 0);

		for (Entity entity : game.getEntities())
		{
			AffineTransform transform = AffineTransform.getRotateInstance(entity.getRotation(), entity.getRotationPoint().getX() + entity.getPosition().getX(), entity.getRotationPoint().getY() + entity.getPosition().getY());
			
			if (entity.getImage() != null)
			{
				transform.translate(entity.getPosition().getX(), entity.getPosition().getY());
				transform.scale(entity.getDimensions().getWidth() / entity.getImage().getWidth(null), entity.getDimensions().getHeight() / entity.getImage().getHeight(null));
				g2.drawImage(entity.getImage(), transform, null);
			}
		}
		
		Color[] colors = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.WHITE, Color.YELLOW, Color.LIGHT_GRAY};
		for(User u: game.getCamera().getUsers())
		{
			if (u.isVisible())
				drawText(g2, ""+u.getScore(), colors[(u.getId() - 1)%colors.length], 70, new Point2D.Double(u.getHead().getX(), 70));
		}
		
		Song song = game.getSong();
		
		if (song != null)
		{
			int time = (int)song.getTime();
			int length = (int)song.getLength();
			
			drawText(g2, String.format("%02d:%02d / %02d:%02d", time / 60, time % 60, length / 60, length % 60), Color.ORANGE, 25, new Point2D.Double(8, 25));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
}
