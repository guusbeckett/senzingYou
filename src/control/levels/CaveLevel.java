package control.levels;

import java.awt.Toolkit;

import model.Game;
import model.entities.cave.Droplet;
import model.entities.cave.Rock;

public class CaveLevel extends DodgeLevel
{

	public CaveLevel(Game game)
	{
		super(game);
		game.setBackground(Toolkit.getDefaultToolkit().getImage("./images/cave/background.png"));
		// TODO Auto-generated constructor stub
	}

	public void update(double time)
	{
		super.update(time);
		if((int)(Math.random() * time) == 1){
			game.getEntities().add(new Droplet());
		}
		if((int)(Math.random() * (time * 0.5)) == 1){
			game.getEntities().add(new Rock(game.getCamera().getUsers()));
		}
		
	}
}
