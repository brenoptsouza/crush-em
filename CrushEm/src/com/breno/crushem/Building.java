package com.breno.crushem;

public class Building
{
	private BuildingSuperType mSuperType;
	private int mLevel;
	private float mProgress;
	private float mTotal;
	
	public Building(BuildingSuperType superType, float total)
	{
		mSuperType = superType;
		mLevel = 1;
		mProgress = 0;
		mTotal = total;
	}
	
	public int getLevel()
	{
		return mLevel;
	}
	public void setLevel(int level)
	{
		this.mLevel = level;
	}
	public float getProgress()
	{
		return mProgress;
	}
	public void setProgress(float progress)
	{
		this.mProgress = progress;
	}
	public float getTotal()
	{
		return mTotal;
	}
	public void setTotal(float total)
	{
		this.mTotal = total;
	}
	
	public void update(float delta)
	{
		if(mProgress < mTotal)
		{
			mProgress += delta;
			mProgress = mProgress >= mTotal ? mTotal : mProgress;  
		}
	}
}
