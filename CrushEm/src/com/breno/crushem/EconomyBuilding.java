package com.breno.crushem;

import javax.management.MBeanAttributeInfo;

import com.breno.crushem.bean.EconomyBuildingBean;

public class EconomyBuilding extends Building
{

	private int mCashIncrement;
	
	public EconomyBuilding(EconomyBuildingBean bean, ArmyBase armyBase)
	{
		super(bean, armyBase);
		mCashIncrement = bean.getCashIncrement(mLevel);
	}

	public int getCashIncrement()
	{
		return mCashIncrement;
	}

	public void setCashIncrements(int cashIncrement)
	{
		this.mCashIncrement = cashIncrement;
	}
	
	
	@Override
	protected void enableAction() {
		this.mArmyBase.incrementCash(mCashIncrement);
		this.setProgress(BEGGINING_PROGRESS);
		
	}

	@Override
	protected void onUpgrade()
	{
		setProgress(0);
		mCashIncrement = ((EconomyBuildingBean)mBean).getCashIncrement(mLevel);
	}
	

}
