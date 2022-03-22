package com.taller1.service;

import java.util.Optional;

import com.taller1.model.prod.Product;
import com.taller1.model.prod.Productcosthistory;
import com.taller1.repositories.ProductRepository;
import com.taller1.repositories.ProductcosthistoryRepository;

public class ProductcostHistoricImp implements  ProductcosthistoryService{
	
	ProductRepository productRepository;
	ProductcosthistoryRepository productcosthistoryRepository;
	
	
	public ProductcostHistoricImp(ProductcosthistoryRepository productcosthistoryRepository,	ProductRepository productRepository) {
		this.productRepository=productRepository;
		this.productcosthistoryRepository=productcosthistoryRepository;
		
	}
	
	public void savePHC(Productcosthistory productcosthistory, Integer productId) {
		   Optional<Product> product=productRepository.findById(productId);
		   
		   if(product.isEmpty()) {
			   throw new RuntimeException();
		   }else if(productcosthistoryRepository==null) {
			   throw new RuntimeException();
		   }else if(product.get().getSellstartdate().after(product.get().getSellenddate())) {
			   
		   }
	}
 
}
