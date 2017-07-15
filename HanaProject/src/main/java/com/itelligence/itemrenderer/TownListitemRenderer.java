package com.itelligence.itemrenderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.itelligence.domain.Town;

public class TownListitemRenderer implements ListitemRenderer<Town> {

	@Override
	public void render(Listitem item, Town obj, int arg2) throws Exception {
		Town town = (Town) obj;
		item.setValue(town);
		
		Listcell townNameListcell = new Listcell(town.getTownName());
		Listcell townIdListcell = new Listcell(String.valueOf(town.getTownId()));
		
		townNameListcell.setParent(item);
		townIdListcell.setParent(item);
	}

}
