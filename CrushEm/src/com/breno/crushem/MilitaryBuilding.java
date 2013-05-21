package com.breno.crushem;

import com.breno.crushem.bean.MilitaryBuildingBean;

public class MilitaryBuilding extends Building
{

	private BuildingType mType;
	
	private boolean fighterReady;
	
	public MilitaryBuilding(MilitaryBuildingBean buildingBean, ArmyBase armyBase)
	{
		super(buildingBean, armyBase);
		
		mType = buildingBean.getType();
		fighterReady = false;
	}
	
	public boolean isFighterReady() 
	{
		return fighterReady;
	}
	
	
	public void getFighter() {
		// TODO Futuramente esse método pode atualizar a base...
		fighterReady = false;
		setProgress(BEGGINING_PROGRESS);
	}
	
	public BuildingType getFighterType()
	{
		return mType;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		if(mArmyBase.isOverPopulation()){
			setProgress(BEGGINING_PROGRESS);
		}
		
	}

	@Override
	protected void enableAction() {
		fighterReady = true;
		
	}
	
}
