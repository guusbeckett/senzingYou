package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.CameraData;
import model.Game;
import model.User;
import model.entities.Entity;

public class SenzingPanel extends JPanel implements ActionListener
{
	private static final  long serialVersionUID = 1L;
	private Game game;

	public SenzingPanel(Game game)
	{
		super();
		this.game = game;
		new Timer(1000/30, this).start();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		CameraData cameraData = game.getCameraData();
		
		if (cameraData != null)
		{
			g2.drawImage(cameraData.getImage(), null, 0, 0);
			/*for(User user: game.getCameraData().getUsers()){
				if(user.getHandExact() != null){
					Shape check = new Ellipse2D.Float(50, 70, 20, 20);
					Shape exact = new Ellipse2D.Float((int)user.getHandExact().getX(), (int)user.getHandExact().getY(), 20, 20);
					
					g2.setColor(Color.RED);
					g2.fill(exact);
					
					g2.setColor(Color.PINK);
					g2.fill(check);
				}	
				if(user.getLeftHand() != null &&
						user.getRightHand() != null){
					
					Shape check = new Ellipse2D.Float(50, 50, 20, 20);
					
					
					
					Shape left = new Ellipse2D.Float((int)user.getLeftHand().getX(), (int)user.getLeftHand().getY(), 20, 20);
					Shape right = new Ellipse2D.Float((int)user.getRightHand().getX(), (int)user.getRightHand().getY(), 20, 20);
					
					g2.setColor(Color.BLUE);
					g2.fill(left);
					
					g2.setColor(Color.GREEN);
					g2.fill(right);
					
					
					
					g2.setColor(Color.YELLOW);
					g2.fill(check);
				}
				g2.setColor(Color.BLACK);
				
				
				
			}
			*/
		}
		
		for (Entity entity : game.getEntities())
		{
			g2.fill(AffineTransform.getRotateInstance(entity.getRotation(), entity.getRotationPoint().getX() + entity.getBounds().getX(), entity.getRotationPoint().getY() + entity.getBounds().getY()).createTransformedShape(entity.getBounds()));
		}
		
		g2.drawString("Score: " + game.getScore(), 200, 100);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
}
