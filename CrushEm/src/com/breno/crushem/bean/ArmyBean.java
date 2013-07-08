package com.breno.crushem.bean;

import com.breno.crushem.ArmyType;
import com.breno.crushem.GameObject;

/**
 * Classe que agrupa TODOS os dados especificos de um exercito.
 * 
 * @author Diego
 *
 */
public class ArmyBean {
	
	private ArmyType mArmyType;
	private BuildingBean[] mSupportedBuildings;
	private GameObject mBaseWall;
	private GameObject mBaseWallBg;
	private int mInitialPopulation;
	private int mInitialCash;
	
	public ArmyBean(ArmyType mArmyType) {
		this.mArmyType = mArmyType;
	}

	public BuildingBean[] getSupportedBuildings() {
		return mSupportedBuildings;
	}

	public int getInitialPopulation() {
		return mInitialPopulation;
	}

	public int getInitialCash() {
		return mInitialCash;
	}

	public ArmyType getArmyType() {
		return mArmyType;
	}

	public void setSupportedBuildings(BuildingBean[] mBuildingsSupported) {
		this.mSupportedBuildings = mBuildingsSupported;
	}

	public void setInitialPopulation(int mInitialPopulation) {
		this.mInitialPopulation = mInitialPopulation;
	}

	public void setInitialCash(int mInitialCash) {
		this.mInitialCash = mInitialCash;
	}

	public GameObject getmBaseWall() {
		return mBaseWall;
	}

	public void setmBaseWall(GameObject mBaseWall) {
		this.mBaseWall = mBaseWall;
	}

	public GameObject getmBaseWallBg() {
		return mBaseWallBg;
	}

	public void setmBaseWallBg(GameObject mBaseWallBg) {
		this.mBaseWallBg = mBaseWallBg;
	}
	

}
