package com.taller1SM.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.taller1SM.model.prod.Location;
import com.taller1SM.model.prod.Product;
import com.taller1SM.model.prod.Productcategory;
import com.taller1SM.model.prod.Productinventory;
import com.taller1SM.model.prod.Productsubcategory;
import com.taller1SM.repositories.ProductRepository;
import com.taller1SM.repositories.ProductSubcategoryRepository;
import com.taller1SM.repositories.ProductcategoryRepository;

public class ProductServiceImp implements ProductService {

	// relaciones
	private ProductRepository productRepository;

	private ProductcategoryRepository productcategoryRepository;

	private ProductSubcategoryRepository productSubcategoryRepository;

	// constructor

	public ProductServiceImp(ProductRepository productRepository, ProductcategoryRepository productcategoryRepository,
			ProductSubcategoryRepository productSubcategoryRepository) {

		this.productRepository = productRepository;
		this.productcategoryRepository = productcategoryRepository;
		this.productSubcategoryRepository = productSubcategoryRepository;
	}

	// servicio de guardar producto
	@Override
	public void saveProduct(Product product, Integer prCategoryId, Integer prSubcategoryId) {

		Optional<Productcategory> productcategory = productcategoryRepository.findById(prCategoryId);

		Optional<Productsubcategory> productsubcategory = productSubcategoryRepository.findById(prSubcategoryId);

		if (product == null) {
			throw new RuntimeException("no valid");

		} else if (!productRepository.findById(product.getProductid()).isEmpty()) {
			throw new IllegalArgumentException("el id de producto ya existe ");
		} else if (productcategory.isEmpty()) {
			throw new RuntimeException("no valid");
		} else if (productsubcategory.isEmpty()) {
			throw new RuntimeException("no valid");
		} else if (product.getProductnumber().isEmpty()) {
			throw new IllegalArgumentException("el numero de producto esta vacio");

		} else if (product.getSellstartdate().after(product.getSellenddate())) {
			throw new IllegalArgumentException("La fecha de inicio de venta debe ser menor a la fecha de fin");
		} else if (product.getWeight() < 0 || product.getSize() < 0) {
			throw new IllegalArgumentException("Peso mayor a 0");
		} else if (productRepository.existsById(product.getProductid())) {
			throw new IllegalArgumentException("el id de producto ya existe");
		} else {
			productsubcategory.get().setProductcategory(productcategory.get());
			product.setProductsubcategory(productsubcategory.get());

			productRepository.save(product);
		}

		// servicio de editar producto

	}// fin metodo

	@Override
	public void editProduct(Product product, Integer prCategoryId, Integer prSubcategoryId) {
		Optional<Productcategory> productcategory = productcategoryRepository.findById(prCategoryId);

		Optional<Productsubcategory> productsubcategory = productSubcategoryRepository.findById(prSubcategoryId);

		if (product == null) {
			throw new RuntimeException();
		} else {
			Optional<Product> prod = productRepository.findById(product.getProductid());

			if (productRepository.findById(product.getProductid()).isEmpty()) {
				throw new IllegalArgumentException("el id de producto ya existe ");
			} else if (productcategory.isEmpty()) {

				throw new RuntimeException("La categoria del producto esta vacia");

			} else if (productsubcategory.isEmpty()) {
				throw new RuntimeException("Sub categorie esta vacia ");

			} else if (product.getProductnumber().isEmpty()) {
				throw new IllegalArgumentException("the proudct nunber no tbe empty");

			} else if (product.getSellstartdate().after(product.getSellenddate())) {
				throw new IllegalArgumentException("La fecha de inicio de venta debe ser menor a la fecha de fin");

			} else if (product.getWeight() < 0 || product.getSize() < 0) {
				throw new IllegalArgumentException("Peso mayor a 0 , Tamaño mayor a 0");

			} else {
				Product productModified = prod.get();
				productModified.setProductsubcategory(productsubcategory.get());
				productModified.getProductsubcategory().setProductcategory(productcategory.get());
				productModified.setName(product.getName());
				productModified.setProductnumber(product.getProductnumber());
				productModified.setSellstartdate(product.getSellstartdate());
				productModified.setSellenddate(product.getSellenddate());
				productModified.setWeight(product.getWeight());
				productModified.setSize(product.getSize());
				productRepository.save(productModified);
			}
		}

	}

}
