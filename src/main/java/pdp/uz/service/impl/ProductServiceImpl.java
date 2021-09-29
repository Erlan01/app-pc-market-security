package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pdp.uz.domain.Attachment;
import pdp.uz.domain.Category;
import pdp.uz.domain.Product;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.ProductDto;
import pdp.uz.model.resp.ApiResponse;
import pdp.uz.repository.AttachmentRepo;
import pdp.uz.repository.CategoryRepo;
import pdp.uz.repository.ProductRepo;
import pdp.uz.service.ProductService;

import java.util.List;
import java.util.Optional;

import static pdp.uz.model.resp.ApiResponse.response;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final CategoryRepo categoryRepo;

    private final AttachmentRepo attachmentRepo;

    private final MapstructMapper mapstructMapper;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo, AttachmentRepo attachmentRepo, MapstructMapper mapstructMapper) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.attachmentRepo = attachmentRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public ResponseEntity<ApiResponse<List<Product>>> get(Pageable pageable) {
        Page<Product> page = productRepo.findAllByActiveTrue(pageable);
        List<Product> productList = page.getContent();
        if (productList.isEmpty()){
            return response(HttpStatus.NOT_FOUND);
        }
        long count = productRepo.count();
        return response(productList, count);
    }

    @Override
    public ResponseEntity<ApiResponse<Product>> get(Long id) {
        Optional<Product> productOptional = productRepo.findByIdAndActiveTrue(id);
        return productOptional.map(ApiResponse::response).orElseGet(() -> response(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ApiResponse<Product>> add(ProductDto dto) {
        if (Utils.isEmpty(dto.getName()) && Utils.isEmpty(dto.getModel())){
            return response(HttpStatus.BAD_REQUEST);
        }
        Optional<Category> categoryOptional = categoryRepo.findByIdAndActiveTrue(dto.getCategoryId());
        if (!categoryOptional.isPresent()){
            return response(HttpStatus.NOT_FOUND);
        }
        Optional<Attachment> attachmentOptional = attachmentRepo.findById(dto.getAttachmentId());
        if (!attachmentOptional.isPresent()){
            return response(HttpStatus.NOT_FOUND);
        }

        Product product = mapstructMapper.toProduct(dto);
        product.setCategory(categoryOptional.get());
        product.setAttachment(attachmentOptional.get());

        return response(productRepo.save(product), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<Product>> update(Long id, ProductDto dto) {
        Optional<Category> categoryOptional = categoryRepo.findByIdAndActiveTrue(dto.getCategoryId());
        if (!categoryOptional.isPresent()){
            return response(HttpStatus.NOT_FOUND);
        }
        Optional<Attachment> attachmentOptional = attachmentRepo.findById(dto.getAttachmentId());
        if (!attachmentOptional.isPresent()){
            return response(HttpStatus.NOT_FOUND);
        }

        Optional<Product> productOptional = productRepo.findByIdAndActiveTrue(id);
        if (!productOptional.isPresent()){
            return response("Product id: "+id+", not found!", HttpStatus.NOT_FOUND);
        }
        Product product = productOptional.get();
        product.setName(dto.getName() != null ? dto.getName() : product.getName());
        product.setModel(dto.getModel() != null ? dto.getModel() : product.getModel());
        product.setDescription(dto.getDescription() != null ? dto.getDescription() : product.getDescription());
        product.setCategory(categoryOptional.get());
        product.setMark(dto.getMark() != null ? dto.getMark() : product.getMark());
        product.setAttachment(attachmentOptional.get());

        return response(productRepo.save(product), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> delete(Long id) {
        Optional<Product> productOptional = productRepo.findByIdAndActiveTrue(id);
        if (!productOptional.isPresent()){
            return response("Product id: "+id+", not found!", HttpStatus.NOT_FOUND);
        }
        Product product = productOptional.get();
        product.setActive(false);
        productRepo.save(product);
        return response("Product deleted", HttpStatus.OK);
    }
}
