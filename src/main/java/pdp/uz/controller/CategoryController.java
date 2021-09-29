package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdp.uz.domain.Category;
import pdp.uz.model.CategoryDto;
import pdp.uz.model.resp.ApiResponse;
import pdp.uz.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Category>>> get (@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return categoryService.getAll(pageable);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Category>> get (@PathVariable Long id){
        return categoryService.get(id);
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Category>> add (@RequestBody CategoryDto dto){
        return categoryService.add(dto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Category>> update (@PathVariable Long id, @RequestBody CategoryDto dto){
        return categoryService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id){
        return categoryService.delete(id);
    }
}
