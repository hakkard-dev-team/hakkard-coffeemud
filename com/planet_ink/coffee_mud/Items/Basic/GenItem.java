package com.planet_ink.coffee_mud.Items.Basic;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.util.*;
import com.planet_ink.coffee_mud.Libraries.interfaces.*;

/*
   Copyright 2001-2018 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
public class GenItem extends StdItem
{
	@Override
	public String ID()
	{
		return "GenItem";
	}

	protected String	readableText="";
	public GenItem()
	{
		super();
		setName("a generic item");
		basePhyStats.setWeight(2);
		setDisplayText("a generic item sits here.");
		setDescription("");
		baseGoldValue=5;
		basePhyStats().setLevel(1);
		recoverPhyStats();
		setMaterial(RawMaterial.RESOURCE_OAK);
	}

	@Override
	public boolean isGeneric()
	{
		return true;
	}

	@Override
	public String text()
	{
		return CMLib.coffeeMaker().getPropertiesStr(this,false);
	}

	@Override
	public String readableText()
	{
		return readableText;
	}

	@Override
	public void setReadableText(String text)
	{
		readableText=text;
	}

	@Override
	public void setMiscText(String newText)
	{
		miscText="";
		CMLib.coffeeMaker().setPropertiesStr(this,newText,false);
		recoverPhyStats();
	}

	@Override
	public String getStat(String code)
	{
		if(CMLib.coffeeMaker().getGenItemCodeNum(code)>=0)
			return CMLib.coffeeMaker().getGenItemStat(this,code);
		return CMProps.getStatCodeExtensionValue(getStatCodes(), xtraValues, code);
	}

	@Override
	public void setStat(String code, String val)
	{
		if(CMLib.coffeeMaker().getGenItemCodeNum(code)>=0)
			CMLib.coffeeMaker().setGenItemStat(this,code,val);
		CMProps.setStatCodeExtensionValue(getStatCodes(), xtraValues, code,val);
	}

	private static String[] codes=null;

	@Override
	public String[] getStatCodes()
	{
		if(codes==null)
			codes=CMProps.getStatCodesList(CMParms.toStringArray(GenericBuilder.GenItemCode.values()),this);
		return codes;
	}

	@Override
	public boolean sameAs(final Environmental E)
	{
		if(!(E instanceof GenItem))
			return false;
		final String[] theCodes=getStatCodes();
		for(int i=0;i<theCodes.length;i++)
		{
			if(!E.getStat(theCodes[i]).equals(getStat(theCodes[i])))
				return false;
		}
		return true;
	}
}
