package com.gameshop.ecommerce.user.controller;

import com.gameshop.ecommerce.user.model.LocalUser;
import com.gameshop.ecommerce.user.model.LocalUserDto;
import com.gameshop.ecommerce.user.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "MyAccount", description = "API to work with My Account page")
public class AccountController {

    private final AccountService accountService;

    @PutMapping
    public ResponseEntity<LocalUserDto> updateInfo(@AuthenticationPrincipal LocalUser user, @RequestBody LocalUserDto userDto) {
        LocalUserDto updatedUser = accountService.updateInfo(user, userDto);
        return ResponseEntity.ok(updatedUser);
    }

}
