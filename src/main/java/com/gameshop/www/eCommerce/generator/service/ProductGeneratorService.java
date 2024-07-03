package com.gameshop.www.eCommerce.generator.service;

import com.gameshop.www.eCommerce.product.dao.BrandDAO;
import com.gameshop.www.eCommerce.product.dao.CategoryDAO;
import com.gameshop.www.eCommerce.product.dao.ProductDAO;
import com.gameshop.www.eCommerce.product.model.Brand;
import com.gameshop.www.eCommerce.product.model.Category;
import com.gameshop.www.eCommerce.product.model.Inventory;
import com.gameshop.www.eCommerce.product.model.Product;
import com.gameshop.www.eCommerce.review.model.Review;
import com.gameshop.www.eCommerce.user.dao.LocalUserDAO;
import com.gameshop.www.eCommerce.user.model.LocalUser;
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
    private final LocalUserDAO localUserDAO;
    List<String> categories = List.of("Keyboards", "Mice", "Headsets", "Mouse mats", "Joysticks and controllers", "Gaming chairs");
    List<String> brands = List.of("Logitech", "Razer", "Acer", "Asus", "Gigabyte", "MSI");
    Map<String, String> images = new HashMap<>(Map.of("Mouse", "https://s3-alpha-sig.figma.com/img/bb97/c661/c58d8770f78ef73d41318729cc66f445?Expires=1719187200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=liIDVJI36n3Yb97jZ6-wu5Ipt4q-LFC~q1oyQASMu94EAgtQ6JgYXYPsoXHIFYnCsFzoZfY6VcIw52V-FV5IWSEYu4POu3tpkCOj0HIof92GfY7ODalUYic3iiDnpSUADb11ScMMlQsvgn9L5YSdSUjX86X8eyqzrTU9j6kIBN9ugCa4Hd7hodOGrjg~PtzN6kDKn72xHlz6XNjyBTIkSZhCHFgqOe9F-GlWAggpF24BqHwacG6oVRYmwBpOdVZwEjh0MmsyXhqK6uMJ4FTzvZmUy6M6LhxL9K-kjmkk3yE7b-cmNu0ZFdSIt-uBzNFgjlYOpd~n6ars8fxMIbtRPw__",
            "Keyboard", "https://s3-alpha-sig.figma.com/img/001a/c491/8073e1bae39c39e70ba49cc90495dd28?Expires=1719187200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=VuRSv~ljDyQ9xlfJOaSso5F8MOP5z~TU1fgjIBhpg9CN51aPvpgJqt2UjaTQgwsX3s2XLKFo0xIcIAF6xfjvmsQVzeCa7FGeNv1Ie7NFFbICsR1i~LcVqI6aEsr0RR~pGf-9JmtdbbA3H9QJnpaVVnGh2S~q4MfLG4~XWCY5mBfzWla1yZeZWqAJZGj5eFQc-8rjXtsB2kQ1W44uvHt8OYWiZmJAUOEL~CSfT4ZJjvlnY1QZvUd4GUEJ7r7bKOtdFOW4mIK0ON0I0eyAcTd0I5B1uvPyhUEvGPIc6oovHn9CLaCUE7pbMevZWHpZsSxA3Em3iwuU6rPxXKKwnkEnwQ__", "Headset",
            "https://s3-alpha-sig.figma.com/img/1c77/991f/4c035eeed1b2fdf51e19bd56b7898560?Expires=1719187200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=FFgL~VV2JhyoK5Dgkyr1~fdzYoylET~o1JvR5opAVmkZINpHVB3Y6KkGyqDZNMzpxCI0QwwtbFfV0fGYA-oY3GTwNd88gqhjjyO~~C9xJOCNQFNFtIO99NfHDUTArFQNFsrMwVb1b3xJyF6cm~7Ynvzl9GVvT8COuYqghxJwjG0ujnPMrK-6wbdV7CVvZ9oRJn74Kd7fNjohJdQ8o64hjphX7D5PXpLJfDgAFAqoa556EMeqbWGA~ZvML72MaORzXwsL606qHv~VJB8Nj2ksPc2PdkffgZta7o1j39rplK2zAq6cH~Mwm-nzFluQlm3UlAXtOfdH2IkH-w0PPw9qFA__", "Joystick",
            "https://s3-alpha-sig.figma.com/img/0eff/4dc0/d737f393fa2acc3ff0f97c58eed44f62?Expires=1719187200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=gT7xsvKHCJBDZzkmohFNXYQ0T7WHKdBxCxjndzc4oZ901OFJbxmh3qd~9fEXD7RokytTwYo4p0Hoj2kl1OqzyoYKoDbn4e3KclTXN14MoltnKG0friEtni-fS1U-6JJf8x4rfItXHC2W2Mfc-Ismn97klfJ1bgJUA1gleT18IGUGYU97tLpC5w4XRgOQUF~O7MSsGrpSZySYWPjbar8JGOrmcCXNM5OgAq1imkXvI9C6JFlWDvlJ-PG-ebIs~waReWLz5XW8AOxRqjKkeSPIAD69PKvhA-k4R0mguI0CCQdz3u7ivUCzApg7h3759--W3R0yIpLsUxmJFjqWKrmnfA__",
            "def", "https://static.tildacdn.com/tild6237-6265-4232-a233-663832313834/noroot.png"));
    List<Brand> brandsList = new ArrayList<>();
    List<Category> categoriesList = new ArrayList<>();
    List<Product> products = new ArrayList<>();

    public ProductGeneratorService(ProductDAO productDAO, BrandDAO brandDAO, CategoryDAO categoryDAO,
                                   LocalUserDAO localUserDAO) {
        this.productDAO = productDAO;
        this.brandDAO = brandDAO;
        this.categoryDAO = categoryDAO;
        this.localUserDAO = localUserDAO;
    }

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
            review.setLocalUser(users.get(faker.random().nextInt(1, users.size() - 1)));
            reviews.add(review);
            review.setProduct(product);
        }
        return reviews;
    }
}
