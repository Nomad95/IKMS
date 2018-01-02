package pl.politechnika.ikms.service.upload;

import org.springframework.web.multipart.MultipartFile;
import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.fileTree.FileTreeNode;
import pl.politechnika.ikms.domain.upload.DidacticMaterialFileEntity;
import pl.politechnika.ikms.rest.dto.upload.FileFormDataDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UploadService extends GenericService<DidacticMaterialFileEntity>{

    boolean uploadFile(MultipartFile file, FileFormDataDto formDataDto, String role, HttpServletRequest httpRequest);

    DidacticMaterialFileEntity getFile(Long materialId);

    List<FileTreeNode> getFileTree(HttpServletRequest httpServletRequest);
}
