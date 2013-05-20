package com.breno.crushem;

import com.breno.crushem.bean.EconomyBuildingBean;

public class EconomyBuilding extends Building
{

	private int mCashIncrement;
	
	public EconomyBuilding(EconomyBuildingBean bean)
	{
		super(bean);
		mCashIncrement = bean.getCashIncrement();
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
