package pl.politechnika.ikms.service.upload;

import org.springframework.web.multipart.MultipartFile;
import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.upload.TempFile;

public interface UploadService extends GenericService<TempFile>{
    void uploadFile(MultipartFile file);

    TempFile getFile();
}
