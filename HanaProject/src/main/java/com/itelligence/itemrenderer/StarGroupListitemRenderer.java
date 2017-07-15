package com.itelligence.itemrenderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.itelligence.domain.StarGroup;

public class StarGroupListitemRenderer implements ListitemRenderer<StarGroup> {

	@Override
	public void render(Listitem item, StarGroup obj, int arg2) throws Exception {
		StarGroup starGroup = (StarGroup) obj;
		item.setValue(starGroup);
		
		Listcell starNameListcell = new Listcell(starGroup.getStarGroupName());
		Listcell starIdListcell = new Listcell(String.valueOf(starGroup.getStarGroupId()));
		
		starNameListcell.setParent(item);
		starIdListcell.setParent(item);
	}

}
