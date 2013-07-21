package com.breno.crushem;

import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.breno.crushem.Screens.LevelScreen;

public class Battlefield
{
	Array<Array<GameObject>> mHomeFighters;
	Array<Array<GameObject>> mAwayFighters;
	
	private ArmyBase mPlayerBase;
	private ArmyBase mCpuBase;
	private GameObject mHomeBaseWall;
	private GameObject mAwayBaseWall;
	private ObjectMap<Integer, Rectangle> mLanesBounds;
	private float mLevelWidth;
	private LevelScreen mLevelScreen;
	
	public Battlefield(LevelScreen levelScreen, ArmyBase playerBase, ArmyBase cpuBase)
	{
		mLevelScreen = levelScreen;
		mPlayerBase = playerBase;
		mPlayerBase.setTeam(Team.HOME);
		mCpuBase = cpuBase;
		mCpuBase.setTeam(Team.AWAY);
		mLanesBounds = new ObjectMap<Integer, Rectangle>(3);
		
		mHomeFighters = new Array<Array<GameObject>>();
		mAwayFighters = new Array<Array<GameObject>>();
		
		mPlayerBase.setBattlefield(this);
		mCpuBase.setBattlefield(this);
	}
	
	public void addFighter(GameObject fighter)
	{
		final Array<Array<GameObject>> teamArray = fighter.getTeam() == Team.HOME ? mHomeFighters : mAwayFighters;
		final Array<GameObject> laneArray = teamArray.get(fighter.getLane());
		laneArray.add(fighter);
		fighter.setBattlefield(this);
	}
	
	public void removeFighter(GameObject fighter)
	{
		final Array<Array<GameObject>> teamArray = fighter.getTeam() == Team.HOME ? mHomeFighters : mAwayFighters;
		final Array<GameObject> laneArray = teamArray.get(fighter.getLane());
		laneArray.removeValue(fighter, false);
	}
	
	public GameObject getAvailableTarget(GameObject attacker)
	{
		final Array<Array<GameObject>> enemyArray = attacker.getTeam() == Team.HOME ? mAwayFighters : mHomeFighters;
		final Array<GameObject> laneArray = enemyArray.get(attacker.getLane());
		
		final Iterator<GameObject> i = laneArray.iterator();
		GameObject possibleTarget;
		while(i.hasNext())
		{
			possibleTarget = i.next();
			if(attacker.getBounds().overlaps(possibleTarget.getBounds()))
				return possibleTarget;
		}
		return null;
	}

	public Array<GameObject> getAllFighters(Team team) 
	{
		final Array<GameObject> result = new Array<GameObject>();
		if(team == null || team == Team.HOME)
		{
			Iterator<Array<GameObject>> i = mHomeFighters.iterator();
			while(i.hasNext())
				result.addAll(i.next());
		}
		if(team == Team.HOME)
			return result;
		
		if(team == null || team == Team.AWAY)
		{
			Iterator<Array<GameObject>> j = mAwayFighters.iterator();
			while(j.hasNext())
				result.addAll(j.next());
		}
		return result;
	}
	
	public GameObject getHomeBaseWall()
	{
		return mHomeBaseWall;
	}

	public void setHomeBaseWall(GameObject homeBaseWall)
	{
		this.mHomeBaseWall = homeBaseWall;
	}

	public GameObject getAwayBaseWall()
	{
		return mAwayBaseWall;
	}

	public void setAwayBaseWall(GameObject awayBaseWall)
	{
		this.mAwayBaseWall = awayBaseWall;
	}

	public int getNumberOfLanes()
	{
		return mLanesBounds.size;
	}
	
	public void addLane(int laneInex, Rectangle bounds)
	{
		mLanesBounds.put(laneInex, bounds);
		mHomeFighters.add(new Array<GameObject>());
		mAwayFighters.add(new Array<GameObject>());
	}
	
	public Rectangle getLaneBounds(int laneIndex)
	{
		return mLanesBounds.get(laneIndex);
	}

	public float getLaneScaleFactor(int laneIndex)
	{
		return mLanesBounds.get(laneIndex).width / mLanesBounds.get(0).width; 
	}
	
	public ArmyBase getPlayerBase()
	{
		return mPlayerBase;
	}

	public void setPlayerBase(ArmyBase playerBase)
	{
		this.mPlayerBase = playerBase;
	}

	public ArmyBase getCpuBase()
	{
		return mCpuBase;
	}

	public void setCpuBase(ArmyBase cpuBase)
	{
		this.mCpuBase = cpuBase;
	}
	
	public void update(float delta)
	{
		mPlayerBase.update(delta);
		mCpuBase.update(delta);
		
		final int homeHp = mHomeBaseWall.getHp();
		if(homeHp <= 0) {
			mLevelScreen.declareWinner(mCpuBase);
		}
		
		final int awayHp = mAwayBaseWall.getHp();
		if(awayHp <= 0) {
			mLevelScreen.declareWinner(mPlayerBase);
		}
		
	}



	public float getLevelWidth()
	{
		return mLevelWidth;
	}
	
	public void setLevelWidth(float levelWidth)
	{
		mLevelWidth = levelWidth;
	}
}
