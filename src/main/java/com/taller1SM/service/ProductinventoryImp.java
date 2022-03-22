package com.taller1SM.service;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcategory;
import com.taller1SM.model.prod.Productinventory;
import com.taller1SM.repositories.LocationRepository;
import com.taller1SM.repositories.ProductRepository;
import com.taller1SM.repositories.ProductinventoryRepository;

public class ProductinventoryImp implements ProductinventoryService {
	
	private ProductinventoryRepository productinventoryRepository;
	private ProductRepository productRepository;
	
	
	private LocationRepository locationRepository;
	
	public ProductinventoryImp(ProductinventoryRepository productinventoryRepositoryLocationRepository, ProductRepository productRepository,LocationRepository  locationRepository) {
		
		
		this.productinventoryRepository=productinventoryRepository;
		this.productRepository=productRepository;
		this.locationRepository=locationRepository;
	}
	
	public void savePIR(Productinventory productinventory, Integer productId, Integer locationId ) {
		
		Optional<Product> product= productRepository.findById(productId);
		Optional<Location> location= locationRepository.findById(locationId);
		
		
		
		if(productinventory==null) {
			throw new RuntimeException("no valid");
		} else if(product.isEmpty()) {

			
		throw new RuntimeException("no valid");

			
		}else if(location.isEmpty()) {
			throw new RuntimeException("no valid");
		}else if(productinventory.getQuantity()>=0) {
			throw new IllegalArgumentException("Cantidad mayor a 0");
			
		}else {
	
			productinventory.setProduct(product.get());
			productinventory.setLocation(location.get());
		productinventoryRepository.save(productinventory);
		
		}
		
		
		
		
		
		
	}
	
	
	
	public void editPIR(Productinventory productinventory, Integer productId, Integer locationId) {
		
		Optional<Product> product= productRepository.findById(productId);
		Optional<Location> location= locationRepository.findById(locationId);
		
		

		if(productinventory==null) {
			throw new RuntimeException("no valid");
		} else {
			
			Optional<Productinventory> productInventoryOptional = productinventoryRepository.findById(productinventory.getId());
			
	if(product.isEmpty()) {

			
		throw new RuntimeException("no valid");

			
		}else if(location.isEmpty()) {
			throw new RuntimeException("no valid");
		}else if(productinventory.getQuantity()>=0) {
			throw new IllegalArgumentException("Cantidad mayor a 0");
			
		}else {
			
			Productinventory PIRmodified=productInventoryOptional.get();
			PIRmodified.setProduct(product.get());
			PIRmodified.setLocation(location.get());
			PIRmodified.setQuantity(productinventory.getQuantity());
		productinventoryRepository.save(productinventory);
		
		}
	
	}
		
		
		
		
	}// fin metodo
	
	
	
	
	
	
	
	
	
	
	
	
}


