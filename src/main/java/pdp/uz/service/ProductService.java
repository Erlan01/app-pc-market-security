package pdp.uz.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import pdp.uz.domain.Product;
import pdp.uz.model.ProductDto;
import pdp.uz.model.resp.ApiResponse;

import java.util.List;

public interface ProductService {

    ResponseEntity<ApiResponse<List<Product>>> get(Pageable pageable);

    ResponseEntity<ApiResponse<Product>> get(Long id);

    ResponseEntity<ApiResponse<Product>> add(ProductDto dto);

    ResponseEntity<ApiResponse<Product>> update(Long id, ProductDto dto);

    ResponseEntity<ApiResponse<String>> delete(Long id);
}
