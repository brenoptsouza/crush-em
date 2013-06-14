package com.breno.crushem;

import com.breno.crushem.bean.PopulationBuildingBean;

public class PopulationBuilding extends Building
{
	protected int mSupportedPopulation;
	
	public PopulationBuilding(PopulationBuildingBean buildingBean, ArmyBase armyBase)
	{
		super(buildingBean, armyBase);
		mSupportedPopulation = buildingBean.getPopulationIncrement(mLevel);
	}

	@Override
	protected void onUpgrade()
	{
		mSupportedPopulation = ((PopulationBuildingBean)mBean).getPopulationIncrement(mLevel);
	}

	@Override
	protected void enableAction()
	{
	}
	
	
}
