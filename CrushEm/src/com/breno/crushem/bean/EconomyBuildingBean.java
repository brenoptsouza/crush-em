package com.breno.crushem.bean;

import com.breno.crushem.BuildingSuperType;

public class EconomyBuildingBean extends BuildingBean {

	private int mCashIncrement;
	
	public EconomyBuildingBean() {
		super();
		setSuperType(BuildingSuperType.ECONOMIY);
	}

	public int getCashIncrement() {
		return mCashIncrement;
	}

	public void setCashIncrement(int mCashIncrement) {
		this.mCashIncrement = mCashIncrement;
	}
	
	
	
}
