package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.taller1SM.main.Taller1SmApplication;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.repositories.ProductRepository;
import com.taller1SM.repositories.ProductSubcategoryRepository;
import com.taller1SM.repositories.ProductcategoryRepository;
import com.taller1SM.service.LocationService;
import com.taller1SM.service.ProductService;
import com.taller1SM.service.ProductServiceImp;

@SpringBootTest
@ContextConfiguration(classes =Taller1SmApplication.class )

class ServiceUnitTesting {

	// relaciones 
	private ProductService productService;
	private  LocationService locationService;
	private Productcosthistory productcosthistory;

	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
 ProductcategoryRepository productcategoryRepository;
	@Mock
	 ProductSubcategoryRepository productSubcategoryRepository;
	
	
	
	@BeforeAll
	public void productInitializer() {
		this.productService=new ProductServiceImp(productRepository, productcategoryRepository, productSubcategoryRepository);
		
	}
	
	
	
	public void setup1() {
		
		
	
	
	
	}

	
	
@Test

public void test1() {
	assertTrue(true);
}
	
}
