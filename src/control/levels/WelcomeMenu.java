package control.levels;

import java.awt.Toolkit;
import java.util.Collections;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.entities.menu.welcome.ButtonCave;
import model.entities.menu.welcome.ButtonDesert;
import model.entities.menu.welcome.ButtonRainforest;
import model.entities.menu.welcome.ButtonSky;
import model.entities.menu.welcome.ButtonWater;

public class WelcomeMenu extends MenuLevel
{
	public WelcomeMenu(Game game)
	{
		super(game);
		game.setBackground(Toolkit.getDefaultToolkit().getImage("./images/menu/welcome/background.png"));
		game.setGround(Toolkit.getDefaultToolkit().getImage("./images/underwater/ground.jpg"));
		game.getEntities().add(new ButtonWater());
		game.getEntities().add(new ButtonCave());
		game.getEntities().add(new ButtonDesert());
		game.getEntities().add(new ButtonRainforest());
		game.getEntities().add(new ButtonSky());
		Collections.reverse(game.getEntities());	//So that the hand come first!
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
