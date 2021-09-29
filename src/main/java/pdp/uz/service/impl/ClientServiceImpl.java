package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pdp.uz.domain.Client;
import pdp.uz.helpers.MapstructMapper;
import pdp.uz.helpers.Utils;
import pdp.uz.model.ClientDto;
import pdp.uz.model.resp.ApiResponse;
import pdp.uz.repository.ClientRepo;
import pdp.uz.service.ClientService;
import static pdp.uz.model.resp.ApiResponse.response;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepo clientRepo;

    private final MapstructMapper mapstructMapper;

    @Autowired
    public ClientServiceImpl(ClientRepo clientRepo, MapstructMapper mapstructMapper) {
        this.clientRepo = clientRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public ResponseEntity<ApiResponse<List<Client>>> getAll(Pageable pageable) {
        Page<Client> page = clientRepo.findAllByActiveTrue(pageable);
        List<Client> clientList = page.getContent();
        if (clientList.isEmpty()){
            return response(HttpStatus.NOT_FOUND);
        }
        long totalCount = clientRepo.count();
        return response(clientList, totalCount);
    }

    @Override
    public ResponseEntity<ApiResponse<Client>> get(Long id) {
        Optional<Client> clientOptional = clientRepo.findByIdAndActiveTrue(id);
        return clientOptional.map(ApiResponse::response).orElseGet(() -> response(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ApiResponse<Client>> add(ClientDto dto) {
        if (Utils.isEmpty(dto.getFullName())){
            return response("This full name should not be null!", HttpStatus.BAD_REQUEST);
        }
        if (Utils.isEmpty(dto.getPhoneNumber()) && Utils.isEmpty(dto.getEmail())){
            return response("This email and phone number should not be null!", HttpStatus.BAD_REQUEST);
        }
        if (clientRepo.existsByPhoneNumberAndEmailAndActiveTrue(dto.getPhoneNumber(), dto.getEmail())){
            return response("This email and phone number already exists!", HttpStatus.FORBIDDEN);
        }
        Client client = mapstructMapper.toClient(dto);
        return response(clientRepo.save(client), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<Client>> update(Long id, ClientDto dto) {
        if (Utils.isEmpty(dto.getFullName())){
            return response("This full name should not be null!", HttpStatus.BAD_REQUEST);
        }
        if (Utils.isEmpty(dto.getPhoneNumber()) && Utils.isEmpty(dto.getEmail())){
            return response("This email and phone number should not be null!", HttpStatus.BAD_REQUEST);
        }
        if (clientRepo.existsByPhoneNumberAndEmailAndActiveTrue(dto.getPhoneNumber(), dto.getEmail())){
            return response("This email and phone number already exists!", HttpStatus.FORBIDDEN);
        }

        Optional<Client> clientOptional = clientRepo.findByIdAndActiveTrue(id);
        if (!clientOptional.isPresent()){
            return response("Client id: " +id+", not found!", HttpStatus.NOT_FOUND);
        }
        Client client = clientOptional.get();
        client.setFullName(dto.getFullName());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setAddress(dto.getAddress());
        Client save = clientRepo.save(client);
        return response(mapstructMapper.toClient(save), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> delete(Long id) {
        Optional<Client> optionalClient = clientRepo.findByIdAndActiveTrue(id);
        if (!optionalClient.isPresent()){
            return response("Client id: "+id+", not found", HttpStatus.NOT_FOUND);
        }
        Client client = optionalClient.get();
        client.setActive(false);
        clientRepo.save(client);
        return response("Client deleted", HttpStatus.OK);
    }
}
