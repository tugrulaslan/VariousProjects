/**
 * 
 */
package org.training.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import org.training.core.model.MyTrainingModel;
import org.training.facades.myfacades.data.MyTrainingData;


/**
 * @author tugrul.aslan
 * 
 */
public class TrainingDataPopulator implements Populator<MyTrainingModel, MyTrainingData>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final MyTrainingModel source, final MyTrainingData target) throws ConversionException
	{
		target.setCode(source.getCode());
		target.setName(source.getName());
		target.setActive(source.getActive() == null ? false : source.getActive().booleanValue());

	}

}
