package pl.politechnika.ikms.rest.controller.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.politechnika.ikms.domain.upload.TempFile;
import pl.politechnika.ikms.service.upload.UploadService;

import java.io.IOException;

@RestController
@RequestMapping(value = "api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping(consumes = "multipart/form-data")
    public void uploadFile(@RequestParam MultipartFile file) throws IOException {
        uploadService.uploadFile(file);
    }

    @GetMapping
    public TempFile uploadFile() throws IOException {
        return uploadService.getFile();
    }
}
