package com.itelligence.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.itelligence.dao.DatabaseDAO;
import com.itelligence.dao.DatabaseDAOImpl;
import com.itelligence.domain.Hotel;
import com.itelligence.domain.Region;
import com.itelligence.domain.StarGroup;
import com.itelligence.domain.Town;
import com.itelligence.itemrenderer.HotelListitemRenderer;
import com.itelligence.itemrenderer.RegionListitemRenderer;
import com.itelligence.itemrenderer.StarGroupListitemRenderer;
import com.itelligence.itemrenderer.TownListitemRenderer;
import com.itelligence.util.Utilities;

public class IndexController extends SelectorComposer<Component> {

	private static final long serialVersionUID = -6136049340719904957L;
	private final static Logger logger = Logger.getLogger(IndexController.class);

	/*
	 * Constants
	 */
	private static final int POPUP_SECONDS = 3000;
	private static final String POPUP_POSITION_END_BEFORE = "end_before";
	private static final String POPUP_POSITION_BEFORE_START = "before_start";
	private static final String POPUP_POSITION_END_AFTER = "end_after";

	@Wire
	private Datebox dateFrom;

	@Wire
	private Datebox dateTo;

	@Wire
	private Combobox cmbAdultFrom;

	@Wire
	private Combobox cmbAdultTo;

	@Wire
	private Combobox cmbChildrenFrom;

	@Wire
	private Combobox cmbChildrenTo;

	@Wire
	private Listbox listboxHotelGrid;

	@Wire
	private Listbox listboxRegion;

	@Wire
	private Listbox listboxTown;

	@Wire
	private Listbox listboxHotelStar;

	@Wire
	private Listbox listboxAction;

	@Wire
	private Textbox txtSearch;

	@Wire
	private Textbox txtFactor;

	@Wire
	private Button btnExecute;

	@Wire
	private Label lblMessages;

	@Wire
	private Checkbox checkRegion;

	private static DatabaseDAO dao = new DatabaseDAOImpl();

