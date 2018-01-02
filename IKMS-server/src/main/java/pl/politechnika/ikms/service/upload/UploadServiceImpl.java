package pl.politechnika.ikms.service.upload;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.fileTree.FileTreeNode;
import pl.politechnika.ikms.domain.group.GroupEntity;
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.domain.person.ParentEntity;
import pl.politechnika.ikms.domain.upload.DidacticMaterialFileEntity;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.repository.group.GroupRepository;
import pl.politechnika.ikms.repository.person.EmployeeRepository;
import pl.politechnika.ikms.repository.person.ParentRepository;
import pl.politechnika.ikms.repository.upload.DidacticMaterialFileRepository;
import pl.politechnika.ikms.rest.dto.upload.FileFormDataDto;
import pl.politechnika.ikms.security.JwtUserFacilities;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UploadServiceImpl extends AbstractService<DidacticMaterialFileEntity, DidacticMaterialFileRepository> implements UploadService {

    private final @NonNull FileTreeBuilder fileTreeBuilder;
    private final @NonNull EmployeeRepository employeeRepository;
    private final @NonNull GroupRepository groupRepository;
    private final @NonNull ParentRepository parentRepository;
    private final @NonNull JwtUserFacilities jwtUserFacilities;



    public UploadServiceImpl(DidacticMaterialFileRepository repository, FileTreeBuilder fileTreeBuilder, EmployeeRepository employeeRepository, GroupRepository groupRepository, ParentRepository parentRepository, JwtUserFacilities jwtUserFacilities) {
        super(repository, DidacticMaterialFileEntity.class);
        this.fileTreeBuilder = fileTreeBuilder;
        this.employeeRepository = employeeRepository;
        this.groupRepository = groupRepository;
        this.parentRepository = parentRepository;
        this.jwtUserFacilities = jwtUserFacilities;
    }

    @Override
    public boolean uploadFile(MultipartFile file, FileFormDataDto formDataDto, String role, HttpServletRequest httpRequest) {
        log.debug("uploading file with form data: {}", formDataDto);

        try {
            List<Long> employeesIds = deserializeToArray(formDataDto.getSelectedEmployees());
            List<Long> groupsIds = deserializeToArray(formDataDto.getSelectedGroups());
            List<Long> parentsIds = deserializeToArray(formDataDto.getSelectedParents());

            DidacticMaterialFileEntity didacticFile = new DidacticMaterialFileEntity();
            didacticFile.setContent(file.getBytes());
            didacticFile.setDateOfUpload(LocalDate.now());
            didacticFile.setDescription(formDataDto.getDescription());
            didacticFile.setExtension(getFileExtension(file.getOriginalFilename()));
            didacticFile.setFilename(file.getOriginalFilename());
            didacticFile.setFolder(formDataDto.getFolder());
            didacticFile.setSubfolder(formDataDto.getSubfolder());
            didacticFile.setIcon(getIconByExtension(file.getOriginalFilename()));
            didacticFile.setSize(convertToReadableFormat(file.getSize()));

            List<ParentEntity> parents = parentRepository.findAll(parentsIds);
            if (!parents.isEmpty()) {
                didacticFile.setSharedParents(parents);
            }
            List<GroupEntity> groups = groupRepository.findAll(groupsIds);
            if (!groups.isEmpty()) {
                didacticFile.setSharedGroups(groups);
            }
            List<EmployeeEntity> employees = employeeRepository.findAll(employeesIds);
            if (!employees.isEmpty()) {
                didacticFile.setSharedEmployees(employees);
            }

            if ("EMPLOYEE".equals(role)) {
                UserEntity userEntity = jwtUserFacilities.findUserByUsernameFromToken(httpRequest);
                Optional<EmployeeEntity> byUserId = employeeRepository.getByUserId(userEntity.getId());
                if (byUserId.isPresent()) {
                    EmployeeEntity employeeEntity = byUserId.get();
                    if (!didacticFile.getSharedEmployees().contains(employeeEntity)) {
                        didacticFile.getSharedEmployees().add(employeeEntity);
                    }
                }
            }

            getRepository().save(didacticFile);
            log.debug("File {} saved in database", file.getOriginalFilename());
        } catch (IOException e) {
            log.error("Error while parsing file data ({})", file.getOriginalFilename());
            return false;
        }

        return true;
    }

    private List<Long> deserializeToArray(String values) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        return objectMapper.readValue(values, typeFactory.constructCollectionType(List.class, Long.class));
    }

    @Override
    public DidacticMaterialFileEntity getFile(Long materialId) {
        DidacticMaterialFileEntity file = getRepository().getOne(materialId);
        file.getContent();
        return file;
    }

    @Override
    public List<FileTreeNode> getFileTree(HttpServletRequest httpRequest) {
        UserEntity userEntity = jwtUserFacilities.findUserByUsernameFromToken(httpRequest);
        return fileTreeBuilder.getAllFiles(userEntity);
    }

    private String convertToReadableFormat(long fileSize) {
        String result = "0";
        if (fileSize < 1024) {
            return String.valueOf(fileSize) + " B";
        }
        if (fileSize >= 1024) {
            fileSize = fileSize / 1024;
            result = String.valueOf(fileSize) + " kiB";
        }
        if (fileSize >= 1024) {
            fileSize = fileSize / 1024;
            result = String.valueOf(fileSize) + " MiB";
        }
        if (fileSize >= 1024) {
            fileSize = fileSize / 1024;
            result = String.valueOf(fileSize) + " GiB";
        }

        return result;
    }

    private String getIconByExtension(String filename){
        String fileExtension = getFileExtension(filename.toLowerCase());
        if (StringUtils.isEmpty(fileExtension)) {
            return "fa-file-o";
        }
        if ("doc".equals(fileExtension) || "odt".equals(fileExtension)) {
            return "fa-file-word-o";
        }
        if ("png".equals(fileExtension) || "jpg".equals(fileExtension) || "jpeg".equals(fileExtension) || "bmp".equals(fileExtension)) {
            return "fa-file-image-o";
        }
        if ("zip".equals(fileExtension) || "rar".equals(fileExtension)) {
            return "fa-file-archive-o";
        }
        if ("txt".equals(fileExtension)) {
            return "fa-file-text-o";
        }
        if ("pdf".equals(fileExtension)) {
            return "fa-file-pdf-o";
        }
        if ("xlsx".equals(fileExtension) || "xls".equals(fileExtension) || "csv".equals(fileExtension)) {
            return "fa-file-excel-o";
        }
        if ("avi".equals(fileExtension) || "wmv".equals(fileExtension) || "mp4".equals(fileExtension) || "mpeg".equals(fileExtension)) {
            return "fa-file-video-o";
        }

        return "fa-file-o";
    }

    private static String getFileExtension(String fileName) {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return null;
    }
}
