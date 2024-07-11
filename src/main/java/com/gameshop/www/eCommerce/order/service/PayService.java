package com.gameshop.www.eCommerce.order.service;

import com.gameshop.www.eCommerce.config.WayForPayConfig;
import com.gameshop.www.eCommerce.order.model.WebOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PayService {

    private final WayForPayConfig wayForPayConfig;

    public String initiatePayment(WebOrder order) {
        String url = "https://secure.wayforpay.com/pay";
        String orderReference = order.getOrderNumber();
        long orderDate = order.getOrderTime().toInstant().toEpochMilli();
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

        Map<String, String> params = new HashMap<>();
        params.put("merchantAccount", wayForPayConfig.getMerchantLogin());
        params.put("merchantDomainName", wayForPayConfig.getMerchantDomainName());
        params.put("merchantTransactionSecureType", "AUTO");
        params.put("orderReference", orderReference);
        params.put("orderDate", String.valueOf(orderDate));
        params.put("amount", String.valueOf(amount));
        params.put("currency", currency);
        params.put("clientFirstName", order.getUser().getFirstName());
        params.put("clientLastName", order.getUser().getLastName());
        params.put("clientEmail", order.getUser().getEmail());
        params.put("clientPhone", order.getUser().getPhoneNumber());
        params.put("returnUrl", wayForPayConfig.getReturnUrl());
        params.put("serviceUrl", wayForPayConfig.getServiceUrl());
        params.put("merchantSignature", signature);

        for (String productName : productNames) {
            params.put("productName[]", productName);
        }
        for (Integer productPrice : productPrices) {
            params.put("productPrice[]", String.valueOf(productPrice));
        }
        for (Integer productCount : productCounts) {
            params.put("productCount[]", String.valueOf(productCount));
        }

        String requestParams = params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        return url + "?" + requestParams;
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
