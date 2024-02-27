package com.gameshop.www.eCommerce.product.service;

import com.gameshop.www.eCommerce.product.dao.BrandDAO;
import com.gameshop.www.eCommerce.product.dao.CategoryDAO;
import com.gameshop.www.eCommerce.product.dao.ProductDAO;
import com.gameshop.www.eCommerce.product.model.Brand;
import com.gameshop.www.eCommerce.product.model.Category;
import com.gameshop.www.eCommerce.product.model.Product;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneratorService {


    List<String> categories = List.of("Keyboard", "Mouse", "Headset", "Pad", "Joystick", "Gaming chairs");
    List<String> brands = List.of("Logitech", "Razer", "Acer", "Asus", "Gigabyte", "MSI");
    List<Brand> brandsList = new ArrayList<>();
    List<Category> categoriesList = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    private ProductDAO productDAO;
    private BrandDAO brandDAO;
    private CategoryDAO categoryDAO;

    public GeneratorService(ProductDAO productDAO, BrandDAO brandDAO, CategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.brandDAO = brandDAO;
        this.categoryDAO = categoryDAO;
    }

    public void generateProducts() {
        long time = System.currentTimeMillis();

        Faker faker = new Faker();
        if (!(brandsList.size() > 1)) {
            addBrandAndCategory();
        }
        for (int i = 0; i < 1000; i++) {
            Product product = createProduct(faker);
            products.add(product);
        }
        productDAO.saveAll(products);
        products.clear();

        long time2 = System.currentTimeMillis();
        long duration = time2 - time;
        System.out.println("Duration: " + duration);
    }

    private Product createProduct(Faker faker) {
        Product product = new Product();
        product.setName(faker.commerce().productName());
        product.setPrice(faker.number().numberBetween(50, 1000));
        product.setShortDescription((faker.lorem().characters(50, 100)));
        product.setImageUrl(faker.company().url());
        product.setBrand(brandsList.get(faker.number().numberBetween(0, 6)));
        product.setCategory(categoriesList.get(faker.number().numberBetween(0, 6)));
        return product;
    }

    private Brand createBrand(int i) {
        Brand brand = new Brand();
        brand.setName(brands.get(i));
        return brand;
    }

    private Category createCategory(int i) {
        Category category = new Category();
        category.setName(categories.get(i));
        return category;

    }

    private void addBrandAndCategory() {
        for (int i = 0; i < brands.size(); i++) {
            Brand brand = createBrand(i);
            brandsList.add(brand);
            Category category = createCategory(i);
            categoriesList.add(category);
        }
        brandDAO.saveAll(brandsList);
        categoryDAO.saveAll(categoriesList);

    }
}
