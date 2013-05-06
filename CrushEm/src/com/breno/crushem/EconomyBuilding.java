package com.breno.crushem;

public class EconomyBuilding extends Building
{

	private int mCashIncrement;
	
	public EconomyBuilding(float total, int cashIncrement)
	{
		super(BuildingSuperType.ECONOMIY, total);
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
	
	

}
