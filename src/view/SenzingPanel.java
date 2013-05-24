package view;

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

import javax.swing.JPanel;
import javax.swing.Timer;

import model.Camera;
import model.Game;
import model.entities.Entity;

public class SenzingPanel extends JPanel implements ActionListener
{
	private static final  long serialVersionUID = 1L;
	private Game game;
	private Font font = new Font("Serif", Font.BOLD, 144);

	public SenzingPanel(Game game)
	{
		super();
		this.game = game;
		setBackground(Color.BLACK);
		new Timer(1000/30, this).start();
	}
	
	private void drawText(Graphics2D g2, String text)
	{
		FontRenderContext frc = g2.getFontRenderContext();
		GlyphVector gv = font.createGlyphVector(frc, text);
		g2.draw(gv.getOutline(100, 200));
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		double scaleFactor = (double)getHeight() / (double)Camera.VIEW_HEIGHT;
		
		g2.translate((getWidth() - (Camera.VIEW_WIDTH * scaleFactor)) / 2, 0);
		g2.scale(scaleFactor, scaleFactor);
		
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
		
		drawText(g2, "Test");
		
		/*	g2.setColor(Color.RED);
		for(User u: game.getCamera().getUsers()){

			if(u.getName() != null){
				g2.drawString(u.getName()+": "+u.getScore(), (int)u.getHead().getX(), (int)u.getHead().getY());
			}
			else{
				g2.drawString("NoBody: "+u.getScore(), (int)u.getHead().getX(), (int)u.getHead().getY());
			}
		}
		g2.setColor(Color.BLACK);*/
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
}
