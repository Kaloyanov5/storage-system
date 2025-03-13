package spring.project.storage_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spring.project.storage_system.repository.FurnitureRepository;

@Service
public class FurnitureService {

    @Autowired
    private FurnitureRepository furnitureRepository;

    public ResponseEntity<?> getAllFurniture() {
        return ResponseEntity.ok(furnitureRepository.findAll());
    }
}
