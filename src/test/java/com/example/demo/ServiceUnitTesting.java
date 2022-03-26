package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.taller1SM.main.Taller1SmApplication;
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
import com.taller1SM.service.LocationService;
import com.taller1SM.service.LocationServiceImp;
import com.taller1SM.service.ProductService;
import com.taller1SM.service.ProductServiceImp;
import com.taller1SM.service.ProductcostHistoricImp;
import com.taller1SM.service.ProductinventoryImp;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = Taller1SmApplication.class)

@TestInstance(Lifecycle.PER_CLASS)
class ServiceUnitTesting {

	// Product
	@InjectMocks
	private ProductServiceImp productService;
	// Location
	@InjectMocks
	private LocationServiceImp locationService;
	// ProductCostHistoric
	@InjectMocks
	private ProductcostHistoricImp productcostHistoricService;
	// ProductInventory
	@InjectMocks
	private ProductinventoryImp productinventoryService;

	// Mocks de repositorios
	@Mock
	private ProductRepository productRepository;
	@Mock
	private ProductcategoryRepository productcategoryRepository;
	@Mock
	private ProductSubcategoryRepository productSubcategoryRepository;
	@Mock
	private LocationRepository locationRepository;
	@Mock
	private ProductcosthistoryRepository productcosthistoryRepository;
	@Mock
	private ProductinventoryRepository productinventoryRepository;

	// inicialiaziador

	public ServiceUnitTesting() {
		productService = new ProductServiceImp(productRepository, productcategoryRepository,
				productSubcategoryRepository);
		locationService = new LocationServiceImp(locationRepository);
		productcostHistoricService = new ProductcostHistoricImp(productcosthistoryRepository, productRepository);
		productinventoryService = new ProductinventoryImp(productinventoryRepository, productRepository,
				locationRepository);
	}

	// Escenarios

	public void setup1Product() {
		Product pr = new Product();

		Productcategory productCategory = new Productcategory();
		Productsubcategory productSubCategory = new Productsubcategory();
		pr.setProductid(1);
		productCategory.setProductcategoryid(1);
		productSubCategory.setProductsubcategoryid(1);
		when(productRepository.findById(1)).thenReturn(Optional.of(pr));

	}

	@BeforeAll
	public void setup1Location() {
		Location loc = new Location();
		// 1 elemento
		loc.setLocationid(1);
		loc.setName("00005");
		loc.setAvailability(new BigDecimal(10));
		loc.setCostrate(new BigDecimal(1));
		// 2 elemento
		loc.setLocationid(2);
		loc.setName("123456");
		loc.setAvailability(new BigDecimal(9));
		loc.setCostrate(new BigDecimal(0));
		// 3 elemento
		loc.setLocationid(3);
		loc.setName("12345");
		loc.setAvailability(new BigDecimal(3));
		loc.setCostrate(new BigDecimal(1));

		when(locationRepository.findById(1)).thenReturn(Optional.of(loc));

		when(locationRepository.findById(2)).thenReturn(Optional.of(loc));

		when(locationRepository.findById(3)).thenReturn(Optional.of(loc));

	}

	// ---------Pruebas de Product------------

