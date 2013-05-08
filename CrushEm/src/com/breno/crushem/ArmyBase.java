package com.breno.crushem;

import java.util.Iterator;

import com.badlogic.gdx.utils.Array;

public class ArmyBase
{
	private int mSupportedPopulation;
	private int mCash;
	private ArmyType mArmy;
	private Battlefield mBattlefield;
	private Team mTeam;
	
	Array<MilitaryBuilding> mMilitaryBuildings;
	Array<EconomyBuilding> mEconomyBuildings;
	Array<Building> mPopulationBuildings;
	Array<BuildingType> mAvailableTrainedUnits;
	
	
	public ArmyBase(ArmyType armyType, int initialSupportedPopulation, int initialCash)
	{
		mSupportedPopulation = initialSupportedPopulation;
		mCash = initialCash;
		mArmy = armyType;
		
		mMilitaryBuildings = new Array<MilitaryBuilding>();
		mEconomyBuildings = new Array<EconomyBuilding>();
		mPopulationBuildings = new Array<Building>();
		mAvailableTrainedUnits = new Array<BuildingType>();
	}
	
	public void setBattlefield(Battlefield field)
	{
		this.mBattlefield = field;
	}
	
	public void update(float delta)
	{
		final Iterator<EconomyBuilding> i = mEconomyBuildings.iterator();
		while(i.hasNext())
		{
			final EconomyBuilding economyBuilding = i.next();
			economyBuilding.update(delta);
			if(economyBuilding.getProgress() >= economyBuilding.getTotal())
			{
				mCash += economyBuilding.getCashIncrement();
				economyBuilding.setProgress(0);
			}
		}
		
		final boolean overPopulation = mBattlefield.getAllFighters(mTeam).size >= mSupportedPopulation;
		final Iterator<MilitaryBuilding> j = mMilitaryBuildings.iterator();
		while(j.hasNext())
		{
			final MilitaryBuilding militaryBuilding = j.next();
			if(overPopulation)
				militaryBuilding.setProgress(0);
			else
			{
				final float previousProgress = militaryBuilding.getProgress();
				militaryBuilding.update(delta);
				if(previousProgress < militaryBuilding.getTotal()
				&& militaryBuilding.getProgress() >= militaryBuilding.getTotal())
					mAvailableTrainedUnits.add(militaryBuilding.getFighterType());
			}
		}
		
		//TODO update the population buildings
	}

	public int getCash()
	{
		return mCash;
	}
	
	public Array<MilitaryBuilding> getMilitaryBuildings()
	{
		return mMilitaryBuildings;
	}
	
	public Array<BuildingType> getAvailableTrainedUnits()
	{
		return mAvailableTrainedUnits;
	}

	public void addEconomyBuilding(EconomyBuilding building)
	{
		mEconomyBuildings.add(building);
	}
	
	public boolean consumeAvailableFighter(BuildingType fighterType)
	{
		final boolean removed = mAvailableTrainedUnits.removeValue(fighterType, true);
		
		if(removed)
		{
			//restart the building count
			final Iterator<MilitaryBuilding> j = mMilitaryBuildings.iterator();
			while(j.hasNext())
			{
				final MilitaryBuilding militaryBuilding = j.next();
				if(militaryBuilding.getProgress() >= militaryBuilding.getTotal())
				{
					militaryBuilding.setProgress(0);
					break;
				}
			}
		}
		return removed;
	}

	public void addMilitaryBuilding(MilitaryBuilding militaryBuilding)
	{
		mMilitaryBuildings.add(militaryBuilding);
	}
	
	public ArmyType getArmyType()
	{
		return mArmy;
	}

	public void spendCash(int cash)
	{
		mCash -= cash;
	}

	public int getPopulationLimit()
	{
		return mSupportedPopulation;
	}

	public void increasePopulationBy(int populationIncrease)
	{
		mSupportedPopulation += populationIncrease;
	}

	public void setTeam(Team team)
	{
		mTeam = team;
	}
	
}
