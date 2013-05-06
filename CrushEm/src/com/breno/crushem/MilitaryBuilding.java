package com.breno.crushem;

public class MilitaryBuilding extends Building
{

	private BuildingType mType;
	
	public MilitaryBuilding(float total,  BuildingType fighterType)
	{
		super(BuildingSuperType.MILITARY, total);
		mType = fighterType;
	}

	public BuildingType getFighterType()
	{
		return mType;
	}

}
