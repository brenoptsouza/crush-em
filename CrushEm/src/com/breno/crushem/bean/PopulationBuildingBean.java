package com.breno.crushem.bean;

import com.breno.crushem.BuildingSuperType;

public class PopulationBuildingBean extends BuildingBean {

	private int mPopulationIncrement;
	
	public PopulationBuildingBean() {
		super();
		setSuperType(BuildingSuperType.POPULATION);
	}

	public int getPopulationIncrement() {
		return mPopulationIncrement;
	}

	public void setPopulationIncrement(int mPopulationIncrement) {
		this.mPopulationIncrement = mPopulationIncrement;
	}
	
	
}
