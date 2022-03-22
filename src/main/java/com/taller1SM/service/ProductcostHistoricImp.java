package com.taller1SM.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcosthistory;
import com.taller1SM.repositories.ProductRepository;
import com.taller1SM.repositories.ProductcosthistoryRepository;

public class ProductcostHistoricImp implements ProductcosthistoryService {

	private ProductRepository productRepository;
	private ProductcosthistoryRepository productcosthistoryRepository;

	public ProductcostHistoricImp(ProductcosthistoryRepository productcosthistoryRepository,
			ProductRepository productRepository) {
		this.productRepository = productRepository;
		this.productcosthistoryRepository = productcosthistoryRepository;

	}
    public void savePHC(Productcosthistory productcosthistory, Integer productId) {

		Optional<Product> product = productRepository.findById(productId);

		if (product.isEmpty()) {
			throw new RuntimeException();
		} else if (productcosthistory == null) {
			throw new RuntimeException();
		} else if (product.get().getSellstartdate().after(product.get().getSellenddate())) {
			throw new IllegalArgumentException("La fecha de inicio de venta debe ser menor a la fecha de fin");
		} else if (productcosthistory.getStandardcost().compareTo(new BigDecimal("0")) == -1) {
			throw new IllegalArgumentException("costo estandar no debe ser negativo");
		}

		productcosthistory.setProduct(product.get());
		productcosthistoryRepository.save(productcosthistory);

	}
public void editPHC(Productcosthistory productcosthistory, Integer productId) {
		Optional<Product> product = productRepository.findById(productId);

		if (productcosthistory == null) {
			throw new RuntimeException();

		} else {

			Optional<Productcosthistory> pcostHistoric = productcosthistoryRepository
					.findById(productcosthistory.getId());
// se cambio la productcosthistoricPK a integer
			if (product.isEmpty()) {
				throw new RuntimeException();
			}
			if (pcostHistoric.isEmpty()) {
				throw new RuntimeException();
			} else if (product.get().getSellstartdate().after(product.get().getSellenddate())) {
				throw new IllegalArgumentException("La fecha de inicio de venta debe ser menor a la fecha de fin");
			} else if (pcostHistoric.get().getStandardcost().compareTo(new BigDecimal("0")) == -1) {
				throw new IllegalArgumentException("costo estandar no debe ser negativo");
			} else {
				Productcosthistory productcosthistorymodified = pcostHistoric.get();

				productcosthistorymodified.setId(productcosthistory.getId());
				productcosthistorymodified.setStandardcost(productcosthistory.getStandardcost());
				productcosthistorymodified.setEnddate(productcosthistory.getEnddate());
				productcosthistorymodified.setModifieddate(productcosthistorymodified.getModifieddate());
				productcosthistorymodified.setProduct(product.get());

				productcosthistoryRepository.save(productcosthistorymodified);

			}

		}
	}

}
