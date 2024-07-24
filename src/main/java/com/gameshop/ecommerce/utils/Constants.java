package com.gameshop.ecommerce.utils;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class Constants {
    public static final String REVIEW_TEXT = "I've been using the QuantumX Pro for a month now, and I'm blown away by"
            + "its precision and customizable RGB lighting."
            + "The ergonomic design is perfect for my long gaming sessions."
            + "A must-have for serious gamers!";
    public final List<String> categories = List.of("Keyboards", "Mice", "Headsets", "Mouse mats",
            "Joysticks and controllers", "Gaming chairs");
    public final List<String> brands = List.of("Logitech", "Razer", "Acer", "Asus", "Gigabyte", "MSI");
    public final Map<String, String> images = new HashMap<>(Map.of(
            "Mouse",
            "https://girlsonlytravel.com/img/works/mouse.png",
            "Keyboard", "https://girlsonlytravel.com/img/works/keyboard.png", "Headset",
            "https://girlsonlytravel.com/img/works/headset.png", "Joystick",
            "https://girlsonlytravel.com/img/works/joystick.png",
            "def", "https://static.tildacdn.com/tild6237-6265-4232-a233-663832313834/noroot.png"
        )
    );
    public static final String VERIFICATION_USER_EMAIL_KEY = "VERIFIC_EMAIL";
    public static final String RESET_PASSWORD_EMAIL_KEY = "RESET_EMAIL";
    public static final String USER_ID = "id";
}