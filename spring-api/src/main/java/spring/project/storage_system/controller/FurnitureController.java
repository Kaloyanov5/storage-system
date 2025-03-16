package spring.project.storage_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.project.storage_system.entity.Furniture;
import spring.project.storage_system.request.FurnitureRequest;
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

    @PostMapping("/furniture")
    public ResponseEntity<?> addFurniture(@RequestParam("furniture") Furniture furniture) {
        return furnitureService.addFurniture(furniture);
    }

    @PutMapping("/furniture/{furnitureId}")
    public ResponseEntity<?> editFurniture(@RequestParam("furniture") FurnitureRequest furnitureRequest,
                                           @PathVariable Long furnitureId) {
        return furnitureService.editFurniture(furnitureRequest, furnitureId);
    }

    @DeleteMapping("/furniture/{furnitureId}")
    public ResponseEntity<?> deleteFurniture(@PathVariable Long furnitureId) {
        return furnitureService.deleteFurniture(furnitureId);
    }
}
