package com.gameshop.ecommerce.user.controller;

import com.gameshop.ecommerce.user.model.LocalUser;
import com.gameshop.ecommerce.user.model.LocalUserDto;
import com.gameshop.ecommerce.user.service.MyAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "MyAccount", description = "API to work with My Account page")
public class MyAccountController {

    private final MyAccountService myAccountService;

    @PatchMapping
    public ResponseEntity<LocalUserDto> updateInfo(@AuthenticationPrincipal LocalUser user, @RequestBody LocalUserDto userDto) throws Exception {
        LocalUserDto updatedUser = myAccountService.updateInfo(user, userDto);
        return ResponseEntity.ok(updatedUser);
    }

}
