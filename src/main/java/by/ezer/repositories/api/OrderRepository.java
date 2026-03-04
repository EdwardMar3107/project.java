package by.ezer.repositories.api;

import by.ezer.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
