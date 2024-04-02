package com.gameshop.www.eCommerce.generator.service;

import com.gameshop.www.eCommerce.product.dao.BrandDAO;
import com.gameshop.www.eCommerce.product.dao.CategoryDAO;
import com.gameshop.www.eCommerce.product.dao.ProductDAO;
import com.gameshop.www.eCommerce.product.model.Brand;
import com.gameshop.www.eCommerce.product.model.Category;
import com.gameshop.www.eCommerce.product.model.Inventory;
import com.gameshop.www.eCommerce.product.model.Product;
import com.gameshop.www.eCommerce.review.dao.ReviewDAO;
import com.gameshop.www.eCommerce.review.model.Review;
import com.gameshop.www.eCommerce.user.dao.LocalUserDAO;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductGeneratorService {


    private static final String REVIEW_TEXT = "I've been using the QuantumX Pro for a month now, and I'm blown away by " +
            "its precision and customizable RGB lighting. The ergonomic design is perfect for my long gaming sessions. " +
            "A must-have for serious gamers!";
    private final ProductDAO productDAO;
    private final BrandDAO brandDAO;
    private final CategoryDAO categoryDAO;
    private final ReviewDAO reviewDAO;

    List<String> categories = List.of("Keyboards", "Mouses", "Headsets", "Mouse mats", "Joysticks and controllers", "Gaming chairs");
    List<String> brands = List.of("Logitech", "Razer", "Acer", "Asus", "Gigabyte", "MSI");
    Map<String, String> images = new HashMap<>(Map.of("Mouse", "http://surl.li/sbdfp", "Keyboard", "http://surl.li/sbdgb", "Headset",
            "http://surl.li/sbebq", "Joystick", "http://surl.li/sbebu", "def", "http://surl.li/jvxbt"));
    List<Brand> brandsList = new ArrayList<>();
    List<Category> categoriesList = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    private final LocalUserDAO localUserDAO;

    public ProductGeneratorService(ProductDAO productDAO, BrandDAO brandDAO, CategoryDAO categoryDAO, ReviewDAO reviewDAO,
                                   LocalUserDAO localUserDAO) {
        this.productDAO = productDAO;
        this.brandDAO = brandDAO;
        this.categoryDAO = categoryDAO;
        this.reviewDAO = reviewDAO;
        this.localUserDAO = localUserDAO;
    }

    public void generateProducts() {
        long time = System.currentTimeMillis();

        Faker faker = new Faker();
        if (!(brandsList.size() > 1)) {
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

    @Transactional
    protected Product createProduct(Faker faker) {
        Product product = new Product();
        product.setName(faker.commerce().productName());
        product.setPrice(faker.number().numberBetween(50, 1000));
        product.setShortDescription((faker.lorem().characters(50, 100)));
        product.setCategory(categoriesList.get(faker.number().numberBetween(0, 6)));
        product.setImageUrl(setImageForCategory(product));
        product.setBrand(brandsList.get(faker.number().numberBetween(0, 6)));
        product.setCharacteristics(createCharacteristics(faker, product.getCategory().getName()));
        product.setReviews(generateReviews(faker, product));
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
            case "Headset" -> {
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
                characteristics.put("Type", faker.options().option("Hybrid mechanical-membrane", "Optical-mechanical", "Scissors", "Mechanical", "Membrane"));
                characteristics.put("Interface", faker.options().option("USB", "Bluetooth", "2,4 GHz"));
                characteristics.put("Size", faker.options().option("100%", "75%", "60%"));
                break;
            case "Headsets":
                characteristics.put("Type", faker.options().option("In-Ear", "On-Ear", "Over-Ear"));
                characteristics.put("Connection type", faker.options().option("Wired", "Wireless", "Combined"));
                characteristics.put("Noise Cancelling", faker.options().option("With noise cancelling", "Without noise cancelling"));
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

    public List<Review> generateReviews(Faker faker, Product product) {
        List<LocalUser> users = localUserDAO.findAll();
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Review review = new Review();
            review.setComment(REVIEW_TEXT);
            review.setRate(faker.random().nextInt(3, 5));
            review.setLocalUser(users.get(faker.random().nextInt(1, users.size())));
            reviews.add(review);
            review.setProduct(product);
        }
        return reviews;
    }
}
