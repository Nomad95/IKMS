package pl.politechnika.ikms.service.upload;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.upload.TempFile;
import pl.politechnika.ikms.repository.upload.TempFileRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class UploadServiceImpl extends AbstractService<TempFile, TempFileRepository> implements UploadService {

    public UploadServiceImpl(TempFileRepository repository) {
        super(repository, TempFile.class);
    }

    @Override
    public void uploadFile(MultipartFile file) {
        try {
            TempFile tempFile = new TempFile();
            tempFile.setContent(file.getBytes());
            tempFile.setName(file.getName());
            getRepository().save(tempFile);
            log.error("File {} saved in database", file.getName());
        } catch (IOException e) {
            log.error("Error while parsing file data ({})", file.getName());
        }

    }

    @Override
    public TempFile getFile() {
        Optional<TempFile> file = getRepository().findAll().stream().findFirst();
        return file.orElseThrow(EntityNotFoundException::new);
    }
}
