package by.ezer.controller;

import by.ezer.dto.OrderCreateDTO;
import by.ezer.dto.OrderDTO;
import by.ezer.dto.PagedResult;
import by.ezer.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById (@PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping ResponseEntity<PagedResult<OrderDTO>> findAllPaged (@RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(orderService.findAllPaged(page, size));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> save (@RequestBody OrderCreateDTO request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update (@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        orderService.updateOrder(id, orderDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDTO> delete (@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
