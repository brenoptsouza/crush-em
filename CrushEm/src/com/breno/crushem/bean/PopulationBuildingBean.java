package com.breno.crushem.bean;

import com.breno.crushem.BuildingSuperType;

public class PopulationBuildingBean extends BuildingBean {

	// The population increment given the building's current level
	private int[] mPopulationIncrement;
	
	public PopulationBuildingBean() {
		super();
		mPopulationIncrement = new int[3];
		setSuperType(BuildingSuperType.POPULATION);
	}

	public int getPopulationIncrement(int level) {
		return mPopulationIncrement[level-1];
	}

	public void setPopulationIncrements(int[] mPopulationIncrement) {
		this.mPopulationIncrement = mPopulationIncrement;
	}
	
	
}
