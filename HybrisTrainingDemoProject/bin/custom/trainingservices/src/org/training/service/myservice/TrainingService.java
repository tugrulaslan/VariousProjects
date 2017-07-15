/**
 * 
 */
package org.training.service.myservice;

import org.training.core.model.MyTrainingModel;


/**
 * @author tugrul.aslan
 * 
 */
public interface TrainingService
{
	MyTrainingModel getMyTraining(final String code);
}
