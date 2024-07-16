package com.gameshop.ecommerce.user.controller;

import com.gameshop.ecommerce.user.model.LocalUserDto;
import com.gameshop.ecommerce.user.service.MyAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/my_account")
@RequiredArgsConstructor
@Tag(name = "MyAccount", description = "API to work with My Account page")
public class MyAccountController {

    private final MyAccountService myAccountService;

    @PutMapping("/{userId}")
    public ResponseEntity<LocalUserDto> updateInfo(@PathVariable UUID userId, @RequestBody LocalUserDto userDto){
        LocalUserDto updatedUser = myAccountService.updateInfo(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }
}
