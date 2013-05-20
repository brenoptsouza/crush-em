package com.breno.crushem.bean;

import com.badlogic.gdx.utils.Array;
import com.breno.crushem.ArmyType;
import com.breno.crushem.BuildingType;

/**
 * Classe que agrupa os dados especificos de um exercito.
 * 
 * @author Diego
 *
 */
public class ArmyBean {
	
	private ArmyType mArmyType;
	private BuildingBean[] mSupportedBuildings;
	private int mInitialPopulation;
	private int mInitialCash;
	
	/**
	 * 
	 * Cria um {@link ArmyBean} com os seguintes parametros:
	 * 
	 * @param mArmyType
	 * 		O identificador do exercito 
	 * @param mSupportedBuildings
	 * 		O tipo de construcoes que o exercito pode construir
	 * @param mInitialPopulation
	 * 		A populacao inicial
	 * @param mInitialCash
	 * 		O dinheiro inicial
	 */
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
	
	
	

}
