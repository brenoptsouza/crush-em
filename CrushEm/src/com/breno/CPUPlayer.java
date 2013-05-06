package com.breno;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.breno.crushem.ArmyBase;
import com.breno.crushem.Battlefield;
import com.breno.crushem.BuildingType;
import com.breno.crushem.GameObject;
import com.breno.crushem.GameObjectFactory;
import com.breno.crushem.MilitaryBuilding;
import com.breno.crushem.Team;

public class CPUPlayer
{
	private Battlefield mBattlefield;
	private Random mRandom;
	private Stage mStage;
	private AssetManager mAssetMgr;

	public CPUPlayer(Battlefield battlefield, Stage stage, AssetManager assetMgr)
	{
		this.mBattlefield = battlefield;
		this.mStage = stage;
		this.mAssetMgr = assetMgr;
		mRandom = new Random();

		// TODO This is tjust for tests
		addMilitaryBuilding(new MilitaryBuilding(20, BuildingType.SPARTAN_BLUE_GUY_FOR_TEST));
		addMilitaryBuilding(new MilitaryBuilding(18, BuildingType.SPARTAN_BLUE_GUY_FOR_TEST));
		addMilitaryBuilding(new MilitaryBuilding(26, BuildingType.SPARTAN_BLUE_GUY_FOR_TEST));

	}

	public void update(float delta)
	{
		final ArmyBase base = mBattlefield.getCpuBase();
		final Iterator<BuildingType> i = base.getAvailableTrainedUnits().iterator();

		while (i.hasNext())
		{
			final BuildingType type = i.next();
			final int lane = mRandom.nextInt(mBattlefield.getNumberOfLanes());
			final Rectangle laneBounds = mBattlefield.getLaneBounds(lane);
			final GameObject fighter = GameObjectFactory.createFighter(type, Team.AWAY, mAssetMgr);
			GameObjectFactory.spawnGameObject(fighter, lane, mBattlefield.getLevelWidth(), (7 + laneBounds.y) + mRandom.nextInt((int) laneBounds.height - 29), mStage, mBattlefield);
			base.consumeAvailableFighter(type);
		}
	}

	private void addMilitaryBuilding(MilitaryBuilding building)
	{
		mBattlefield.getCpuBase().addMilitaryBuilding(building);
	}
}
