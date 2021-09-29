package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdp.uz.domain.Product;
import pdp.uz.model.ProductDto;
import pdp.uz.model.resp.ApiResponse;
import pdp.uz.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<List<Product>>> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                             @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.get(pageable);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Product>> get (@PathVariable Long id){
        return productService.get(id);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Product>> add(@RequestBody ProductDto dto){
        return productService.add(dto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Product>> update(@PathVariable Long id, @RequestBody ProductDto dto){
        return productService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id){
        return productService.delete(id);
    }
}
