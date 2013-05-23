package model;

import org.OpenNI.SkeletonJoint;

public class Recognition
{
	private double leftHandElbow, rightHandElbow, leftElbowShoulder, rightElbowShoulder, leftFootKnee, rightFootKnee, leftKneeHip, rightKneeHip, shoulder;
	private String name;
	
	public Recognition(double leftHandElbow, double rightHandElbow,
			double leftElbowShoulder, double rightElbowShoulder,
			double leftFootKnee, double rightFootKnee, double leftKneeHip,
			double rightKneeHip, double shoulder, String name)
	{
		super();
		this.leftHandElbow = leftHandElbow;
		this.rightHandElbow = rightHandElbow;
		this.leftElbowShoulder = leftElbowShoulder;
		this.rightElbowShoulder = rightElbowShoulder;
		this.leftFootKnee = leftFootKnee;
		this.rightFootKnee = rightFootKnee;
		this.leftKneeHip = leftKneeHip;
		this.rightKneeHip = rightKneeHip;
		this.shoulder = shoulder;
		this.name = name;
	}
	
	public double getLeftHandElbow()
	{
		return leftHandElbow;
	}
	public void setLeftHandElbow(double leftHandElbow)
	{
		this.leftHandElbow = leftHandElbow;
	}
	public double getRightHandElbow()
	{
		return rightHandElbow;
	}
	public void setRightHandElbow(double rightHandElbow)
	{
		this.rightHandElbow = rightHandElbow;
	}
	public double getLeftElbowShoulder()
	{
		return leftElbowShoulder;
	}
	public void setLeftElbowShoulder(double leftElbowShoulder)
	{
		this.leftElbowShoulder = leftElbowShoulder;
	}
	public double getRightElbowShoulder()
	{
		return rightElbowShoulder;
	}
	public void setRightElbowShoulder(double rightElbowShoulder)
	{
		this.rightElbowShoulder = rightElbowShoulder;
	}
	public double getLeftFootKnee()
	{
		return leftFootKnee;
	}
	public void setLeftFootKnee(double leftFootKnee)
	{
		this.leftFootKnee = leftFootKnee;
	}
	public double getRightFootKnee()
	{
		return rightFootKnee;
	}
	public void setRightFootKnee(double rightFootKnee)
	{
		this.rightFootKnee = rightFootKnee;
	}
	public double getLeftKneeHip()
	{
		return leftKneeHip;
	}
	public void setLeftKneeHip(double leftKneeHip)
	{
		this.leftKneeHip = leftKneeHip;
	}
	public double getRightKneeHip()
	{
		return rightKneeHip;
	}
	public void setRightKneeHip(double rightKneeHip)
	{
		this.rightKneeHip = rightKneeHip;
	}
	public double getShoulder()
	{
		return shoulder;
	}
	public void setShoulder(double shoulder)
	{
		this.shoulder = shoulder;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public double totalDif(Recognition r){
		double totalDif = 0;
		totalDif += Math.abs(getShoulder() - r.getShoulder());
		totalDif += Math.abs(getLeftElbowShoulder() - r.getLeftElbowShoulder());
		totalDif += Math.abs(getRightElbowShoulder() - r.getRightElbowShoulder());
		totalDif += Math.abs(getLeftFootKnee() - r.getLeftFootKnee());
		totalDif += Math.abs(getRightFootKnee() - r.getRightFootKnee());
		totalDif += Math.abs(getLeftKneeHip() - r.getLeftKneeHip());
		totalDif += Math.abs(getRightKneeHip() - r.getRightKneeHip());
		totalDif += Math.abs(getLeftHandElbow() - r.getLeftHandElbow());
		totalDif += Math.abs(getRightHandElbow() - r.getRightHandElbow());
		totalDif += Math.abs(getShoulder() - r.getShoulder());
		return totalDif;
				
	}
	
	public boolean compareTo(Recognition r){
		if(
		getShoulder() == r.getShoulder() &&
		getLeftElbowShoulder() == r.getLeftElbowShoulder() &&
		getRightElbowShoulder() == r.getRightElbowShoulder() &&
		getLeftFootKnee() == r.getLeftFootKnee() &&
		getRightFootKnee() == r.getRightFootKnee() &&
		getLeftKneeHip() == r.getLeftKneeHip() &&
		getRightKneeHip() == r.getRightKneeHip() &&
		getLeftHandElbow() == r.getLeftHandElbow() &&
		getRightHandElbow() == r.getRightHandElbow() &&
		getShoulder() == r.getShoulder()){
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		String addClass = "recogs.add(new Recognition("+leftHandElbow+", "+rightHandElbow+", "+leftElbowShoulder+", "+rightElbowShoulder+", "+leftFootKnee+", "+rightFootKnee+", "+leftKneeHip+", "+rightKneeHip+", "+shoulder+", \"Scan Name\"));";
		String toString = "Recognition [leftHandElbow=" + leftHandElbow
				+ ", rightHandElbow=" + rightHandElbow + ", leftElbowShoulder="
				+ leftElbowShoulder + ", rightElbowShoulder="
				+ rightElbowShoulder + ", leftFootKnee=" + leftFootKnee
				+ ", rightFootKnee=" + rightFootKnee + ", leftKneeHip="
				+ leftKneeHip + ", rightKneeHip=" + rightKneeHip
				+ ", shoulder=" + shoulder + ", name=" + name + "]";
		return addClass;
	}
	
	
}
