package com.breno.crushem.bean;

import com.breno.crushem.ArmyBase;
import com.breno.crushem.Building;
import com.breno.crushem.BuildingSuperType;

/**
 * Classe de dados do {@link Building}
 * 
 * @author Diego
 *
 */
public class BuildingBean {
	
	private BuildingSuperType mSuperType;
	private int[] mCostForBuilding;
	private String mDescription;
	private String mName;
	//These arrays store data relative to the building's current level
	private String[] mThumb;
	private float[] mTotalProgress;
	
	public BuildingBean()
	{
		mThumb = new String[3];
		mTotalProgress = new float[3];
	}
	
	public float getTotalProgress(int level) {
		return mTotalProgress[level-1];
	}
	public void setTotalProgresses(float[] mTotalProgress) {
		this.mTotalProgress = mTotalProgress;
	}
	public BuildingSuperType getSuperType() {
		return mSuperType;
	}
	public void setSuperType(BuildingSuperType mSuperType) {
		this.mSuperType = mSuperType;
	}
	public int getCostForBuilding(int level) {
		return mCostForBuilding[level-1];
	}
	public void setCostForBuilding(int[] mCostForBuilding) {
		this.mCostForBuilding = mCostForBuilding;
	}
	public String getDescription() {
		return mDescription;
	}
	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}
	public String getThumb(int level) {
		return mThumb[level-1];
	}
	public void setThumbs(String[] mThumb) {
		this.mThumb = mThumb;
	}
	public String getName() {
		return mName;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	
	


}
