package spring.project.storage_system.service;

import org.springframework.http.HttpStatus;
import spring.project.storage_system.entity.Furniture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spring.project.storage_system.repository.FurnitureRepository;
import spring.project.storage_system.request.FurnitureRequest;

import java.util.Optional;

@Service
public class FurnitureService {

    @Autowired
    private FurnitureRepository furnitureRepository;

    public ResponseEntity<?> getAllFurniture() {
        return ResponseEntity.ok(furnitureRepository.findAll());
    }

    public ResponseEntity<?> addFurniture(Furniture furniture) {
        furnitureRepository.save(furniture);
        return ResponseEntity.ok("Furniture added successfully");
    }

    public ResponseEntity<?> editFurniture(FurnitureRequest furnitureRequest, Long furnitureId) {
        Optional<Furniture> furnitureOptional = furnitureRepository.findById(furnitureId);
        if (furnitureOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Furniture with this ID does not exist.");
        Furniture furniture = furnitureOptional.get();

        furniture.setName(furnitureRequest.getName());
        furniture.setCategory(furnitureRequest.getCategory());
        furniture.setPrice(furnitureRequest.getPrice());
        furniture.setImageName(furnitureRequest.getImageName());

        furnitureRepository.save(furniture);
        return ResponseEntity.ok("Furniture edit successful.");
    }

    public ResponseEntity<?> deleteFurniture(Long furnitureId) {
        Optional<Furniture> furnitureOptional = furnitureRepository.findById(furnitureId);
        if (furnitureOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Furniture with this ID does not exist.");
        Furniture furniture = furnitureOptional.get();

        furnitureRepository.delete(furniture);
        return ResponseEntity.ok("Furniture was deleted successfully.");
    }
}
