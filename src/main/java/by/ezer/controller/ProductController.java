package by.ezer.controller;

import by.ezer.dto.ProductCreateDTO;
import by.ezer.dto.ProductDTO;
import by.ezer.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

//    @GetMapping("/{id}")
//    public ResponseEntity<ProductDTO> findById(@PathVariable long id) {
//        return  productService.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

//    @GetMapping("/")
//    public ResponseEntity<PagedResult<ProductDTO>> findAllPaged(@RequestParam(value = "page", defaultValue = "0") int page,
//                                                                @RequestParam(value = "size", defaultValue = "5") int size) {
//        return ResponseEntity.ok(productService.findAllPaged(page, size));
//    }

    @PostMapping
    public ResponseEntity<ProductDTO> save(@RequestBody ProductCreateDTO request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
//        productService.updateProduct(id,  productDTO);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return ResponseEntity.noContent().build();
//    }
}

