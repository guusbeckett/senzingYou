package control.levels;

import java.awt.Toolkit;

import model.Game;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.entities.rainforest.Banana;
import model.entities.rainforest.Bird;
import model.entities.rainforest.Snake;
import model.entities.rainforest.Tree;
import view.ground.RainforestGround;

public class RainforestLevel extends DodgeLevel
{

	public RainforestLevel(Game game)
	{
		super(game);
		game.setBackground(Toolkit.getDefaultToolkit().getImage(
				"./images/rainforest/background.png"));
		game.setGroundRenderer(new RainforestGround());
		game.getEntities().add(new Tree());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Entity getRandomEntity()
	{
		// TODO Auto-generated method stub
		if(Math.random() > 0.5){
			return new Bird();
		}
		else{
			return new Snake();
		}
	}

	@Override
	public HostileEntity getRandomHostileEntity()
	{
		// TODO Auto-generated method stub
		return new Banana(getGame().getCamera().getUsers());
	}
}

