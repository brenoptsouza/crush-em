package com.breno.crushem;

import java.util.Iterator;

import com.badlogic.gdx.utils.Array;
import com.breno.crushem.bean.ArmyBean;
import com.breno.crushem.bean.BuildingBean;

public class ArmyBase
{
	private int mSupportedPopulation;
	private int mCash;
	private ArmyType mArmy;
	private Battlefield mBattlefield;
	private Team mTeam;
	
	private Array<Building> mBuildings;
	private Array<MilitaryBuilding> mMilitaryBuildings;
	private BuildingBean[] mSupportedBuildings;
	
	/**
	 * Cria um {@link ArmyBase} atraves do wrapper {@link ArmyBean}.
	 * 
	 */
	public ArmyBase(ArmyBean army) {
		
		mSupportedPopulation = army.getInitialPopulation();
		mCash = army.getInitialCash();
		mArmy = army.getArmyType();
		mSupportedBuildings = army.getSupportedBuildings();
		
		mBuildings = new Array<Building>();
		mMilitaryBuildings = new Array<MilitaryBuilding>();
		
	}


	public void setBattlefield(Battlefield field)
	{
		this.mBattlefield = field;
	}
	
	public void update(float delta)
	{
		// TODO: Analisar uma maneira de agrupar os buildings (O ArmyBase não precisa saber necessariamente a distinção entre eles...)
		final Iterator<Building> i = mBuildings.iterator();
		while(i.hasNext())
		{
			final Building building = i.next();
			building.update(delta);
		}
		
		final Iterator<MilitaryBuilding> j = mMilitaryBuildings.iterator();
		while(j.hasNext())
		{
			final Building building = j.next();
			building.update(delta);
		}
		
	}

	public int getCash()
	{
		return mCash;
	}
	
	public void incrementCash(int cashIncrement){
		mCash += cashIncrement;
	}
	
	public Array<MilitaryBuilding> getMilitaryBuildings()
	{
		return mMilitaryBuildings;
	}
	
	public void addBuilding(Building building)
	{
		mBuildings.add(building);
	}
	
	public boolean consumeAvailableFighter(BuildingType fighterType)
	{
		
		// TODO: Deixar com o CPU a mesma forma de iteração como foi feito com o Player
		final Iterator<MilitaryBuilding> j = mMilitaryBuildings.iterator();
		while(j.hasNext())
		{
			final MilitaryBuilding militaryBuilding = j.next();
			if(militaryBuilding.isFighterReady())
			{
				militaryBuilding.getFighter();
				return true;
			}
		}
		return false;
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
	
	public Team getTeam() {
		return mTeam;
	}

	public boolean isOverPopulation() {
		return mBattlefield.getAllFighters(mTeam).size >= mSupportedPopulation;
	}

	public void addMilitaryBuilding(MilitaryBuilding building) {
		this.mMilitaryBuildings.add(building);
	}
	
	public BuildingBean[] getBuildingSupported() {
		return mSupportedBuildings;
				
	}
	
}
