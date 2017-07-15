/**
 * 
 */
package org.training.facades.myfacades.impl;

import de.hybris.platform.servicelayer.dto.converter.Converter;

import org.springframework.beans.factory.annotation.Required;
import org.training.core.model.MyTrainingModel;
import org.training.facades.myfacades.TrainingFacade;
import org.training.facades.myfacades.data.MyTrainingData;
import org.training.service.myservice.TrainingService;


/**
 * @author tugrul.aslan
 * 
 */
public class DefaultTrainingFacade implements TrainingFacade
{
	private TrainingService trainingService;

	private Converter<MyTrainingModel, MyTrainingData> trainingConverter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.training.facades.myfacades.TrainingFacade#getMyTraining(java.lang.String)
	 */
	@Override
	public MyTrainingData getMyTraining(final String code)
	{
		final MyTrainingModel myTraining = trainingService.getMyTraining(code);

		if (myTraining != null)
		{
			//conversion
			return trainingConverter.convert(myTraining);
		}
		return null;
	}

	/**
	 * @return the trainingService
	 */
	protected TrainingService getTrainingService()
	{
		return trainingService;
	}

	/**
	 * @param trainingService
	 *           the trainingService to set
	 */
	@Required
	public void setTrainingService(final TrainingService trainingService)
	{
		this.trainingService = trainingService;
	}

	/**
	 * @return the trainingConverter
	 */
	protected Converter<MyTrainingModel, MyTrainingData> getTrainingConverter()
	{
		return trainingConverter;
	}

	/**
	 * @param trainingConverter
	 *           the trainingConverter to set
	 */
	@Required
	public void setTrainingConverter(final Converter<MyTrainingModel, MyTrainingData> trainingConverter)
	{
		this.trainingConverter = trainingConverter;
	}

}
