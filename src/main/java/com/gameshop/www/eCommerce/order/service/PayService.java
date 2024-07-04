package com.gameshop.www.eCommerce.order.service;

import com.gameshop.www.eCommerce.config.WayForPayConfig;
import com.gameshop.www.eCommerce.order.model.WebOrder;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.StringJoiner;

@AllArgsConstructor
@Service
public class PayService {

    private final WayForPayConfig wayForPayConfig;
    private final RestTemplate restTemplate;

    public String initiatePayment(WebOrder order) {
        String url = "https://secure.wayforpay.com/pay";
        String orderReference = order.getOrderNumber();
        long orderDate = System.currentTimeMillis() / 1000L;
        double amount = order.getTotalPrice();
        String currency = "USD";

        String[] productNames = order.getQuantities().stream()
                .map(q -> q.getProduct().getName())
                .toArray(String[]::new);

        Integer[] productPrices = order.getQuantities().stream()
                .map(q -> q.getProduct().getPrice())
                .toArray(Integer[]::new);

        Integer[] productCounts = order.getQuantities().stream()
                .map(q -> q.getQuantity())
                .toArray(Integer[]::new);

        String signature = generateSignature(wayForPayConfig.getMerchantLogin(),
                wayForPayConfig.getMerchantDomainName(), orderReference, orderDate, amount, currency, productNames, productPrices, productCounts);

        // Формування параметрів запиту
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("merchantAccount", wayForPayConfig.getMerchantLogin());
        params.add("merchantDomainName", wayForPayConfig.getMerchantDomainName());
        params.add("merchantTransactionSecureType", "AUTO");
        params.add("orderReference", orderReference);
        params.add("orderDate", String.valueOf(orderDate));
        params.add("amount", String.valueOf(amount));
        params.add("currency", currency);

        // Додавання масивів продуктів у запит
        for (String productName : productNames) {
            params.add("productName[]", productName);
        }
        for (Integer productPrice : productPrices) {
            params.add("productPrice[]", String.valueOf(productPrice));
        }
        for (Integer productCount : productCounts) {
            params.add("productCount[]", String.valueOf(productCount));
        }

        params.add("clientFirstName", order.getUser().getFirstName());
        params.add("clientLastName", order.getUser().getLastName());
        params.add("clientEmail", order.getUser().getEmail());
        params.add("clientPhone", order.getUser().getPhoneNumber());
        params.add("returnUrl", wayForPayConfig.getReturnUrl());
        params.add("serviceUrl", wayForPayConfig.getServiceUrl());
        params.add("merchantSignature", signature);

        // Відправка POST-запиту
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        return response.getBody();
    }

    private String generateSignature(String merchantAccount, String merchantDomainName, String orderReference, long orderDate, double amount, String currency, String[] productNames, Integer[] productPrices, Integer[] productCounts) {
        try {
            StringJoiner joiner = new StringJoiner(";");
            joiner.add(merchantAccount)
                    .add(merchantDomainName)
                    .add(orderReference)
                    .add(String.valueOf(orderDate))
                    .add(String.valueOf(amount))
                    .add(currency);

            for (String productName : productNames) {
                joiner.add(productName);
            }
            for (Integer productCount : productCounts) {
                joiner.add(String.valueOf(productCount));
            }
            for (Integer productPrice : productPrices) {
                joiner.add(String.valueOf(productPrice));
            }

            String data = joiner.toString();
            SecretKeySpec keySpec = new SecretKeySpec(wayForPayConfig.getSecretKey().getBytes(), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(keySpec);
            byte[] macData = mac.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(macData);
        } catch (Exception e) {
            throw new RuntimeException("Error generating signature", e);
        }
    }
}
