package view.ground;

import java.awt.Graphics2D;
import java.awt.Toolkit;

public class UnderwaterGround implements GroundRenderer
{
	@Override
	public void draw(Graphics2D g2)
	{
		g2.drawImage(Toolkit.getDefaultToolkit().getImage("./images/underwater/ground.jpg"), null, null);
	}
}
