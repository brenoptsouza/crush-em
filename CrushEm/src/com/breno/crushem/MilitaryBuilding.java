package com.breno.crushem;

import com.badlogic.gdx.assets.AssetManager;
import com.breno.crushem.bean.MilitaryBuildingBean;
import com.breno.factories.GameFactory;

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

	public GameObject getFighter(AssetManager assetMgr)
	{
		// TODO Futuramente esse metodo pode atualizar a base...
		if (fighterReady)
			return GameFactory.createFighter(mType, mArmyBase.getTeam(), mLevel, assetMgr);
		else
			return null;
	}
	
	public void reset()
	{
		fighterReady = false;
		setProgress(BEGGINING_PROGRESS);
	}

	public BuildingType getFighterType()
	{
		return mType;
	}

	@Override
	public void update(float delta)
	{
		super.update(delta);

		if (mArmyBase.isOverPopulation())
		{
			setProgress(BEGGINING_PROGRESS);
		}

	}

	@Override
	protected void enableAction()
	{
		fighterReady = true;

	}

	@Override
	protected void onUpgrade()
	{
		setProgress(0);
	}

}
