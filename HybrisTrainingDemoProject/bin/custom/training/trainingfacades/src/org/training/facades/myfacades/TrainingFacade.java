/**
 * 
 */
package org.training.facades.myfacades;

import org.training.facades.myfacades.data.MyTrainingData;


/**
 * @author tugrul.aslan
 * 
 */
public interface TrainingFacade
{
	MyTrainingData getMyTraining(final String code);
}
