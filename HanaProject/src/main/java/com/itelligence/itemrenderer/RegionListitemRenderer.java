package com.itelligence.itemrenderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.itelligence.domain.Region;

public class RegionListitemRenderer implements ListitemRenderer<Region> {

	@Override
	public void render(Listitem item, Region obj, int arg2) throws Exception {
		Region region = (Region) obj;
		item.setValue(region);
	
		Listcell regionNameListcell = new Listcell(region.getRegionName());
		Listcell regionIdListcell = new Listcell(String.valueOf(region.getRegionId()));
		
		regionNameListcell.setParent(item);
		regionIdListcell.setParent(item);
	}

}
