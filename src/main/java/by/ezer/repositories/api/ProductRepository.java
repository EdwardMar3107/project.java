package by.ezer.repositories.api;

import by.ezer.dto.ProductDTO;
import by.ezer.entity.Product;
import by.ezer.exceptions.RepositoryException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
