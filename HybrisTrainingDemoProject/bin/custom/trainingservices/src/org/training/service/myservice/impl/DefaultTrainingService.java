/**
 * 
 */
package org.training.service.myservice.impl;

import de.hybris.platform.servicelayer.model.ModelService;

import org.springframework.beans.factory.annotation.Required;
import org.training.core.model.MyTrainingModel;
import org.training.service.myservice.TrainingDAO;
import org.training.service.myservice.TrainingService;


/**
 * @author tugrul.aslan
 * 
 */
public class DefaultTrainingService implements TrainingService
{

	private TrainingDAO trainingDAO;
	private ModelService modelService;

	/*
	 * { modelService.save(new MyTrainingModel()); modelService.refresh(new MyTrainingModel()); modelService.remove(new
	 * MyTrainingModel()); }
	 */


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.training.service.myservice.TrainingService#getMyTraining(java.lang.String)
	 */
	@Override
	public MyTrainingModel getMyTraining(final String code)
	{
		// YTODO Auto-generated method stub
		return trainingDAO.getMyTrainingByCode(code);
	}


	/**
	 * @return the trainingDAO
	 */
	protected TrainingDAO getTrainingDAO()
	{
		return trainingDAO;
	}


	/**
	 * @param trainingDAO
	 *           the trainingDAO to set
	 */
	@Required
	public void setTrainingDAO(final TrainingDAO trainingDAO)
	{
		this.trainingDAO = trainingDAO;
	}


	/**
	 * @return the modelService
	 */
	protected ModelService getModelService()
	{
		return modelService;
	}


	/**
	 * @param modelService
	 *           the modelService to set
	 */
	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}




}
