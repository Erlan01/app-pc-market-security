package pdp.uz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pdp.uz.domain.Attachment;
import pdp.uz.domain.AttachmentContent;
import pdp.uz.model.AttachmentDto;
import pdp.uz.model.resp.ApiResponse;
import pdp.uz.repository.AttachmentContentRepo;
import pdp.uz.repository.AttachmentRepo;
import pdp.uz.service.AttachmentService;

import java.io.IOException;
import java.util.Iterator;

import static pdp.uz.model.resp.ApiResponse.response;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepo attachmentRepo;
    private final AttachmentContentRepo attachmentContentRepo;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepo attachmentRepo, AttachmentContentRepo attachmentContentRepo) {
        this.attachmentRepo = attachmentRepo;
        this.attachmentContentRepo = attachmentContentRepo;
    }

    @Override
    public ResponseEntity<ApiResponse<AttachmentDto>> upload(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        Attachment attachment = Attachment.builder()
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .contentType(file.getContentType()).build();
        Attachment save = attachmentRepo.save(attachment);

        AttachmentContent content = AttachmentContent.builder()
                .attachment(save)
                .data(file.getBytes()).build();
        attachmentContentRepo.save(content);

        return response("Successfully", HttpStatus.CREATED);
    }
}
