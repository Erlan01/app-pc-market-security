package pdp.uz.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import pdp.uz.domain.Category;
import pdp.uz.model.CategoryDto;
import pdp.uz.model.resp.ApiResponse;

import java.util.List;

public interface CategoryService {

    ResponseEntity<ApiResponse<List<Category>>> getAll(Pageable pageable);

    ResponseEntity<ApiResponse<Category>> get(Long id);

    ResponseEntity<ApiResponse<Category>> add(CategoryDto dto);

    ResponseEntity<ApiResponse<Category>> update(Long id, CategoryDto dto);

    ResponseEntity<ApiResponse<String>> delete(Long id);
}
