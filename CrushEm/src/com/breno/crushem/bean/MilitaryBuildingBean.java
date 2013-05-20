package com.breno.crushem.bean;

import com.breno.crushem.BuildingSuperType;
import com.breno.crushem.BuildingType;

public class MilitaryBuildingBean extends BuildingBean {

	private BuildingType mType;
	
	public MilitaryBuildingBean() {
		super();
		setSuperType(BuildingSuperType.MILITARY);
	}
	
	public BuildingType getType() {
		return mType;
	}
	public void setType(BuildingType mType) {
		this.mType = mType;
	}
	
}
