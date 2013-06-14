package com.breno.crushem.bean;

import com.breno.crushem.BuildingSuperType;

public class EconomyBuildingBean extends BuildingBean {

	//The cash increment given the building's current level
	private int[] mCashIncrement;
	
	public EconomyBuildingBean() {
		super();
		mCashIncrement = new int[3];
		setSuperType(BuildingSuperType.ECONOMIY);
	}

	public int getCashIncrement(int level) {
		return mCashIncrement[level-1];
	}

	public void setCashIncrements(int[] mCashIncrement) {
		this.mCashIncrement = mCashIncrement;
	}
	
	
	
}
