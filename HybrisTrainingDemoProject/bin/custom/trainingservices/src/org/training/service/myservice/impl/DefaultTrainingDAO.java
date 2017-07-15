/**
 * 
 */
package org.training.service.myservice.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.training.core.model.MyTrainingModel;
import org.training.service.myservice.TrainingDAO;


/**
 * @author tugrul.aslan
 * 
 */
public class DefaultTrainingDAO implements TrainingDAO
{
	private FlexibleSearchService flexibleSearchService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.training.services.myService.TrainingDao#getMyTrainingByCode(java.lang.String)
	 */
	@Override
	public MyTrainingModel getMyTrainingByCode(final String code)
	{
		final String query = "SELECT {pk} FROM {MyTraining} where {code} = ?code";
		final Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("code", code);
		final SearchResult<MyTrainingModel> result = flexibleSearchService.search(query, parameter);

		final List<MyTrainingModel> resultList = result.getResult();

		if (resultList.size() > 0)
		{
			return resultList.get(0);
		}
		else
		{
			return null;
		}
	}

	/*
	 * WE DEFINE GETTER WITH PROTECTED BECAUSE THE REFERENCE TO HAVE ITS OWN FLEXIBLE SEARCH INSTANCE ON ITS OWN
	 */
	/**
	 * @return the flexibleSearchService
	 */
	protected FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/*
	 * REQUIRED IS ADDED BEANS ARE INTIALIZED WHEN SERVER STARTED UP SPRING SEES THIS SERVICE AS REQIRED
	 */

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	@Required
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}


}
