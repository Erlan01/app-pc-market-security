package pdp.uz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pdp.uz.model.AttachmentDto;
import pdp.uz.model.resp.ApiResponse;
import pdp.uz.service.AttachmentService;

import java.io.IOException;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }


    @PostMapping(value = "/upload")
    public ResponseEntity<ApiResponse<AttachmentDto>> upload(MultipartHttpServletRequest request) throws IOException {
        return attachmentService.upload(request);
    }
}
