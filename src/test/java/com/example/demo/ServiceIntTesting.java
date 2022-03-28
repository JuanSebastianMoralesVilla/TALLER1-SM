package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcategory;
import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.model.prod.Productinventory;
import com.taller1SM.model.prod.Productsubcategory;
import com.taller1SM.repositories.LocationRepository;
import com.taller1SM.repositories.ProductRepository;
import com.taller1SM.repositories.ProductSubcategoryRepository;
import com.taller1SM.repositories.ProductcategoryRepository;
import com.taller1SM.repositories.ProductcosthistoryRepository;
import com.taller1SM.repositories.ProductinventoryRepository;
import com.taller1SM.service.LocationServiceImp;
import com.taller1SM.service.ProductServiceImp;
import com.taller1SM.service.ProductcostHistoricImp;
import com.taller1SM.service.ProductinventoryImp;




@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
//@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)

public class ServiceIntTesting {

	
//	Producto
	private ProductServiceImp productService;
	private ProductRepository productRepository;
	private Product product;

	private Productsubcategory productsubcategory;
	private Productcategory productcategory;

	private ProductcategoryRepository productcategoryRepository;
	private ProductSubcategoryRepository productSubcategoryRepository;

	
	// location
	private Location location;
	private LocationServiceImp locationService;
	private LocationRepository locationRepository;
	
	
	// product cost historic

	private Productcosthistory productcosthistory;
	private ProductcostHistoricImp productcosthistoryService;
	private ProductcosthistoryRepository productcosthistoryRepository;
	
	
	
	// product inventory
	private ProductinventoryImp productinventoryService;
	private ProductinventoryRepository productinventoryRepository;
	private Productinventory productinventory;
	
	
	@Autowired
	public void ProductIntegrationTest(ProductServiceImp productService, ProductRepository productRepository,
			ProductSubcategoryRepository productSubcategoryRepository,
			ProductcategoryRepository productcategoryRepository) {

		this.productService = productService;
		this.productRepository = productRepository;
		this.productSubcategoryRepository = productSubcategoryRepository;
		this.productcategoryRepository = productcategoryRepository;

	}

	public void setupPR() {
		productsubcategory = new Productsubcategory();
		productsubcategory.setName("cel");
		productsubcategory.setProductsubcategoryid(1);

		productsubcategory = productSubcategoryRepository.save(productsubcategory);

		productcategory = new Productcategory();
		productcategory.setName("ipgone");
		productcategory.setProductcategoryid(1);

		productcategory = productcategoryRepository.save(productcategory);

	}

	
	@Test
	
	public void testSaveIntegratedProduct() {
		//setupPR();

		product = new Product();

		try {

			Timestamp sellstart = new Timestamp(System.currentTimeMillis());
			Timestamp sellend = new Timestamp(System.currentTimeMillis() + (60 * 60 * 24));
			product.setSellstartdate(sellstart);
			product.setSellenddate(sellend);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}

		product.setProductnumber("2");
		product.setWeight(2);
		product.setSize(4);

		productService.saveProduct(product, productcategory.getProductcategoryid(),
				productsubcategory.getProductsubcategoryid());

		Optional<Product> theProductEntity = productRepository.findById(product.getProductid()); // me devuelve a p

		Product theProduct = theProductEntity.get();

		assertFalse(theProduct.getProductnumber().isEmpty());
		assertTrue(theProduct.getWeight() > 0.0);
		assertTrue(theProduct.getSize() > 0);
		assertTrue(theProduct.getSellstartdate().before(theProduct.getSellenddate()));

	}

	// Servicio de guardar producto 
	@Test
	
	public void testEditIntegratedProduct() {

		

		try {
			

			Timestamp sellstart = new Timestamp(System.currentTimeMillis());
			Timestamp sellend = new Timestamp(System.currentTimeMillis() + (60 * 60 * 24));

			product.setProductnumber("00001");
			product.setSellstartdate(sellstart);
			product.setSellenddate(sellend);
			product.setWeight(-2); 
			product.setSize(2);

			assertThrows(IllegalArgumentException.class,
					() -> productService.editProduct(product,
							product.getProductsubcategory().getProductcategory().getProductcategoryid(),
							product.getProductsubcategory().getProductsubcategoryid()));
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		}

		// editar el producto con argumentos validos
		
		try {
			String productNumber = "1010";
			long weight = 2;
			long size = 3;

			Timestamp sellstart = new Timestamp(System.currentTimeMillis());
			Timestamp sellend = new Timestamp(System.currentTimeMillis() + (60 * 60 * 24));

			product.setProductnumber(productNumber);

			product.setSellstartdate(sellstart);
			product.setSellenddate(sellend);
			product.setWeight(weight);
			product.setSize(size);

			productService.editProduct(product,
					product.getProductsubcategory().getProductcategory().getProductcategoryid(),
					product.getProductsubcategory().getProductsubcategoryid());

			Optional<Product> theProductEntity = productRepository.findById(product.getProductid());
			Product theProduct = theProductEntity.get();

			assertEquals(productNumber, theProduct.getProductnumber());
			assertEquals(weight, theProduct.getWeight());
			assertEquals(size, theProduct.getSize());
			assertEquals(sellstart, theProduct.getSellstartdate());
			assertEquals(sellend, theProduct.getSellenddate());

		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		}

	}
	
	
	// test de location
	@Disabled
	@Test
	public void TestSaveLocationIntegrated() {
		
		location = new Location();
		location.setName("00001");
		location.setAvailability(new BigDecimal(10));
		location.setCostrate(new BigDecimal(1));
		
	//	locationService.saveLocation(location);
		
	//	Optional<Location> loc = locationRepository.findById(location.getLocationid()); 
		
	//	Location loc1 = loc.get();
/*
		assertFalse(loc1.getName().isEmpty());
		assertTrue(loc1.getAvailability().compareTo(new BigDecimal("0"))==1);
		assertTrue(loc1.getCostrate().compareTo(new BigDecimal("0"))==1);
		*/
		
	}
	
	
	@Test
	public void TestEditLocationIntegrated() {
		
		location = new Location();
		location.setName("00001");
		location.setAvailability(new BigDecimal(10));
		location.setCostrate(new BigDecimal(1));
		
	//locationService.saveLocation(location);
		
		//Optional<Location> loc = locationRepository.findById(location.getLocationid()); 
		
		//Location loc1 = loc.get();

	
		
		
	}
	
	
	
	// Test de product cost historic
	
	
	@Test
	
	public void TestSaveProductCostHistoric() {
		productcosthistory = new Productcosthistory();
		
		productcosthistory.setProduct(product);
		productcosthistory.setEnddate(new Timestamp(System.currentTimeMillis()+(60*60*24)));
		
		productcosthistory.setStandardcost(new BigDecimal("10"));
	}
	
	
	
	@Test
	// editar costo estandar a 20
	public void TestEditProductCostHistoric() {
		
		
productcosthistory = new Productcosthistory();
		
		productcosthistory.setProduct(product);
		productcosthistory.setModifieddate(new Timestamp(System.currentTimeMillis()+(60*60*24)));
		
		productcosthistory.setStandardcost(new BigDecimal("20"));
		
		
	}
	
	
	
	// Test de product inventory 
	
	
	public void setupPIR() {
		productinventory.getProduct();
		productinventory.getLocation();
	}
    @Test
	
	public void TestSaveProductInventory() {
		
    	productinventory=new Productinventory();
    	productinventory.setQuantity(100);
	}
	
	@Test
	
	public void TestEditProductInventory() {
		
	}

} // end of class