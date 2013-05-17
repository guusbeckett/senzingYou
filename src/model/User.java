package model;

import java.awt.geom.Point2D;

import org.OpenNI.Point3D;
import org.OpenNI.SceneMetaData;
import org.OpenNI.UserGenerator;

public class User
{
	private int id, score;
	private UserGenerator userGenerator;
	private Point2D head, neck, leftShoulder, rightShoulder, torso, 
	leftElbow, rightElbow, leftHand, rightHand, leftHip, rightHip, 
	leftKnee, rightKnee, leftFoot, rightFoot, midpoint, handExact;
	
	private Point3D rightHandWorld;
	
	public User(int id, UserGenerator userGenerator)
	{
		this.id = id;
		this.userGenerator = userGenerator;
	}

	public int getId()
	{
		return id;
	}
	
	public SceneMetaData getUserPixels()
	{
		return userGenerator.getUserPixels(id);
	}
	
	public Point2D getMidpoint(){
		return midpoint;
	}
	
	public Point2D getHead()
	{
		return head;
	}


	public Point2D getNeck()
	{
		return neck;
	}


	public Point2D getLeftShoulder()
	{
		return leftShoulder;
	}


	public Point2D getRightShoulder()
	{
		return rightShoulder;
	}

	public Point2D getTorso()
	{
		return torso;
	}

	public Point2D getLeftElbow()
	{
		return leftElbow;
	}

	public Point2D getRightElbow()
	{
		return rightElbow;
	}

	public Point2D getLeftHand()
	{
		return leftHand;
	}


	public Point2D getRightHand()
	{
		return rightHand;
	}


	public Point2D getLeftHip()
	{
		return leftHip;
	}


	public Point2D getRightHip()
	{
		return rightHip;
	}


	public Point2D getLeftKnee()
	{
		return leftKnee;
	}

	public Point2D getRightKnee()
	{
		return rightKnee;
	}


	public Point2D getLeftFoot()
	{
		return leftFoot;
	}


	public Point2D getRightFoot()
	{
		return rightFoot;
	}


	public void setHead(Point2D head)
	{
		this.head = head;
	}

	public void setNeck(Point2D neck)
	{
		this.neck = neck;
	}

	public void setLeftShoulder(Point2D leftShoulder)
	{
		this.leftShoulder = leftShoulder;
	}

	public void setRightShoulder(Point2D rightShoulder)
	{
		this.rightShoulder = rightShoulder;
	}

	public void setTorso(Point2D torso)
	{
		this.torso = torso;
	}

	public void setLeftElbow(Point2D leftElbow)
	{
		this.leftElbow = leftElbow;
	}

	public void setRightElbow(Point2D rightElbow)
	{
		this.rightElbow = rightElbow;
	}

	public void setLeftHand(Point2D leftHand)
	{
		this.leftHand = leftHand;
	}

	public void setRightHand(Point2D rightHand)
	{
		this.rightHand = rightHand;
	}

	public void setLeftHip(Point2D leftHip)
	{
		this.leftHip = leftHip;
	}

	public void setRightHip(Point2D rightHip)
	{
		this.rightHip = rightHip;
	}

	public void setLeftKnee(Point2D leftKnee)
	{
		this.leftKnee = leftKnee;
	}

	public void setRightKnee(Point2D rightKnee)
	{
		this.rightKnee = rightKnee;
	}

	public void setLeftFoot(Point2D leftFoot)
	{
		this.leftFoot = leftFoot;
	}

	public void setRightFoot(Point2D rightFoot)
	{
		this.rightFoot = rightFoot;
	}

	public void setMidpoint(Point2D midpoint)
	{
		this.midpoint = midpoint;
	}

	public Point2D getHandExact()
	{
		return handExact;
	}

	public void setHandExact(Point2D handExact)
	{
		this.handExact = handExact;
	}

	public Point3D getRightHandWorld()
	{
		return rightHandWorld;
	}

	public void setRightHandWorld(Point3D rightHandWorld)
	{
		this.rightHandWorld = rightHandWorld;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}


}
