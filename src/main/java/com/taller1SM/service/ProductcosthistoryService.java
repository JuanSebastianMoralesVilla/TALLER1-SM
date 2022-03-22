package com.taller1SM.service;

import com.taller1SM.model.prod.Productcosthistory;

public interface ProductcosthistoryService {

	void savePHC(Productcosthistory productcosthistory, Integer productId);

	void editPHC(Productcosthistory productcosthistory, Integer productId);

}
