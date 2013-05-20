package com.breno.crushem;

import com.breno.crushem.bean.BuildingBean;

public abstract class Building
{
	
	protected static final int BEGGINING_PROGRESS = 0;
	
	protected ArmyBase mArmyBase;
	private BuildingSuperType mSuperType;
	private int mLevel;
	private float mProgress;
	private float mTotalProgress;
	private String mThumb;
	
	
	public Building(BuildingBean buildingBean)
	{
		mSuperType = buildingBean.getSuperType();
		mArmyBase = buildingBean.getArmyBase();
		mTotalProgress = buildingBean.getTotalProgress();
		mThumb = buildingBean.getThumb();
		mLevel = 1;
		mProgress = 0;
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
		return mTotalProgress;
	}
	public void setTotal(float total)
	{
		this.mTotalProgress = total;
	}
	public String getThumb() {
		return this.mThumb;
	}
	
	public BuildingSuperType getBuildingSuperType() {
		return mSuperType;
	}
	
	public void update(float delta)
	{
		if(mProgress < mTotalProgress)
		{
			mProgress += delta;
			mProgress = mProgress >= mTotalProgress ? mTotalProgress : mProgress;  
		} else {
			enableAction();
		}
	}

	protected abstract void enableAction();
}
