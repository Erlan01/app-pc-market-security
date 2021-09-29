package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pdp.uz.domain.Client;
import pdp.uz.domain.Order;
import pdp.uz.domain.Product;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.OrderDto;
import pdp.uz.model.resp.ApiResponse;
import pdp.uz.repository.ClientRepo;
import pdp.uz.repository.OrderRepo;
import pdp.uz.repository.ProductRepo;
import pdp.uz.service.OrderService;

import static pdp.uz.model.resp.ApiResponse.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;

    private final ProductRepo productRepo;

    private final ClientRepo clientRepo;

    private final MapstructMapper mapstructMapper;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, ProductRepo productRepo, ClientRepo clientRepo, MapstructMapper mapstructMapper) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.clientRepo = clientRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public ResponseEntity<ApiResponse<List<Order>>> get(Pageable pageable) {
        Page<Order> page = orderRepo.findAll(pageable);
        List<Order> orderList = page.getContent();
        if (orderList.isEmpty()) {
            return response(HttpStatus.NOT_FOUND);
        }
        long count = orderRepo.count();
        return response(orderList, count);
    }

    @Override
    public ResponseEntity<ApiResponse<Order>> get(Long id) {
        Optional<Order> orderOptional = orderRepo.findById(id);
        return orderOptional.map(ApiResponse::response).orElseGet(() -> response(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ApiResponse<Order>> add(OrderDto dto) {
        Optional<Client> clientOptional = clientRepo.findByIdAndActiveTrue(dto.getClientId());
        if (!clientOptional.isPresent()) {
            return response("Client id: " + dto.getClientId() + ", not found!", HttpStatus.NOT_FOUND);
        }

        List<Product> productList = new ArrayList<>();

        for (Long productId : dto.getProductsId()) {
            if (Utils.isEmpty(productId)) {
                continue;
            }
            Optional<Product> productOptional = productRepo.findByIdAndActiveTrue(productId);
            if (productOptional.isPresent()) {
                productList.add(productOptional.get());
            }
        }
        Order order = mapstructMapper.toOrder(dto);
        order.setClient(clientOptional.get());
        order.setProducts(productList);

        return response(orderRepo.save(order), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<Order>> update(Long id, OrderDto dto) {
        Optional<Client> clientOptional = clientRepo.findByIdAndActiveTrue(dto.getClientId());
        if (!clientOptional.isPresent()) {
            return response("Client id: " + dto.getClientId() + ", not found!", HttpStatus.NOT_FOUND);
        }

        List<Product> productList = new ArrayList<>();

        for (Long productId : dto.getProductsId()) {
            if (Utils.isEmpty(productId)) {
                continue;
            }
            Optional<Product> productOptional = productRepo.findByIdAndActiveTrue(productId);
            productOptional.ifPresent(productList::add);
        }

        Optional<Order> orderOptional = orderRepo.findById(id);
        if (!orderOptional.isPresent()) {
            return response(HttpStatus.NOT_FOUND);
        }
        Order order = orderOptional.get();
        order.setProducts(productList);
        order.setClient(clientOptional.get());
        return response(orderRepo.save(order), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> delete(Long id) {
        Optional<Order> orderOptional = orderRepo.findById(id);
        if (!orderOptional.isPresent()) {
            return response(HttpStatus.NOT_FOUND);
        }
        orderRepo.delete(orderOptional.get());
        return response("Order deleted", HttpStatus.OK);
    }
}
