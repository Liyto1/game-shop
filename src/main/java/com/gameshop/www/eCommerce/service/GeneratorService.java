package com.gameshop.www.eCommerce.service;

import com.gameshop.www.eCommerce.dao.BrandDAO;
import com.gameshop.www.eCommerce.dao.CategoryDAO;
import com.gameshop.www.eCommerce.dao.ProductDAO;
import com.gameshop.www.eCommerce.model.Brand;
import com.gameshop.www.eCommerce.model.Category;
import com.gameshop.www.eCommerce.model.Product;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratorService {


    private Faker faker = new Faker();
    private ProductDAO productDAO;
    private BrandDAO brandDAO;
    private CategoryDAO categoryDAO;

    public GeneratorService(ProductDAO productDAO, BrandDAO brandDAO, CategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.brandDAO = brandDAO;
        this.categoryDAO = categoryDAO;
    }

    private List<String> categories = List.of("Keyboard", "Mouse", "Headset", "Mouse Pad", "Joystick", "Gaming chairs");
    private List<String> brands = List.of("Logitech", "Razer", "Acer", "Asus", "Gigabyte", "MSI");
    private int a = 0;


    public void generateProducts() {
        for (int i = 0; i < 6; i++) {
            Category category = new Category();
            Brand brand = new Brand();
            category.setName(categories.get(i));
            brand.setName(brands.get(i));
            brandDAO.save(brand);
            categoryDAO.save(category);
        }
        for (int i = 0; i < 100; i++) {
            a = (int) (Math.random() * 6);
            Product product = new Product();
            product.setName(faker.commerce().productName());
            product.setPrice(faker.number().randomDouble(2, 10, 100));
            product.setShortDescription((faker.lorem().paragraph()));
            product.setImageUrl(faker.company().url());
            Brand randomBrand = brandDAO.findByNameIgnoreCase(brands.get(a));
            product.setBrand(randomBrand);
            Category randomCategory = categoryDAO.findByNameIgnoreCase(categories.get(a));
            product.setCategory(randomCategory);
            productDAO.save(product);
        }
    }
}
