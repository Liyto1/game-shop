package com.gameshop.www.eCommerce.product.search;

import org.hibernate.search.jpa.Search;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@Transactional
public class SearchService implements ApplicationListener<ApplicationReadyEvent> {

    private static final String FACET_CATEGORY = "category";
    private static final String FACET_BRAND = "brand";
    private static final String FACET_PRICE = "price";
    private static final String FACET_CHARACTERISTIC = "characteristic";

    private final EntityManager entityManager;

    public SearchService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private void initData() throws InterruptedException {
        Search.getFullTextEntityManager(entityManager).createIndexer().startAndWait();

    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            initData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
