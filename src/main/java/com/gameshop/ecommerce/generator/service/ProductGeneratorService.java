package com.gameshop.ecommerce.generator.service;

import com.gameshop.ecommerce.review.store.ReviewEntity;
import com.gameshop.ecommerce.user.store.LocalUserRepository;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import com.gameshop.ecommerce.product.dao.BrandDAO;
import com.gameshop.ecommerce.product.dao.CategoryDAO;
import com.gameshop.ecommerce.product.dao.ProductDAO;
import com.gameshop.ecommerce.product.model.Brand;
import com.gameshop.ecommerce.product.model.Category;
import com.gameshop.ecommerce.product.model.Inventory;
import com.gameshop.ecommerce.product.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gameshop.ecommerce.utils.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductGeneratorService {

    private final ProductDAO productDAO;
    private final BrandDAO brandDAO;
    private final CategoryDAO categoryDAO;
    private final LocalUserRepository localUserRepository;

    List<Brand> brandsList = new ArrayList<>();
    List<Category> categoriesList = new ArrayList<>();
    List<Product> products = new ArrayList<>();

    public void generateProducts() {
        long time = System.currentTimeMillis();

        Faker faker = new Faker();
        if (brandsList.size() <= 1) {
            addBrandAndCategory();
        }
        for (int i = 0; i < 1000; i++) {
            Product product = createProduct(faker);
            product.updateAvgRate();
            products.add(product);
        }
        productDAO.saveAll(products);
        products.clear();

        long time2 = System.currentTimeMillis();
        long duration = time2 - time;
        log.info("Products generated in {} ms", duration);

    }


    protected Product createProduct(Faker faker) {
        Product product = new Product();
        product.setName(faker.commerce().productName());
        product.setPrice(faker.number().numberBetween(50, 1000));
        product.setShortDescription((faker.lorem().characters(50, 100)));
        product.setCategory(categoriesList.get(faker.number().numberBetween(0, 6)));
        product.setImageUrl(setImageForCategory(product));
        product.setBrand(brandsList.get(faker.number().numberBetween(0, 6)));
        product.setCharacteristics(createCharacteristics(faker, product.getCategory().getName()));
        product.setReviewEntities(generateReviews(faker, product));
        Inventory inventory = createInventory(faker, product);
        product.setInventory(inventory);
        return product;
    }

    private String setImageForCategory(Product product) {
        switch (product.getCategory().getName()) {
            case "Mice" -> {
                return images.get("Mouse");
            }
            case "Keyboards" -> {
                return images.get("Keyboard");
            }
            case "Joysticks and controllers" -> {
                return images.get("Joystick");
            }
            case "Headsets" -> {
                return images.get("Headset");
            }
            default -> {
                return images.get("def");
            }
        }
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

    private Map<String, String> createCharacteristics(Faker faker, String category) {
        Map<String, String> characteristics = new HashMap<>();

        switch (category) {
            case "Mice":
                characteristics.put("DPI", String.valueOf(faker.options().option(800, 1600, 2200, 600, 1200)));
                characteristics.put("Buttons", String.valueOf(faker.number().numberBetween(2, 8)));
                characteristics.put("Interface", faker.options().option("USB", "Bluetooth", "2,4 GHz"));
                characteristics.put("Color", faker.options().option("Red", "White", "Black", "Blue", "Yellow"));
                break;
            case "Keyboards":
                characteristics.put("Layout", faker.options().option("QWERTY", "AZERTY", "QWERTZ"));
                characteristics.put("Type", faker.options().option("Hybrid mechanical-membrane", "Optical-mechanical",
                        "Scissors", "Mechanical", "Membrane"));
                characteristics.put("Interface", faker.options().option("USB", "Bluetooth", "2,4 GHz"));
                characteristics.put("Size", faker.options().option("100%", "75%", "60%"));
                break;
            case "Headsets":
                characteristics.put("Type", faker.options().option("In-Ear", "On-Ear", "Over-Ear"));
                characteristics.put("Connection type", faker.options().option("Wired", "Wireless", "Combined"));
                characteristics.put("Noise Cancelling", faker.options().option("With noise cancelling",
                        "Without noise cancelling"));
                break;
        }

        return characteristics;
    }

    private Inventory createInventory(Faker faker, Product product) {
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(faker.number().numberBetween(0, 100));
        return inventory;
    }

    public List<ReviewEntity> generateReviews(Faker faker, Product product) {
        List<LocalUserEntity> users = localUserRepository.findAll();
        List<ReviewEntity> reviewEntities = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ReviewEntity reviewEntity = new ReviewEntity();
            reviewEntity.setComment(REVIEW_TEXT);
            reviewEntity.setRate(faker.random().nextInt(3, 5));
            reviewEntity.setLocalUserEntity(users.get(faker.random().nextInt(1, users.size() - 1)));
            reviewEntities.add(reviewEntity);
            reviewEntity.setProduct(product);
        }

        return reviewEntities;
    }
}

