package pdp.uz.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import pdp.uz.domain.Client;
import pdp.uz.model.ClientDto;
import pdp.uz.model.resp.ApiResponse;

import java.util.List;

public interface ClientService {

    ResponseEntity<ApiResponse<List<Client>>> getAll(Pageable pageable);

    ResponseEntity<ApiResponse<Client>> get(Long id);

    ResponseEntity<ApiResponse<Client>> add(ClientDto dto);

    ResponseEntity<ApiResponse<Client>> update(Long id, ClientDto dto);

    ResponseEntity<ApiResponse<String>> delete(Long id);
}
