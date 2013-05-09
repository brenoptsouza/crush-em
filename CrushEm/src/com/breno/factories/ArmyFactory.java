package com.breno.factories;

import static com.breno.crushem.BuildingType.SPARTAN_BLUE_GUY_FOR_TEST;
import static com.breno.crushem.BuildingType.SPARTAN_ECONOMY;
import static com.breno.crushem.BuildingType.SPARTAN_GREEN_GUY_FOR_TEST;
import static com.breno.crushem.BuildingType.SPARTAN_POPULATION;
import static com.breno.crushem.BuildingType.SPARTAN_RED_GUY_FOR_TEST;

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
		
		spartanArmy.setSupportedBuildings(new BuildingType[] {
				SPARTAN_BLUE_GUY_FOR_TEST, 
				SPARTAN_ECONOMY, 
				SPARTAN_POPULATION});
		
		return spartanArmy;
		
	}
	
	public static Army createZumbiArmy() {
		
		Army zumbiArmy = new Army(ArmyType.ZOMBIE);
		
		zumbiArmy.setInitialCash(300);
		zumbiArmy.setInitialPopulation(6);
		
		zumbiArmy.setSupportedBuildings(new BuildingType[] {
				SPARTAN_GREEN_GUY_FOR_TEST, 
				SPARTAN_ECONOMY, 
				SPARTAN_POPULATION});
		
		return zumbiArmy;
		
	}
	
	public static Army createPirateArmy() {
		
		Army pirateArmy = new Army(ArmyType.PIRATE);
		
		pirateArmy.setInitialCash(500);
		pirateArmy.setInitialPopulation(2);
		
		pirateArmy.setSupportedBuildings(new BuildingType[] {
				SPARTAN_RED_GUY_FOR_TEST, 
				SPARTAN_ECONOMY, 
				SPARTAN_POPULATION});
		
		return pirateArmy;
		
	}

}
