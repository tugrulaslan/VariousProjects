package trader.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import trader.BrokerException;
import trader.BrokerModel;
import trader.BrokerModelImpl;
import trader.Stock;

@ManagedBean(name="stocks")
@RequestScoped
public class StocksManagedBean {
	
	private BrokerModel brokerModel = BrokerModelImpl.getInstance();
	
	public Stock[] getAllStocks() throws BrokerException{
		return brokerModel.getAllStocks();
		
	}

	public BrokerModel getBrokerModel() {
		return brokerModel;
	}

	public void setBrokerModel(BrokerModel brokerModel) {
		this.brokerModel = brokerModel;
	}
	
	

}
