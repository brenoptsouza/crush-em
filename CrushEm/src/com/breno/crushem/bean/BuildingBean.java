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
	private int mCostForBuilding;
	private String mDescription;
	private String mThumb;
	private String mName;
	private float mTotalProgress;
	
	public float getTotalProgress() {
		return mTotalProgress;
	}
	public void setTotalProgress(float mTotalProgress) {
		this.mTotalProgress = mTotalProgress;
	}
	public BuildingSuperType getSuperType() {
		return mSuperType;
	}
	public void setSuperType(BuildingSuperType mSuperType) {
		this.mSuperType = mSuperType;
	}
	public int getCostForBuilding() {
		return mCostForBuilding;
	}
	public void setCostForBuilding(int mCostForBuilding) {
		this.mCostForBuilding = mCostForBuilding;
	}
	public String getDescription() {
		return mDescription;
	}
	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}
	public String getThumb() {
		return mThumb;
	}
	public void setThumb(String mThumb) {
		this.mThumb = mThumb;
	}
	public String getName() {
		return mName;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	
	


}
