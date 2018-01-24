package pl.politechnika.ikms.rest.controller.upload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.politechnika.ikms.domain.fileTree.FileTreeNode;
import pl.politechnika.ikms.rest.dto.upload.DidacticMaterialFileDto;
import pl.politechnika.ikms.rest.dto.upload.FileFormDataDto;
import pl.politechnika.ikms.service.upload.UploadService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "api/upload")
@RequiredArgsConstructor
@Slf4j
public class UploadController {

    public static final String ROLE = "role";

    private final UploadService uploadService;

    @PostMapping
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, FileFormDataDto formDataDto,
    @RequestHeader HttpHeaders headers, HttpServletRequest httpRequest) throws IOException {
        List<String> role = headers.get(ROLE);

        if (CollectionUtils.isEmpty(role)) {
            throw new IllegalArgumentException("Role was not specified");
        }
        boolean result = uploadService.uploadFile(file, formDataDto, role.get(0), httpRequest);
        if (result) {
            return ResponseEntity.ok(Boolean.TRUE);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{materialId}")
    public DidacticMaterialFileDto downloadFile(@PathVariable("materialId") Long materialId) throws IOException {
        return uploadService.getFile(materialId);
    }

    @GetMapping(value = "/tree")
    public List<FileTreeNode> getFileTree(HttpServletRequest httpRequest) throws IOException {
        return uploadService.getFileTree(httpRequest);
    }
}
