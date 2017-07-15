package com.itelligence.itemrenderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.itelligence.domain.Hotel;

public class HotelListitemRenderer implements ListitemRenderer<Hotel> {

	@Override
	public void render(Listitem item, Hotel obj, int arg2) throws Exception {
		Hotel hotel = (Hotel) obj;
		item.setValue(hotel);

		Listcell hotelIdListcell = new Listcell(String.valueOf(hotel.getHotelId()));
		Listcell hotelNameListcell = new Listcell(hotel.getHotelName());
		Listcell townNameListcell = new Listcell(hotel.getTownName());
		Listcell starsListcell = new Listcell(hotel.getStars());

		hotelIdListcell.setParent(item);
		hotelNameListcell.setParent(item);
		townNameListcell.setParent(item);
		starsListcell.setParent(item);

	}

}
