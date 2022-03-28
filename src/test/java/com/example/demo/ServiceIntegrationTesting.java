package com.example.demo;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.taller1SM.main.Taller1SmApplication;
import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcategory;
import com.taller1SM.model.prod.Productsubcategory;
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

@SpringBootTest



public class ServiceIntegrationTesting {

	// Probar que funcionen los metodos implementados en los servicios.

	// Product

	private ProductService productService;
	

	// Location

	private LocationService locationService;

	// ProductCostHistoric

	private ProductcosthistoryService productcosthistoryService;
	// ProductInventory

	private ProductinventoryService productinventoryService;

	// ProductService productService,LocationService
	// locationService,ProductcosthistoryService productcosthistoryService,
	// ProductinventoryService productinventoryService

	private ProductRepository productRepository;

	private ProductcategoryRepository productcategoryRepository;

	private ProductSubcategoryRepository productSubcategoryRepository;

	private LocationRepository locationRepository;

	private ProductcosthistoryRepository productcosthistoryRepository;

	private ProductinventoryRepository productinventoryRepository;

	@Autowired

	public ServiceIntegrationTesting() {
		// TODO Auto-generated constructor stub
		this.productService = productService;

		this.locationService = locationService;
		this.productcosthistoryService = productcosthistoryService;
		this.productinventoryService = productinventoryService;

	}

	


	public void setupPR() {
		
		Productcategory pc = new Productcategory();
		
		pc.setProductcategoryid(1);
		pc.setName("cel");
		pc.setModifieddate(new Timestamp(System.currentTimeMillis()));
		productcategoryRepository.save(pc);
		
		Productsubcategory sub = new Productsubcategory();
		
		sub.setProductsubcategoryid(1);
		sub.setName("iphone");
		sub.setModifieddate(new Timestamp(System.currentTimeMillis()));
		productSubcategoryRepository.save(sub);
		
		
	}

	
	
	@Nested
	@DisplayName("Add product tests")
	class SaveProductTests {

		
		
		
		
		
	}

	@Nested
	@DisplayName("Edit product tests")
	class editProductTests {
		
	}
/// --------------------------- LOCATION----------------------------------

	public void setupLOC() {

		Location lc1 = new Location();
		lc1.setLocationid(1);
		Location lc2 = new Location();
		lc2.setLocationid(2);

		locationService.saveLocation(lc1);
		locationService.saveLocation(lc2);

	}
	
	@Test
	public void test2() {
		assertTrue(true);
	}

	
	@Nested
	@DisplayName("Save Location test")
	class SaveLocationTests {

		
	}

	@Nested
	@DisplayName("Edit Location Test")
	class editLocationTests {
		
	}

	
	//-----------  Product inventory --------------
	@Test
	public void test3() {
		assertTrue(true);
	}

	
	@Nested
	@DisplayName("Save Product inventory test")
	class SaveProductInventoryTests {

		
	}// fin clase save PIR

	@Nested
	@DisplayName("Edit Product inventory Test")
	class edigProductInventoryTests {
		
	}

	

}
