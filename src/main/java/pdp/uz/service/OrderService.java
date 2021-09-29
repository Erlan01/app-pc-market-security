package pdp.uz.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import pdp.uz.domain.Order;
import pdp.uz.model.OrderDto;
import pdp.uz.model.resp.ApiResponse;

import java.util.List;

public interface OrderService {

    ResponseEntity<ApiResponse<List<Order>>> get(Pageable pageable);

    ResponseEntity<ApiResponse<Order>> get(Long id);

    ResponseEntity<ApiResponse<Order>> add(OrderDto dto);

    ResponseEntity<ApiResponse<Order>> update(Long id, OrderDto dto);

    ResponseEntity<ApiResponse<String>> delete(Long id);
}
