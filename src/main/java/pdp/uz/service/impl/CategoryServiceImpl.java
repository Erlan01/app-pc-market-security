package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pdp.uz.domain.Category;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.CategoryDto;
import pdp.uz.model.resp.ApiResponse;
import pdp.uz.repository.CategoryRepo;
import pdp.uz.service.CategoryService;
import static pdp.uz.model.resp.ApiResponse.response;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo, MapstructMapper mapstructMapper) {
        this.categoryRepo = categoryRepo;
        this.mapstructMapper = mapstructMapper;
    }


    @Override
    public ResponseEntity<ApiResponse<List<Category>>> getAll(Pageable pageable) {
        Page<Category> page = categoryRepo.findAllByActiveTrue(pageable);
        List<Category> categoryList = page.getContent();
        if (categoryList.isEmpty()){
            return response(HttpStatus.NOT_FOUND);
        }
        Long totalCount = categoryRepo.count();
        return response(categoryList, totalCount);
    }

    @Override
    public ResponseEntity<ApiResponse<Category>> get(Long id) {
        Optional<Category> categoryOptional = categoryRepo.findByIdAndActiveTrue(id);
        return categoryOptional.map(ApiResponse::response).orElseGet(() -> response(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ApiResponse<Category>> add(CategoryDto dto) {
        if (Utils.isEmpty(dto.getName())){
            return response("Category name should not be null!", HttpStatus.BAD_REQUEST);
        }
        Optional<Category> categoryOptional = categoryRepo.findByNameAndActiveTrue(dto.getName());
        if (categoryOptional.isPresent()){
            return response("This name is already exists!", HttpStatus.FORBIDDEN);
        }
        Category category = mapstructMapper.toCategory(dto);
        return response(categoryRepo.save(category), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<Category>> update(Long id, CategoryDto dto) {
        if (Utils.isEmpty(dto.getName())){
            return response("Category name should not be null!", HttpStatus.BAD_REQUEST);
        }
        Optional<Category> optionalCategory = categoryRepo.findByIdAndActiveTrue(id);
        if (!optionalCategory.isPresent()){
            return response("Category id:"+id+", not found!", HttpStatus.NOT_FOUND);
        }
        Category category = optionalCategory.get();
        if (category.getName().equalsIgnoreCase(dto.getName())){
            return response("This name is already exists!", HttpStatus.FORBIDDEN);
        }
        category.setName(dto.getName());
        Category saveCategory = mapstructMapper.toCategory(category);
        return response(categoryRepo.save(saveCategory), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> delete(Long id) {
        Optional<Category> categoryOptional = categoryRepo.findByIdAndActiveTrue(id);
        if (!categoryOptional.isPresent()){
            return response("Category id:"+id+", not found!", HttpStatus.NOT_FOUND);
        }
        Category category = categoryOptional.get();
        category.setActive(false);
        categoryRepo.save(category);
        return response("Category deleted", HttpStatus.OK);
    }
}
