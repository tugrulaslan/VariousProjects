/**
 * 
 */
package org.training.service.myservice;

import org.training.core.model.MyTrainingModel;


/**
 * @author tugrul.aslan
 * 
 */
public interface TrainingDAO
{
	MyTrainingModel getMyTrainingByCode(String code);
}
