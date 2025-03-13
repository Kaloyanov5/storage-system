package spring.project.storage_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.storage_system.entity.Furniture;

public interface FurnitureRepository extends JpaRepository<Furniture, Long> {
}
