package control.levels;

import java.awt.Toolkit;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.entities.menu.welcome.ButtonWater;

public class WelcomeMenu extends MenuLevel
{

	public WelcomeMenu(Game game)
	{
		super(game);
		game.setBackground(Toolkit.getDefaultToolkit().getImage("./images/menu/background.png"));
		game.setGround(Toolkit.getDefaultToolkit().getImage("./images/underwater/ground.jpg"));
		game.getEntities().add(new ButtonWater());
	}

	@Override
	public Entity getRandomEntity()
	{
		return null;
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		return null;
	}
	
	public void update(double time)
	{
		super.update(time);		
	}

}
