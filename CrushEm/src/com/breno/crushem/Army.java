package com.breno.crushem;

import com.badlogic.gdx.utils.Array;

/**
 * Classe que agrupa os dados especificos de um exercito.
 * 
 * @author Diego
 *
 */
public class Army {
	
	private ArmyType mArmyType;
	private Array<BuildingType> mBuildingsSupported;
	private int mInitialPopulation;
	private int mInitialCash;
	
	/**
	 * 
	 * Cria um {@link Army} com os seguintes parametros:
	 * 
	 * @param mArmyType
	 * 		O identificador do exercito 
	 * @param mBuildingsSupported
	 * 		O tipo de construcoes que o exercito pode construir
	 * @param mInitialPopulation
	 * 		A populacao inicial
	 * @param mInitialCash
	 * 		O dinheiro inicial
	 */
	public Army(ArmyType mArmyType) {
		this.mArmyType = mArmyType;
		
	}

	public Array<BuildingType> getmBuildingsSupported() {
		return mBuildingsSupported;
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

	public void setBuildingsSupported(Array<BuildingType> mBuildingsSupported) {
		this.mBuildingsSupported = mBuildingsSupported;
	}

	public void setInitialPopulation(int mInitialPopulation) {
		this.mInitialPopulation = mInitialPopulation;
	}

	public void setInitialCash(int mInitialCash) {
		this.mInitialCash = mInitialCash;
	}
	
	
	

}
