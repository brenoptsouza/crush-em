package com.breno.crushem;

import com.breno.crushem.bean.BuildingBean;

public abstract class Building
{
	
	protected static final int BEGGINING_PROGRESS = 0;
	
	protected ArmyBase mArmyBase;
	private BuildingSuperType mSuperType;
	protected int mLevel;
	private float mProgress;
	private float mTotalProgress;
	private String mThumb;
	protected BuildingBean mBean;
	
	
	public Building(BuildingBean buildingBean, ArmyBase armyBase)
	{
		mBean = buildingBean;
		mSuperType = buildingBean.getSuperType();
		mArmyBase = armyBase;
		mLevel = 1;
		mTotalProgress = buildingBean.getTotalProgress(mLevel);
		mThumb = buildingBean.getThumb(mLevel);
		mProgress = 0;
	}
	
	public BuildingBean getBean()
	{
		return mBean;
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
	public void setTotals(float total)
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
		if(mProgress< mTotalProgress)
		{
			mProgress += delta;
			mProgress = mProgress >= mTotalProgress ? mTotalProgress : mProgress;  
		} else {
			enableAction();
		}
	}

	public void upgrade()
	{
		mLevel++;
		mThumb = mBean.getThumb(mLevel);
		mTotalProgress = mBean.getTotalProgress(mLevel);
		onUpgrade();
	}
	
	protected abstract void onUpgrade();
	protected abstract void enableAction();
}