	/*
	 * ListModels
	 */
	private ListModelList<Region> regionModel;
	private ListModelList<StarGroup> starGroupModel;
	private ListModelList<Town> townModel = new ListModelList<>();
	private ListModelList<Hotel> hotelModel;
	private ListModelList<Hotel> hotelModelTemp = new ListModelList<>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);

		

		// Stargroup component
		starGroupModel = new ListModelList<>(dao.getAllStarGroups());
		listboxHotelStar.setItemRenderer(new StarGroupListitemRenderer());
		listboxHotelStar.setModel(starGroupModel);
		listboxHotelStar.setMultiple(true);
		listboxHotelStar.setCheckmark(true);
		
		// Hotel component
				hotelModel = new ListModelList<Hotel>(dao.getAllHotels());
				listboxHotelGrid.setModel(hotelModel);
				listboxHotelGrid.setMultiple(true);
				listboxHotelGrid.setCheckmark(true);
				listboxHotelGrid.setMold("paging");
				listboxHotelGrid.setAutopaging(true);
				
				
		// Region component
				regionModel = new ListModelList<>(dao.getAllRegions());
				listboxRegion.setItemRenderer(new RegionListitemRenderer());
				listboxRegion.setModel(regionModel);
				listboxRegion.setMultiple(true);
				listboxRegion.setCheckmark(true);
			
		// Town component
		listboxTown.setModel(townModel);
		listboxTown.setMultiple(true);
		listboxTown.setCheckmark(true);

	}

	/*
	 * Region Selection Listener
	 */
	@Listen("onSelect = #listboxRegion")
	public void regionSelectEvent() {
		// Check selection size
		int selectionSize = listboxRegion.getSelectedCount();
		if (selectionSize > 0) {

			// Collect selected regions' ids
			int[] regionIds = regionIdConvertor(getSelectedRegions());

			listboxTown.getItems().clear();
			townModel.clear();
			townModel.addAll(dao.getTownsByRegionId(regionIds));
			listboxTown.setMultiple(true);
			listboxTown.setCheckmark(true);
		} else {
			// remove all in town
			listboxTown.getItems().clear();
		}
	}

	/*
	 * Town Selection Listener
	 */
	@Listen("onSelect = #listboxTown")
	public void townSelectEvent() {
		validationAndAction();
	}
	
	private void validationAndAction(){
		int townSelectionSize = getSelectedTowns().size();
		int starSelectionSize = getSelectedStars().size();
		List<Integer> townids = new ArrayList<>();
		List<Integer> starIds = new ArrayList<>();
		boolean isCompleted = true;
		// if (townSelectionSize > 0) {

		// Element control
		if (getSelectedCheckIn() == null) {
			Clients.showNotification("Check in date cannot be empty!", "error", dateFrom, POPUP_POSITION_END_BEFORE,
					POPUP_SECONDS);
			isCompleted = false;
			// Checkout date null value
		}
		if (getSelectedCheckout() == null) {
			Clients.showNotification("Check out date cannot be empty!", "error", dateTo, POPUP_POSITION_END_BEFORE,
					POPUP_SECONDS);
			isCompleted = false;
		}
		if (getSelectedAdultFrom() == null) {
			Clients.showNotification("Adults From cannot be empty!", "error", cmbAdultFrom, POPUP_POSITION_END_BEFORE,
					POPUP_SECONDS);
			isCompleted = false;
		}
		if (getSelectedAdultTo() == null) {
			Clients.showNotification("Adults To cannot be empty!", "error", cmbAdultTo, POPUP_POSITION_END_BEFORE,
					POPUP_SECONDS);
			isCompleted = false;
		}
		if (getSelectedChildrenFrom() == null) {
			Clients.showNotification("Children From cannot be empty!", "error", cmbChildrenFrom,
					POPUP_POSITION_END_BEFORE, POPUP_SECONDS);
			isCompleted = false;
		}
		if (getSelectedChildrenTo() == null) {
			Clients.showNotification("Children To cannot be empty!", "error", cmbChildrenTo, POPUP_POSITION_END_BEFORE,
					POPUP_SECONDS);
			isCompleted = false;
		}
		
		/*
		 * All conditions are met!
		 * */
		if (isCompleted) {
			List<Hotel> hotelData = new ArrayList<>();

			townids = townIdConvertor(getSelectedTowns());
			starIds = starIdConvertor(getSelectedStars());
			
			if(townids != null || starIds != null){
				hotelData = dao.getHotelsByParameters(townids, starIds,
						Utilities.convertUtilDateToString(getSelectedCheckIn()),
						Utilities.convertUtilDateToString(getSelectedCheckout()), getSelectedAdultFrom(),
						getSelectedAdultTo(), getSelectedChildrenFrom(), getSelectedChildrenTo());
			} else if(townids == null && starIds == null){
				hotelData = dao.getAllHotels();
			}
			
			listboxHotelGrid.getItems().clear();
			hotelModel.clear();
			hotelModel.addAll(hotelData);
			listboxHotelGrid.setMultiple(true);
			listboxHotelGrid.setCheckmark(true);
		} else{
			townids = townIdConvertor(getSelectedTowns());
			starIds = starIdConvertor(getSelectedStars());
			 if(townids == null && starIds == null){
				 listboxHotelGrid.getItems().clear();
				hotelModel.clear();
				hotelModel.addAll(dao.getAllHotels());
				listboxHotelGrid.setMultiple(true);
				listboxHotelGrid.setCheckmark(true);
			 }
		}
	}

	private void setHidden(Listbox box) {
		box.setVisible(false);
	}

	private void setVisible(Listbox box) {
		box.setVisible(true);
	}
	/*
	 * Search box Listener
	 */

	@Listen("onChanging = #txtSearch")
	public void searchEvent(InputEvent event) {
		// copy all data to the temp list if the keyword is not empty
		if (event != null && event.getValue().length() >= 3) {
			List<Hotel> data = searchHotelsByName(hotelModel, event.getValue());
			logger.info("found hotels " + data.size() + " by keyword " + event.getValue());

			// copy all data to the temp
			copyHotelData(hotelModel, hotelModelTemp);

			// assign the list
			listboxHotelGrid.getItems().clear();
			hotelModel.clear();
			hotelModel.addAll(data);
			listboxHotelGrid.setMultiple(true);
			listboxHotelGrid.setCheckmark(true);

			if (data != null && data.size() == 0) {
				setHidden(listboxHotelGrid);
			} else {
				setVisible(listboxHotelGrid);
			}
		}

		// if the keyword is deleted restore the data back from the temp
		// collection
		else if ((event != null && event.getValue().length() == 0) || hotelModelTemp.size() > 0) {
			// restore the original data
			copyHotelData(hotelModelTemp, hotelModel);

			// copy all to list
			List<Hotel> hotelList = convertListModelToList(hotelModel);

			// empty the temp
			hotelModelTemp.clear();

			// assign the actual list model to the component
			listboxHotelGrid.getItems().clear();
			hotelModel.clear();
			hotelModel.addAll(hotelList);
			listboxHotelGrid.setMultiple(true);
			listboxHotelGrid.setCheckmark(true);
			if (hotelList != null && hotelList.size() > 0) {
				setVisible(listboxHotelGrid);
			}
		}

	}

	/*
	 * Star Selection Listener
	 */
	@Listen("onSelect = #listboxHotelStar")
	public void starSelectEvent() {
		validationAndAction();
		// Check selection size
//		int starSelectionSize = getSelectedStars().size();
//		int townSelectionSize = getSelectedTowns().size();
		// town selected and star selected
		// if(starSelectionSize > 0 && townSelectionSize > 0)

		// town not selected star selected
		// else if(starSelectionSize > 0 && townSelectionSize == 0)

		// nothing selected print all hotels!
		/*
		 * if (selectionSize > 0 && townSelectionSize == 0) { List<Integer>
		 * selectedStarIds = starIdConvertor(getSelectedStars());
		 * listboxHotelGrid.getItems().clear(); hotelModel.clear();
		 * hotelModel.addAll(dao.getHotelsByStarIds(selectedStarIds));
		 * listboxHotelGrid.setMultiple(true);
		 * listboxHotelGrid.setCheckmark(true); setVisible(listboxHotelGrid); }
		 * else { // remove all in town listboxHotelGrid.getItems().clear();
		 * hotelModel.clear(); listboxHotelGrid.setMultiple(true);
		 * listboxHotelGrid.setCheckmark(true); setHidden(listboxHotelGrid); }
		 */
	}

	@Listen("onClick = #btnExecute")
	public void executeButtonClickEvent() {
		// Checking date null value
		if (dateFrom.getValue() == null || dateFrom.getValue().equals(""))
			Clients.showNotification("Check in date cannot be empty!", "error", dateFrom, POPUP_POSITION_END_BEFORE,
					POPUP_SECONDS);

		// Checkout date null value
		else if (dateTo.getValue() == null || dateTo.getValue().equals("")) {
			Clients.showNotification("Check out date cannot be empty!", "error", dateTo, POPUP_POSITION_END_BEFORE,
					POPUP_SECONDS);

			// Adult from value check
		} else if (cmbAdultFrom.getValue() == null || cmbAdultFrom.getValue().equals("")) {
			Clients.showNotification("Adults From cannot be empty!", "error", cmbAdultFrom, POPUP_POSITION_END_BEFORE,
					POPUP_SECONDS);

			// Adult to value check
		} else if (cmbAdultTo.getValue() == null || cmbAdultTo.getValue().equals("")) {
			Clients.showNotification("Adults To cannot be empty!", "error", cmbAdultTo, POPUP_POSITION_END_BEFORE,
					POPUP_SECONDS);

			// child from value check
		} else if (cmbChildrenFrom.getValue() == null || cmbChildrenFrom.getValue().equals("")) {
			Clients.showNotification("Children From cannot be empty!", "error", cmbChildrenFrom,
					POPUP_POSITION_END_BEFORE, POPUP_SECONDS);

			// Adult to value check
		} else if (cmbChildrenTo.getValue() == null || cmbChildrenTo.getValue().equals("")) {
			Clients.showNotification("Children To cannot be empty!", "error", cmbChildrenTo, POPUP_POSITION_END_BEFORE,
					POPUP_SECONDS);

			// Hotel selection has made?
		} else if (getSelectedHotels().size() == 0 || getSelectedHotels() == null) {
			Clients.showNotification("There is no selected hotel!", "error", listboxHotelGrid,
					POPUP_POSITION_END_BEFORE, POPUP_SECONDS);

			// Action has been checked?
		} else if (getSelectedAction() == null || getSelectedAction().equals("")) {
			Clients.showNotification("There is no selected hotel!", "error", listboxAction, POPUP_POSITION_END_AFTER,
					POPUP_SECONDS);

			// Factor value null or empty
		} else if (getGivenFactor() == null) {
			logger.error("Factor value is invalid!");
			Clients.showNotification("Factor value is invalid!", "error", txtFactor, POPUP_POSITION_END_AFTER,
					POPUP_SECONDS);
			// Factor value range check
		} else if (!Utilities.isInRange(Integer.valueOf(txtFactor.getValue().toString()))) {
			logger.error("Factor value out of range!");
			Clients.showNotification("Factor value out of range!", "error", txtFactor, POPUP_POSITION_END_AFTER,
					POPUP_SECONDS);
		} else {
			logger.info("Executing query");
			List<Integer> hotelIds = hotelIdConvertor(getSelectedHotels());
			Integer factor = Integer.valueOf(txtFactor.getValue().toString());
			Integer operationType = Integer.valueOf(listboxAction.getSelectedItem().getValue().toString());

			StringBuilder stringBuilder = dao.executeUpdate(hotelIds,
					Utilities.convertUtilDateToString(getSelectedCheckIn()),
					Utilities.convertUtilDateToString(getSelectedCheckout()), getSelectedAdultFrom(),
					getSelectedAdultTo(), getSelectedChildrenFrom(), getSelectedChildrenTo(), factor, operationType);
			lblMessages.setValue(stringBuilder.toString());
			logger.info("Executed query : " + stringBuilder.toString());
		}

	}

	private List<Region> getSelectedRegions() {
		List<Region> list = new ArrayList<>();
		Iterator<Listitem> iterator = listboxRegion.getSelectedItems().iterator();
		while (iterator.hasNext()) {
			Listitem listitem = (Listitem) iterator.next();
			list.add(listitem.getValue());
		}
		return list;
	}

	private List<Town> getSelectedTowns() {
		List<Town> list = new ArrayList<>();
		Iterator<Listitem> iterator = listboxTown.getSelectedItems().iterator();
		while (iterator.hasNext()) {
			Listitem listitem = (Listitem) iterator.next();
			list.add(listitem.getValue());
		}
		return list;
	}

	private List<StarGroup> getSelectedStars() {
		List<StarGroup> list = new ArrayList<>();
		Iterator<Listitem> iterator = listboxHotelStar.getSelectedItems().iterator();
		while (iterator.hasNext()) {
			Listitem listitem = (Listitem) iterator.next();
			list.add(listitem.getValue());
		}
		return list;
	}

	private List<Hotel> getSelectedHotels() {
		List<Hotel> list = new ArrayList<>();
		for(Listitem li : listboxHotelGrid.getItems()){
		if(li.isSelected()){
				list.add(li.getValue());
			}
		}
		return list;
	}

	private java.util.Date getSelectedCheckIn() {
		if (dateFrom.getValue() != null)
			return dateFrom.getValue();
		return null;
	}

	private java.util.Date getSelectedCheckout() {
		if (dateTo.getValue() != null)
			return dateTo.getValue();
		return null;
	}

	private Integer getSelectedAdultFrom() {
		if (cmbAdultFrom.getSelectedItem() != null && !StringUtils.isEmpty(cmbAdultFrom.getSelectedItem().getValue()))
			return Integer.parseInt(cmbAdultFrom.getSelectedItem().getValue().toString());
		return null;
	}

	private Integer getSelectedAdultTo() {
		// Adult to value check
		if (cmbAdultTo.getSelectedItem() != null && !StringUtils.isEmpty(cmbAdultTo.getSelectedItem().getValue()))
			return Integer.parseInt(cmbAdultTo.getSelectedItem().getValue().toString());
		return null;
	}

	private Integer getSelectedChildrenFrom() {
		if (cmbChildrenFrom.getSelectedItem() != null
				&& !StringUtils.isEmpty(cmbChildrenFrom.getSelectedItem().getValue()))
			return Integer.parseInt(cmbChildrenFrom.getSelectedItem().getValue().toString());
		return null;
	}

	private Integer getSelectedChildrenTo() {
		if (cmbChildrenTo.getSelectedItem() != null
				&& !StringUtils.isEmpty(cmbChildrenTo.getSelectedItem().getValue().toString()))
			return Integer.parseInt(cmbChildrenTo.getSelectedItem().getValue().toString());
		return null;
	}

	private String getSelectedAction() {
		if (listboxAction.getSelectedItem() != null)
			return listboxAction.getSelectedItem().getValue().toString();
		return null;
	}

	private Integer getGivenFactor() {
		if (txtFactor.getValue() == null && StringUtils.isBlank(txtFactor.getValue()))
			return null;
		else
			return Integer.valueOf(txtFactor.getValue());
	}

	private String getSearchKeyword() {
		if (txtSearch != null && !StringUtils.isBlank(txtSearch.getValue().toString()))
			return txtSearch.getValue().toString();
		return null;
	}

	private static void executeQuery() {

	}

	private static int[] regionIdConvertor(final List<Region> regionList) {
		if (regionList != null && regionList.size() > 0) {
			int size = regionList.size();
			int[] regionIds = new int[size];
			for (int i = 0; i < size; i++)
				regionIds[i] = regionList.get(i).getRegionId();
			return regionIds;
		}
		return null;
	}

	private static List<Integer> townIdConvertor(final List<Town> townList) {
		if (townList != null && townList.size() > 0) {
			List<Integer> townIds = new ArrayList<>();
			for (Town obj : townList)
				townIds.add(obj.getTownId());
			return townIds;
		}
		return null;
	}

	private static List<Integer> starIdConvertor(final List<StarGroup> starList) {
		if (starList != null && starList.size() > 0) {
			List<Integer> starIds = new ArrayList<>();
			for (StarGroup obj : starList)
				starIds.add(obj.getStarGroupId());
			return starIds;
		}
		return null;

	}

	private static List<Integer> hotelIdConvertor(final List<Hotel> hotelList) {
		if (hotelList != null) {
			List<Integer> hotelIds = new ArrayList<>();
			for (Hotel obj : hotelList)
				
				hotelIds.add(obj.getHotelId());
			return hotelIds;
		}
		return null;
	}

	private static void copyHotelData(ListModelList<Hotel> source, ListModelList<Hotel> dest) {
		dest.clear();
		Iterator<Hotel> iterator = source.iterator();
		while (iterator.hasNext()) {
			Hotel hotel = (Hotel) iterator.next();
			dest.add(hotel);
		}

	}

	private static List<Hotel> searchHotelsByName(final ListModelList<Hotel> data, final String name) {
		List<Hotel> list = new ArrayList<>();
		for (Hotel obj : data) {
			if (obj.getHotelName().toLowerCase().indexOf(name.toLowerCase()) >= 0)
				list.add(obj);
		}
		return list;
	}

	private static List<Hotel> convertListModelToList(final ListModelList<Hotel> data) {
		List<Hotel> list = new ArrayList<>();
		for (Hotel obj : data)
			list.add(obj);
		return list;
	}

}
