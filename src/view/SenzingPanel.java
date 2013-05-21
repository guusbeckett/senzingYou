package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
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
		
		//This background is purelly for the test purpuse.
		g2.drawImage(Toolkit.getDefaultToolkit().getImage("./images/underwater/background.png"), 0, 0, null);
		
		if (cameraData != null)
		{
			g2.drawImage(cameraData.getImage(), null, 0, 0);
		}
				
		for (Entity entity : game.getEntities())
		{
			if(entity.getImage() != null){
				g2.drawImage(entity.getImage(), (int)entity.getBounds().getX(), (int)entity.getBounds().getY(), (int)entity.getBounds().getWidth(), (int)entity.getBounds().getHeight(), null);
			}
			else{
				g2.fill(AffineTransform.getRotateInstance(entity.getRotation(), entity.getRotationPoint().getX() + entity.getBounds().getX(), entity.getRotationPoint().getY() + entity.getBounds().getY()).createTransformedShape(entity.getBounds()));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
}
