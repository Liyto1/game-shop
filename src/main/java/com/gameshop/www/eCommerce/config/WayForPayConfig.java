package com.gameshop.www.eCommerce.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class WayForPayConfig {
    @Value("${wayforpay.merchant.login}")
    private String merchantLogin;

    @Value("${wayforpay.merchant.domian.name}")
    private String merchantDomainName;

    @Value("${wayforpay.merchant.secret}")
    private String secretKey;

    @Value("${wayforpay.return.url}")
    private String returnUrl;

    @Value("${wayforpay.service.url}")
    private String serviceUrl;
}
