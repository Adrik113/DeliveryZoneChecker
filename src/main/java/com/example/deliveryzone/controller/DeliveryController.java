package com.example.deliveryzone.controller;

import com.example.deliveryzone.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class DeliveryController {
    @Autowired 
    private DeliveryService deliveryService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/checkDelivery")
    public String checkDelivery(@RequestParam double latitude,
                                @RequestParam double longitude,
                                Model model) {
                    boolean result = deliveryService.isWithinDeliveryZone(latitude, longitude);
                    model.addAttribute("latitude", latitude);
                    model.addAttribute("longitude", longitude);
                    model.addAttribute("inZone", result);
                    return "index";
     }
}
