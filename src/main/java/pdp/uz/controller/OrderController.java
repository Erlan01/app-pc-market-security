package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdp.uz.domain.Order;
import pdp.uz.model.OrderDto;
import pdp.uz.model.resp.ApiResponse;
import pdp.uz.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<List<Order>>> getAll (@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderService.get(pageable);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Order>> get(@PathVariable Long id){
        return orderService.get(id);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Order>> add(@RequestBody OrderDto dto){
        return orderService.add(dto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Order>> update(@PathVariable Long id, @RequestBody OrderDto dto){
        return orderService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id){
        return orderService.delete(id);
    }
}
