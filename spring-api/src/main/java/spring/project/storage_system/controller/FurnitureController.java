package spring.project.storage_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.project.storage_system.service.FurnitureService;

@RestController
@RequestMapping("/api")
public class FurnitureController {

    @Autowired
    private FurnitureService furnitureService;

    @GetMapping("/furniture")
    public ResponseEntity<?> getAllFurniture() {
        return furnitureService.getAllFurniture();
    }
}
