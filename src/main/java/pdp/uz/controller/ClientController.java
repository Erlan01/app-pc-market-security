package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdp.uz.domain.Client;
import pdp.uz.model.ClientDto;
import pdp.uz.model.resp.ApiResponse;
import pdp.uz.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse<List<Client>>> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return clientService.getAll(pageable);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Client>> get (@PathVariable Long id){
        return clientService.get(id);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Client>> add(@RequestBody ClientDto dto){
        return clientService.add(dto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Client>> update (@PathVariable Long id, @RequestBody ClientDto dto){
        return clientService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id){
        return clientService.delete(id);
    }
}
