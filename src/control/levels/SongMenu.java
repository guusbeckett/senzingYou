package control.levels;

import java.awt.Toolkit;
import java.util.Collections;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;

public class SongMenu extends MenuLevel
{
	private Level level;
	public SongMenu(Game game, Level level)
	{
		super(game);
		this.level = level;
		game.setBackground(Toolkit.getDefaultToolkit().getImage("./images/menu/song/background.png"));
		game.setGround(Toolkit.getDefaultToolkit().getImage("./images/underwater/ground.jpg"));
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
