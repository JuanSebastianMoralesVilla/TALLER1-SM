package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
	/*
	 * public void setup1Product() { Product pr = new Product();
	 * 
	 * Productcategory productCategory = new Productcategory(); Productsubcategory
	 * productSubCategory = new Productsubcategory(); pr.setProductid(1);
	 * 
	 * productCategory.setProductcategoryid(1);
	 * productSubCategory.setProductsubcategoryid(1);
	 * 
	 * 
	 * 
	 * 
	 * }
	 **/

	public Product setup1Pr() {
		Product pr = new Product();
		pr.setProductid(1);
		pr.setWeight((long) 3);
		pr.setSize((long) 10);
		pr.setProductnumber("0001");
		pr.setSellenddate(new Timestamp(System.currentTimeMillis() + (60 * 60 * 24)));
		pr.setSellstartdate(new Timestamp(System.currentTimeMillis()));
		return pr;

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
	public void testSaveProductNull() {

		Product pr = null;
		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, null, null);
		});
	}

	// Agregar un producto valido
	@Test
	public void testSaveProductFull() {
		Product pr = new Product();
		Productcategory cr = new Productcategory();
		Productsubcategory prsub = new Productsubcategory();
		pr.setName("iphone");
		pr.setWeight((long) 1);
		pr.setSize((long) 10);
		// 26/04/2022
		// end > mayor

		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(cr));
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));
		pr.setSellenddate(new Timestamp(System.currentTimeMillis() + (60 * 60 * 24)));
		pr.setSellstartdate(new Timestamp(System.currentTimeMillis()));
		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// Agregar un producto con tamaño -1

	@Test
	public void testSaveProductSizeInvalid() {
		Product pr = new Product();
		Productcategory cr = new Productcategory();
		Productsubcategory prsub = new Productsubcategory();
		pr.setName("iphone");
		pr.setWeight((long) 1);
		pr.setSize((long) -1);
		pr.setSellenddate(new Timestamp(System.currentTimeMillis() + (60 * 60 * 24)));
		pr.setSellstartdate(new Timestamp(System.currentTimeMillis()));

		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(cr));
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));
		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// Agregar un producto con peso < 0

	@Test
	public void testSaveProductWeigthInvalid() {
		Product pr = new Product();
		Productcategory cr = new Productcategory();
		Productsubcategory prsub = new Productsubcategory();
		pr.setName("iphone");
		pr.setProductnumber("1111");
		pr.setWeight((long) -1);
		pr.setSize((long) 10);
		pr.setSellenddate(new Timestamp(System.currentTimeMillis() + (60 * 60 * 24)));
		pr.setSellstartdate(new Timestamp(System.currentTimeMillis()));
		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(cr));
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));
		assertThrows(IllegalArgumentException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// validar fecha de inicio > a la de end

	@Test
	public void testSaveProductDate() {
		Product pr = new Product();
		Productcategory cr = new Productcategory();
		Productsubcategory prsub = new Productsubcategory();

		pr.setName("iphone");
		pr.setWeight((long) 1);
		pr.setSize((long) 10);
		pr.setProductnumber("00001");
		// 26/04/2022
		// star > mayor asi no funciona

		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(cr));
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		pr.setSellenddate(new Timestamp(System.currentTimeMillis()));
		pr.setSellstartdate(new Timestamp(System.currentTimeMillis() + (60 * 60 * 24)));
		assertThrows(IllegalArgumentException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	
	// producto con nombre nulo
	@Test
	public void testSaveProductNameNull() {
		Product pr = new Product();
		Productcategory cr = new Productcategory();
		Productsubcategory prsub = new Productsubcategory();

		pr.setName("iphone");
		pr.setWeight((long) 1);
		pr.setSize((long) 10);
		pr.setProductnumber("");
		// 26/04/2022
		// star > mayor asi no funciona

		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(cr));
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		pr.setSellenddate(new Timestamp(System.currentTimeMillis()));
		pr.setSellstartdate(new Timestamp(System.currentTimeMillis() + (60 * 60 * 24)));
		assertThrows(IllegalArgumentException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// prueba para producto con subcategoria inexistente

	@Test
	public void testSaveProductInexistentSubCategory() {
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		prsub.setProductsubcategoryid(1);
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, 1, null);
		});
	}

	// prueba para producto con categoria inexistente
	@Test
	public void testSaveProductInexistentCategory() {
		Product pr = setup1Pr();
		Productcategory prct = new Productcategory();
		prct.setProductcategoryid(1);
		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prct));

		assertThrows(RuntimeException.class, () -> {
			productService.saveProduct(pr, null, 1);
		});
	}

	// prueba para producto con id iguales
	@Test
	public void testSaveProductIdequals() {
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();

		when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prc));
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(IllegalArgumentException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}

	// prueba con category inexistente 
	
	@Test
	public void testSaveProductcategoryInexist() {
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();

		when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		when(productcategoryRepository.findById(1)).thenReturn(Optional.empty());
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(IllegalArgumentException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}
	
	
	// prueba con subcategory Inexistent
	
	
	@Test
	public void testSaveProductsubategoryInexist() {
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();

		when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prc));
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> {
			productService.saveProduct(pr, 1, 1);
		});
	}
	
	
	// EDIT PRODUCT 
	
	@Test
	public void productEditTestInvalidProductNumber() {
	
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();
		pr.setProductnumber("");
		Integer id = 1;
		
		
		
		when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prc));
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));
		
		assertThrows(IllegalArgumentException.class, () -> {
			productService.editProduct(pr, id, id);
		});
	}
	
	@Test
	public void productEditTestInvalidDate() {
	
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();
		pr.setSellenddate(new Timestamp(System.currentTimeMillis()));
		pr.setSellstartdate(new Timestamp(System.currentTimeMillis() + (60*60*24)));
		Integer id = 1;
		
		
		
		when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prc));
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));
		
		assertThrows(IllegalArgumentException.class, () -> {
			productService.editProduct(pr, id, id);
		});
	}
	@Test
	public void productEditTestInvalidsize() {
	
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();
		pr.setSize(-10);
		Integer id = 1;
		
		
		
		when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prc));
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));
		
		assertThrows(IllegalArgumentException.class, () -> {
			productService.editProduct(pr, id, id);
		});
	}
	
	@Test
	public void productEditTestInvalidWeigth() {
	
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();
		pr.setWeight(-10);
		Integer id = 1;
		
		
		
		when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(prc));
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));
		
		assertThrows(IllegalArgumentException.class, () -> {
			productService.editProduct(pr, id, id);
		});
	}
	
	@Test
	public void testEditProductcategoryInexist() {
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();
		
		Integer id = 1;
		
		when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		when(productcategoryRepository.findById(1)).thenReturn(Optional.empty());
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(RuntimeException.class, () -> {
			productService.editProduct(pr, id, id);
		});
	}
	
	
	@Test
	public void testEditProductsubcategoryInexist() {
		Product pr = setup1Pr();
		Productsubcategory prsub = new Productsubcategory();
		Productcategory prc = new Productcategory();
		
		Integer id = 1;
		
		when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		when(productcategoryRepository.findById(1)).thenReturn(Optional.empty());
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		assertThrows(RuntimeException.class, () -> {
			productService.editProduct(pr, id, id);
		});
	}
	
	
	@Test
	public void testEditProductNull() {
		Product pr = null;
		assertThrows(RuntimeException.class, () -> {
			productService.editProduct(pr, null, null);
		});
		
	}
	@Test
	public void testEditProductFull() {
		Product pr = setup1Pr();
		Productcategory cr = new Productcategory();

		Productsubcategory prsub = new Productsubcategory();
	

		when(productRepository.findById(1)).thenReturn(Optional.of(pr));
		when(productcategoryRepository.findById(1)).thenReturn(Optional.of(cr));
		when(productSubcategoryRepository.findById(1)).thenReturn(Optional.of(prsub));

		
		assertDoesNotThrow(() -> {
			productService.editProduct(pr, 1, 1);
		});
		
	}
	
	
	// ---------Pruebas de location------------

	// SAVE LOCATION

	@Test
	public void testSaveLocationNull() {
		Location l = null;

		assertThrows(RuntimeException.class, () -> {
			locationService.saveLocation(l);
		});

	}

	// localizacion guardada bien

	@Test
	public void testSaveLocationFull() {
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

	@Test
	public void testSaveLocationInvalidName() {

		Location loc2 = new Location();
		loc2.setLocationid(1);
		loc2.setName("1234");
		loc2.setAvailability(new BigDecimal(-1));
		loc2.setCostrate(new BigDecimal(1));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}

	// disponibilidad menor a 1
	@Test
	public void testSaveLocationInvalidAvailability() {

		Location loc2 = new Location();
		loc2.setLocationid(1);
		loc2.setName("12345");
		loc2.setAvailability(new BigDecimal(-1));
		loc2.setCostrate(new BigDecimal(1));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}
	// disponibilidad myor a 10
		@Test
		public void testSaveLocationInvalidAvailabilityMajor1() {

			Location loc2 = new Location();
			loc2.setLocationid(1);
			loc2.setName("12345");
			loc2.setAvailability(new BigDecimal(11));
			loc2.setCostrate(new BigDecimal(1));

			assertThrows(IllegalArgumentException.class, () -> {
				locationService.saveLocation(loc2);
			});

		}
		// disponibilidad null
	@Test
	public void testSaveLocationNullAvailability() {

		Location loc2 = new Location();
		loc2.setLocationid(1);
		loc2.setName("12345");
		loc2.setAvailability(null);
		loc2.setCostrate(new BigDecimal(1));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}

	// tasa de costo mayor a 1
	@Test
	public void testSaveLocationInvalidCostRate() {

		Location loc2 = new Location();
		loc2.setLocationid(1);
		loc2.setName("12345");
		loc2.setAvailability(new BigDecimal(10));
		loc2.setCostrate(new BigDecimal(3));

		assertThrows(IllegalArgumentException.class, () -> {
			locationService.saveLocation(loc2);
		});

	}
	// tasa de costo menor a 1
		@Test
		public void testSaveLocationInvalidCostRateMinor() {

			Location loc2 = new Location();
			loc2.setLocationid(1);
			loc2.setName("12345");
			loc2.setAvailability(new BigDecimal(10));
			loc2.setCostrate(new BigDecimal(-1));

			assertThrows(IllegalArgumentException.class, () -> {
				locationService.saveLocation(loc2);
			});

		}
		// tasa de costo menor a 1
		@Test
		public void testSaveLocationInvalidCostRateNull() {

			Location loc2 = new Location();
			loc2.setLocationid(1);
			loc2.setName("12345");
			loc2.setAvailability(new BigDecimal(10));
			loc2.setCostrate(null);

			assertThrows(IllegalArgumentException.class, () -> {
				locationService.saveLocation(loc2);
			});

		}

// location id ocupado en objeto 
		public Location LocationSetup1() {
			Location loc = new Location();
			loc.setLocationid(1);
			loc.setName("12345");
			loc.setAvailability(new BigDecimal(10));
			loc.setCostrate(new BigDecimal(1));
			return loc;
		}
		@Test
		public void testSaveduplicateid() {
			
			Location loc = LocationSetup1();
	
			
			//when(locationRepository.findById(1)).thenReturn(Optional.of(loc));
			
			
			assertThrows(IllegalArgumentException.class, () -> {
				locationService.saveLocation(loc);
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
