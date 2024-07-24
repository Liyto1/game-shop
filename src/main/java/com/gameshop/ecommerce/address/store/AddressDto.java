package com.gameshop.ecommerce.address.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AddressDto (@NotBlank
                          @Size(max = 20)
                          String firstName,
                          @NotBlank
                          @Size(max = 20)
                          String lastName,
                          @NotBlank
                          @Size(max = 12)
                          String contactNumber,
                          @NotBlank
                          @Size(max = 20)
                          String country,
                          @NotBlank
                          @Size(max = 20)
                          String city,
                          @NotBlank
                          @Size(max = 40)
                          String addressLine,
                          @NotBlank
                          @Size(max = 40)
                          Integer postcode) { }