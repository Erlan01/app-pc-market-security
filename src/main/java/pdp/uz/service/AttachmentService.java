package pdp.uz.service;

import org.springframework.http.ResponseEntity;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import pdp.uz.model.AttachmentDto;
import pdp.uz.model.resp.ApiResponse;

import java.io.IOException;

public interface AttachmentService {

    ResponseEntity<ApiResponse<AttachmentDto>> upload(MultipartHttpServletRequest request) throws IOException;

}