	// SAVE PRODUCT

// test que envia la excepcion si el producto es nulo
	@Test
	public void testSave1Product() {
		Product pr = null;
		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, null, null);
		});
	}

	// Agregar un producto valido
	@Test
	public void testSave2Product() {
		Product pr = new Product();
		pr.setName("iphone");
		pr.setWeight((long) 1);
		pr.setSize((long) 10);
		// pr.setSellenddate();
		// pr.setSellstartdate();
		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// Agregar un producto con tamaño -1

	@Test
	public void testSave3Product() {
		Product pr = new Product();
		pr.setName("iphone");
		pr.setWeight((long) 1);
		pr.setSize((long) -1);
		// pr.setSellenddate();
		// pr.setSellstartdate();
		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// Agregar un producto con peso > 0

	@Test
	public void testSave4Product() {
		Product pr = new Product();
		pr.setName("iphone");
		pr.setWeight((long) -1);
		pr.setSize((long) 10);
		// pr.setSellenddate();
		// pr.setSellstartdate();
		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// ---------Pruebas de location------------

	// SAVE LOCATION

	@Test
	public void testSave1Location() {
		Location l = null;

		assertThrows(RuntimeException.class, () -> {
			locationService.saveLocation(l);
		});

	}

	@Test
	public void testSave2Location() {
		Location loc = new Location();
		loc.setLocationid(1);
		loc.setName("00005");
		loc.setAvailability(new BigDecimal(10));
		loc.setCostrate(new BigDecimal(1));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc);
		});

	}

	// validar cantidad de caracetres en el nombre
	@Disabled
	@Test
	public void testSave3Location() {

		Location loc2 = new Location();
		loc2.setLocationid(7);
		loc2.setName("1234");
		loc2.setAvailability(new BigDecimal(10));
		loc2.setCostrate(new BigDecimal(1));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}

	// disponibilidad
	@Test
	public void testSave4Location() {

		Location loc2 = new Location();
		loc2.setLocationid(7);
		loc2.setName("12345");
		loc2.setAvailability(new BigDecimal(-1));
		loc2.setCostrate(new BigDecimal(1));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}

	// tasa de costo
	@Test
	public void testSave5Location() {

		Location loc2 = new Location();
		loc2.setLocationid(7);
		loc2.setName("12345");
		loc2.setAvailability(new BigDecimal(10));
		loc2.setCostrate(new BigDecimal(3));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}
	
	// 
		@Test
		public void testSave6Location() {

			Location loc2 = new Location();
			loc2.setLocationid(8);
			loc2.setName("12345678");
			loc2.setAvailability(new BigDecimal(4));
			loc2.setCostrate(new BigDecimal(1));
     
			assertThrows(IllegalArgumentException.class, () -> {
				locationService.saveLocation(loc2);
			});

		}

	// ---------Pruebas de historic cost------------

	// SAVE PRODUCT COST HISTORIC

	@Test
	public void testSave1ProductCostHistory() {

		Productcosthistory prcH = null;
		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.savePHC(prcH, null);
		});

	}

	// Validacion de fecha 2: mayor o menor y formato
	@Test
	public void testSave2ProductCostHistory() {

		Productcosthistory prcH = new Productcosthistory();
		Product pr = new Product();
		pr.setProductid(1);
		prcH.setProduct(pr);
		prcH.setId(1);
		prcH.setStandardcost(new BigDecimal(10));
		// falta fecha

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.savePHC(prcH, 1);
		});

	}

	// costo estandar
	@Test
	public void testSave4ProductCostHistory() {

		Productcosthistory prcH = new Productcosthistory();
		Product pr = new Product();
		pr.setProductid(1);
		prcH.setProduct(pr);
		prcH.setId(1);
		prcH.setStandardcost(new BigDecimal(-10));
		// falta fecha

		assertThrows(RuntimeException.class, () -> {
			productcostHistoricService.savePHC(prcH, 1);
		});

	}

	// que el producto no exista

	// ---------Pruebas de productInventory------------

	// SAVE PRODUCT INVENTORY
	@Test
	public void testSave1ProductInventory() {

		Productinventory prInvetory = null;

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.savePIR(prInvetory, null, null);
		});

	}

	// cantidad inferior a cero
	@Test
	public void testSave2ProductInventory() {

		Productinventory prInvetory = new Productinventory();

		prInvetory.setId(1);
		// tomar que location y product exitan en el setup
		prInvetory.setQuantity(0);

		assertThrows(RuntimeException.class, () -> {
			productinventoryService.savePIR(prInvetory, 1, 1);
		});

	}

	// no exista la ubi

	// no exista un producto

}
