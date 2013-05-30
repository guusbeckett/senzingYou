package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
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
import model.entities.Entity;
import model.entities.HostileEntity;
import control.Song;

public class SenzingPanel extends JPanel implements ActionListener
{
	private static final  long serialVersionUID = 1L;
	private Game game;
	private Image rewardImage, losingImage;
	
	public SenzingPanel(Game game)
	{
		super();
		this.game = game;
		setBackground(Color.BLACK);
		new Timer(1000/30, this).start();
		rewardImage = Toolkit.getDefaultToolkit().getImage("./images/reward.png");
		losingImage = Toolkit.getDefaultToolkit().getImage("./images/losingpoint.png");
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
				//Only if hostile we need to show them the bonus'
				//Entity
				if(!(entity instanceof HostileEntity)){
					transform.translate(entity.getPosition().getX(), entity.getPosition().getY());
					transform.scale(entity.getDimensions().getWidth() / entity.getImage().getWidth(null), entity.getDimensions().getHeight() / entity.getImage().getHeight(null));
					g2.drawImage(entity.getImage(), transform, null);
				}
				else if (entity instanceof HostileEntity)
				{
					HostileEntity hostile = (HostileEntity) entity;
					if(hostile.isAlive()){
						transform.translate(entity.getPosition().getX(), entity.getPosition().getY());
						transform.scale(entity.getDimensions().getWidth() / entity.getImage().getWidth(null), entity.getDimensions().getHeight() / entity.getImage().getHeight(null));
						g2.drawImage(entity.getImage(), transform, null);
					}
					//Anders is hij dood!
					else{
						transform.rotate(0);
						transform.translate(hostile.getDeadLocation().getX(), hostile.getDeadLocation().getY());
						
						if(hostile.getReward() <= 0){
							transform.scale(entity.getDimensions().getWidth() / losingImage.getWidth(null), entity.getDimensions().getHeight() / losingImage.getHeight(null));
							g2.drawImage(losingImage, transform, null);
							transform.translate(0, (rewardImage.getWidth(null) / 1.5));
							drawText(g2, ""+hostile.getReward(), Color.BLUE, 250, transform);
						}
						else{
							transform.scale(entity.getDimensions().getWidth() / rewardImage.getWidth(null), entity.getDimensions().getHeight() / rewardImage.getHeight(null));
							g2.drawImage(rewardImage, transform, null);
							transform.translate(0, (rewardImage.getWidth(null) / 1.5));
							drawText(g2, ""+hostile.getReward(), Color.GREEN, 250, transform);
						}
					}
					
				}
				
			}
		}
		
		if(!game.getCamera().getUsers().isEmpty()){
			Color[] colors = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.WHITE, Color.YELLOW, Color.LIGHT_GRAY};
			ArrayList<User> copyUsers = new ArrayList<User>();
			copyUsers.addAll(game.getCamera().getUsers());
			Collections.sort(copyUsers);
			int x = 30;
			int scoreWidth = (game.getCamera().VIEW_WIDTH - x) / copyUsers.size();
	
			for(User u: copyUsers)
			{
				if (u.isVisible()){
					drawText(g2, ""+u.getScore(), colors[(u.getId() - 1)%colors.length], 25, new Point2D.Double(x, 25));
				}
				x+=scoreWidth;
			}
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
