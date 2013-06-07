package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.Camera;
import model.Game;
import model.MediaProvider;
import model.Song;
import model.User;
import model.entities.Entity;
import model.entities.HostileEntity;
import model.levels.Level;

public class SenzingPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Game game;
	private Image rewardImage, losingImage;
	private int loaderIndex;
	private boolean loaderReversed = false;
	private HighscoreView highscoreView;

	public SenzingPanel(Game game)
	{
		super();
		this.game = game;
		setBackground(Color.BLACK);
		new Timer(1000 / 30, this).start();
		rewardImage = MediaProvider.getInstance().getImage("reward.png");
		losingImage = MediaProvider.getInstance().getImage("losingpoint.png");
		highscoreView = new HighscoreView(game.getHighscore());
	}
	
	private void drawImageInCenter(Graphics2D g2, Image image, float alpha)
	{
		double width = image.getWidth(null);
		double height = image.getHeight(null);

		AffineTransform ax = new AffineTransform();

		ax.translate(Camera.VIEW_WIDTH / 2 - 320 / 2, Camera.VIEW_HEIGHT / 2 - 240 / 2);
		ax.scale(320 / width, 240 / height);

		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g2.setComposite(ac);
		g2.drawImage(image, ax, null);
	}

	private void drawLoader(Graphics2D g2)
	{
		double loaderWidth = 250;
		double loaderHeight = 20;

		Rectangle2D loader = new Rectangle2D.Double(Camera.VIEW_WIDTH / 2 - loaderWidth / 2, Camera.VIEW_HEIGHT / 2 - loaderHeight / 2, loaderWidth, loaderHeight);

		g2.setColor(Color.LIGHT_GRAY);
		g2.fill(loader);

		g2.setColor(Color.DARK_GRAY);
		g2.draw(loader);
		g2.fill(new Rectangle2D.Double(loader.getX() + loaderIndex, loader.getY(), 50, loader.getHeight()));

		if (loaderReversed)
		{
			loaderIndex -= 10;
		} else
		{
			loaderIndex += 10;
		}

		if ((loaderIndex + 50) >= loaderWidth)
		{
			loaderReversed = true;
		}

		else if (loaderIndex <= 0)
		{
			loaderReversed = false;
		}
	}

	private void drawEntities(Graphics2D g2, int step)
	{
		Text hitText = new Text(Color.BLUE, 250, true);
		
		for (Entity entity : game.getEntities())
		{
			AffineTransform ax = new AffineTransform();

			ax.rotate(entity.getRotation(), entity.getRotationPoint().getX() + entity.getPosition().getX(), entity.getRotationPoint().getY() + entity.getPosition().getY());

			if (entity.getImage() != null)
			{
				if ((step == 0 && !(entity instanceof HostileEntity)) || (step == 1 && (entity instanceof HostileEntity) && ((HostileEntity) entity).isAlive()))
				{
					ax.translate(entity.getPosition().getX(), entity.getPosition().getY());
					if (entity.isMirrored())
						ax.translate(entity.getDimensions().getWidth(), 0);
					ax.scale(entity.getDimensions().getWidth() / entity.getImage().getWidth(null) * ((entity.isMirrored()) ? -1 : 1), entity.getDimensions().getHeight() / entity.getImage().getHeight(null));
					g2.drawImage(entity.getImage(), ax, null);
				}

				else if (step == 1 && entity instanceof HostileEntity)
				{
					HostileEntity hostile = (HostileEntity) entity;

					if (!hostile.isAlive())
					{
						Image image = (hostile.getReward() <= 0) ? losingImage : rewardImage;

						ax.translate(hostile.getDeadLocation().getX(), hostile.getDeadLocation().getY());
						ax.scale(entity.getDimensions().getWidth() / image.getWidth(null), entity.getDimensions().getHeight() / image.getHeight(null));
						g2.drawImage(image, ax, null);
						
						ax.translate(0, (image.getWidth(null) / 1.5));
						hitText.draw(g2, ax, hostile.getReward() + "");
					}
				}
			}
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		double _w = getWidth();
		double _h = getHeight();
		double _x = Camera.VIEW_WIDTH;
		double _y = Camera.VIEW_HEIGHT;
		double _s = _h / _y;
		double _b = (_w / _s - _x) / 2;
		
		BufferedImage screenImage = new BufferedImage((int)_x, (int)_y, BufferedImage.TYPE_INT_ARGB);
		
		if(game.isMakeScreenshot()){
			g2 = screenImage.createGraphics();
		}
		
		//Don't do while making screenshot
		if(!game.isMakeScreenshot()){
			g2.scale(_s, _s);
			g2.translate(_b, 0);
		}
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		BufferedImage[] images = game.getCamera().getImageBackgroundAndForeground();

		// Draw background
		Level level = game.getLevel();

		if (level != null)
		{
			g2.drawImage(level.getBackground(), 0, 0, null);
		}

		// Draw camera background image
		g2.drawImage(images[0], null, 0, 0);

		// Draw the background entities
		drawEntities(g2, 0);

		// Draw camera foreground image
		g2.drawImage(images[1], null, 0, 0);

		// Draw description of level
		if (level == null)
		{
			if (!game.isLoading())
			{
//				drawImageInCenter(g2, MediaProvider.getInstance().getImage("usbConnect.png"), 1f);
				highscoreView.draw(g2);
			}
			else
				drawLoader(g2);
		}

		// Draw the foreground entities
		drawEntities(g2, 1);

		//Don't draw while making a screenshot!
		if(!game.isMakeScreenshot()){
			
			// Draw all the scores
			if (!game.getCamera().getUsers().isEmpty())
			{
				Color[] colors = new Color[] { Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.WHITE, Color.YELLOW, Color.LIGHT_GRAY };
	
				// Now do nothing with X it just print on the head position
				for (User u : game.getCamera().getUsers())
				{
					if (u.isVisible())
					{
						Text scoreText = new Text(colors[(u.getId() - 1) % colors.length], 45, false);
						scoreText.draw(g2, new Point2D.Double(u.getHead().getX(), 50), u.getScore() + "");
					}
				}
			}
	
			// Draw the countdown
			Song song = game.getSong();
	
			if (song != null)
			{
				int time = (int) song.getTime();
				int length = (int) song.getLength();
				
				Text countdownText = new Text(Color.ORANGE, 25);
				countdownText.draw(g2, new Point2D.Double(48, 25), String.format("%02d:%02d / %02d:%02d - %s - %s", time / 60, time % 60, length / 60, length % 60, song.getArtist(), song.getTitle()));
			}
	
			// Draw sideboxes
			g2.setColor(Color.BLACK);
			g2.fill(new Rectangle2D.Double(-_b, 0, _b + 40, _y));
			g2.fill(new Rectangle2D.Double(_x - 20, 0, _b + 20, _y));
			
			
			if (level != null)
			{
				if (level.isDescriptionImageVisible())
				{
					drawImageInCenter(g2, level.getDescriptionImage(), level.getDescriptionImageOpacity());
				}
			}
		}
		
		//Making the screenshot
		if(game.isMakeScreenshot()){
			g2.dispose();
			game.makeScreenshot(screenImage);
			game.setMakeScreenshot(false);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
}
