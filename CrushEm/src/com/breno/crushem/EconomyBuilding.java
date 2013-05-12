package com.breno.crushem;

public class EconomyBuilding extends Building
{

	private int mCashIncrement;
	
	public EconomyBuilding(ArmyBase armyBase, float total, int cashIncrement)
	{
		super(BuildingSuperType.ECONOMIY, armyBase, total);
		mCashIncrement = cashIncrement;
	}

	public int getCashIncrement()
	{
		return mCashIncrement;
	}

	public void setCashIncrement(int cashIncrement)
	{
		this.mCashIncrement = cashIncrement;
	}
	
	
	@Override
	protected void enableAction() {
		this.mArmyBase.incrementCash(mCashIncrement);
		this.setProgress(BEGGINING_PROGRESS);
		
	}
	

}
