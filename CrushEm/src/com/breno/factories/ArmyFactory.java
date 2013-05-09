package com.breno.factories;

import com.badlogic.gdx.utils.Array;
import com.breno.crushem.Army;
import com.breno.crushem.ArmyType;
import com.breno.crushem.BuildingType;

/**
 * Classe responsavel por criar os exercitos.
 * Contém os dados especificos de cada exercito.
 * 
 * @author Diego
 *
 */
public class ArmyFactory {
	
	
	public static Army createSpartanArmy() {
		
		Army spartanArmy = new Army(ArmyType.SPARTAN);
		
		spartanArmy.setInitialCash(400);
		spartanArmy.setInitialPopulation(3);
		
		Array<BuildingType> buildingsSupported = new Array<BuildingType>();
		
		buildingsSupported.add(BuildingType.SPARTAN_BLUE_GUY_FOR_TEST);
		buildingsSupported.add(BuildingType.SPARTAN_ECONOMY);
		buildingsSupported.add(BuildingType.SPARTAN_POPULATION);
		spartanArmy.setBuildingsSupported(buildingsSupported);
		
		return spartanArmy;
		
	}
	
	public static Army createZumbiArmy() {
		
		Army zumbiArmy = new Army(ArmyType.ZOMBIE);
		
		zumbiArmy.setInitialCash(300);
		zumbiArmy.setInitialPopulation(6);
		
		Array<BuildingType> buildingsSupported = new Array<BuildingType>();
		
		buildingsSupported.add(BuildingType.SPARTAN_GREEN_GUY_FOR_TEST);
		buildingsSupported.add(BuildingType.SPARTAN_ECONOMY);
		buildingsSupported.add(BuildingType.SPARTAN_POPULATION);
		zumbiArmy.setBuildingsSupported(buildingsSupported);
		
		return zumbiArmy;
		
	}
	
	public static Army createPirateArmy() {
		
		Army pirateArmy = new Army(ArmyType.PIRATE);
		
		pirateArmy.setInitialCash(500);
		pirateArmy.setInitialPopulation(2);
		
		Array<BuildingType> buildingsSupported = new Array<BuildingType>();
		
		buildingsSupported.add(BuildingType.SPARTAN_RED_GUY_FOR_TEST);
		buildingsSupported.add(BuildingType.SPARTAN_ECONOMY);
		buildingsSupported.add(BuildingType.SPARTAN_POPULATION);
		pirateArmy.setBuildingsSupported(buildingsSupported);
		
		return pirateArmy;
		
	}

}
