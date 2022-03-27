package com.taller1SM.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcategory;
import com.taller1SM.model.prod.Productinventory;
import com.taller1SM.model.prod.Productsubcategory;
import com.taller1SM.repositories.LocationRepository;
import com.taller1SM.repositories.ProductRepository;
import com.taller1SM.repositories.ProductSubcategoryRepository;
import com.taller1SM.repositories.ProductcategoryRepository;

public class LocationServiceImp implements LocationService{

	// nombre al menos 5 caracteres, disponibilidad 1 y 10, tasa de costo 0 y 1

	private LocationRepository locationRepository;

	public LocationServiceImp(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;

	}
@Override
	public void saveLocation(Location location) {
		if (location == null) {
			throw new RuntimeException();
		}else if(!locationRepository.findById(location.getLocationid()).isEmpty()) {
			throw new IllegalArgumentException("el id de location ya existe ");
		
		} else if (location.getName().length() >= 5) {
			throw new IllegalArgumentException("El nombre debe tener 5 o mas caracteres");
		} else if (location.getAvailability().compareTo(new BigDecimal("1")) == -1
				|| (location.getAvailability().compareTo(new BigDecimal("10")) == 1)) {
			throw new IllegalArgumentException("La disponibilidad debe ser un valor entre 1 y 10 ");
		} else if (location.getCostrate().compareTo(new BigDecimal("0")) == -1
				|| (location.getCostrate().compareTo(new BigDecimal("1")) == 1)) {
			throw new IllegalArgumentException("La debe ser un valor entre 1 y 0");
		} else {
			locationRepository.save(location);

		}

	}



public void editLocation(Location location) {

		if (location == null) {
			throw new RuntimeException();
		} else {
			Optional<Location> loc = locationRepository.findById(location.getLocationid());

			if (location == null) {
				throw new RuntimeException();
			} else if (location.getName().length() < 5) {
				throw new IllegalArgumentException("El nombre debe tener 5 o mas caracteres");
			} else if (location.getAvailability().compareTo(new BigDecimal("1")) == -1
					|| (location.getAvailability().compareTo(new BigDecimal("10")) == 1)) {
				throw new IllegalArgumentException("La disponibilidad debe ser un valor entre 1 y 10 ");
			} else if (location.getCostrate().compareTo(new BigDecimal("0")) == -1
					|| (location.getCostrate().compareTo(new BigDecimal("1")) == 1)) {
				throw new IllegalArgumentException("La debe ser un valor entre 1 y 0");
			} else {
				Location locationmodified = loc.get();

				locationmodified.setLocationid(location.getLocationid());
				locationmodified.setName(location.getName());
				locationmodified.setAvailability(location.getAvailability());
				locationmodified.setCostrate(location.getCostrate());

				// validr otras opciones.
				locationmodified.setModifieddate(location.getModifieddate());
				locationmodified.setProductinventories(location.getProductinventories());
				locationmodified.setWorkorderroutings(location.getWorkorderroutings());

				locationRepository.save(locationmodified);
			}
		}

	}

}// fin clase
