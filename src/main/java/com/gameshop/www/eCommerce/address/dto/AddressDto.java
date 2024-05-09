package com.gameshop.www.eCommerce.address.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String country;
    private String city;
    private String addressLine;
    private Integer postcode;

}
