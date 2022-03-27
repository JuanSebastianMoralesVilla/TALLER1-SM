package com.example.demo;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.taller1SM.main.Taller1SmApplication;
import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.repositories.LocationRepository;
import com.taller1SM.repositories.ProductRepository;
import com.taller1SM.repositories.ProductSubcategoryRepository;
import com.taller1SM.repositories.ProductcategoryRepository;
import com.taller1SM.repositories.ProductcosthistoryRepository;
import com.taller1SM.repositories.ProductinventoryRepository;
import com.taller1SM.service.LocationService;
import com.taller1SM.service.LocationServiceImp;
import com.taller1SM.service.ProductService;
import com.taller1SM.service.ProductServiceImp;
import com.taller1SM.service.ProductcostHistoricImp;
import com.taller1SM.service.ProductcosthistoryService;
import com.taller1SM.service.ProductinventoryImp;
import com.taller1SM.service.ProductinventoryService;

@SpringBootTest(classes = Taller1SmApplication.class)

public class ServiceIntegrationTesting {

	
	
	
	//Probar que funcionen los metodos implementados en los servicios.
	
	
	// Product

	private ProductService productService;
	// Location

	private LocationService locationService;

	// ProductCostHistoric

	private ProductcosthistoryService productcosthistoryService;
	// ProductInventory

	private ProductinventoryService productinventoryService;
	
	//ProductService productService,LocationService locationService,ProductcosthistoryService productcosthistoryService, ProductinventoryService productinventoryService
	
	
	
	private ProductRepository productRepository;
	
	private ProductcategoryRepository productcategoryRepository;
	
	private ProductSubcategoryRepository productSubcategoryRepository;
	
	private LocationRepository locationRepository;

	private ProductcosthistoryRepository productcosthistoryRepository;

	private ProductinventoryRepository productinventoryRepository;
	
	public ServiceIntegrationTesting( ) {
		// TODO Auto-generated constructor stub
		this.productService=productService;

		this.locationService=locationService;
		this.productcosthistoryService=productcosthistoryService;
		this.productinventoryService=productinventoryService;
		
		
	}
	
	
	void setupProduct() {
	Product pr=new Product();
	pr.setProductid(1);
	
	Product pr2=new Product();
	pr2.setProductid(2);
	
	
	productService.saveProduct(pr, 1, 1);
	productService.saveProduct(pr2, 1, 1);
	}
	
	public  void setupLocation() {
		
		
		Location lc1=new Location();
		lc1.setLocationid(1);
		Location lc2=new Location();
		lc2.setLocationid(2);
		
		locationService.saveLocation(lc1);
		locationService.saveLocation(lc2);
		

	}

	
	@Test
	void saveLocation() {

		setupLocation();
		Location lc= new Location();
		lc.setLocationid(3);
		locationService.saveLocation(lc);
		
		
		
	}
		
	
	
}
