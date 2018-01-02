package pl.politechnika.ikms.service.upload;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.domain.fileTree.FileTreeNode;
import pl.politechnika.ikms.domain.upload.DidacticMaterialFileEntity;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.repository.person.EmployeeRepository;
import pl.politechnika.ikms.repository.person.ParentRepository;
import pl.politechnika.ikms.repository.upload.DidacticMaterialFileRepository;
import pl.politechnika.ikms.rest.mapper.upload.DidacticMaterialFileMapper;

import java.util.*;

@Component
@RequiredArgsConstructor
public class FileTreeBuilder {
    private final DidacticMaterialFileRepository fileRepository;
    private final EmployeeRepository employeeRepository;
    private final ParentRepository parentRepository;
    private final DidacticMaterialFileMapper mapper;

    public List<FileTreeNode> getAllFiles(UserEntity userEntity){

        List<DidacticMaterialFileEntity> files;
        files = getDidacticMaterialsForUser(userEntity);

        ArrayList<FileTreeNode> fileTree = Lists.newArrayList();
        HashMap<FileTreeNode, FileTreeNode> foldersStructureMap = Maps.newHashMap();
        HashMap<String, String> rootFolders = prepareFoldersNamesMap(files);

        convertToNodeMap(rootFolders, foldersStructureMap);
        addFilesToSubfolders(files, foldersStructureMap);
        assembleTree(foldersStructureMap, fileTree);

        return fileTree;
    }

    private List<DidacticMaterialFileEntity> getDidacticMaterialsForUser(UserEntity userEntity) {
        List<DidacticMaterialFileEntity> files;
        if ("ROLE_EMPLOYEE".equals(userEntity.getRole().getName())) {
            Long employeeId = employeeRepository.getEmployeeIdByUserId(userEntity.getId());
            files = fileRepository.getAllForEmployee(employeeId);
        } else if ("ROLE_PARENT".equals(userEntity.getRole().getName())) {
            Long parentId = parentRepository.getParentIdByUserId(userEntity.getId());
            files = fileRepository.getAllForParent(parentId);
        } else {
            files = fileRepository.findAll();
        }
        return files;
    }

    private HashMap<String, String> prepareFoldersNamesMap(List<DidacticMaterialFileEntity> files) {
        HashMap<String, String> map = Maps.newHashMap();
        files.forEach(file -> map.put(file.getSubfolder(), file.getFolder()));
        return map;
    }

    private void convertToNodeMap(Map<String, String> rootFolders, HashMap<FileTreeNode, FileTreeNode> foldersStructureMap) {
        rootFolders.forEach((subfolder,folder) -> {
            FileTreeNode subfolderNode = new FileTreeNode();
            subfolderNode.setLabel(subfolder);
            subfolderNode.setFolder(true);
            subfolderNode.setCollapsedIcon("fa-folder");
            subfolderNode.setExpandedIcon("fa-folder-open");

            FileTreeNode folderNode = new FileTreeNode();
            folderNode.setLabel(folder);
            folderNode.setFolder(true);
            folderNode.setCollapsedIcon("fa-folder");
            folderNode.setExpandedIcon("fa-folder-open");

            foldersStructureMap.put(subfolderNode, folderNode);
        });
    }

    private void assembleTree(HashMap<FileTreeNode, FileTreeNode> foldersStructureMap, ArrayList<FileTreeNode> fileTree) {
        HashMap<String, FileTreeNode> foldersMap = Maps.newHashMap();
        HashSet<FileTreeNode> rootFolders = Sets.newHashSet();
        foldersStructureMap.forEach((subfolder, folder) -> {
            foldersMap.put(folder.getLabel(), folder);
        });
        foldersMap.forEach((k, v) -> rootFolders.add(v));
        foldersStructureMap.forEach((subfolder, folder) -> {
            final boolean[] wasAdded = {false};
            rootFolders.forEach(rootFolder -> {
                if (!wasAdded[0] && rootFolder.getLabel().equals(folder.getLabel())) {
                    rootFolder.getChildren().add(subfolder);
                    wasAdded[0] = true;
                }
            });
        });
        fileTree.addAll(rootFolders);
    }

    private void addFilesToSubfolders(List<DidacticMaterialFileEntity> files, HashMap<FileTreeNode, FileTreeNode> foldersStructureMap) {
        foldersStructureMap.forEach((subfolder,folder) -> {
            files.forEach(file -> {
                if (file.getSubfolder().equals(subfolder.getLabel()) && file.getFolder().equals(folder.getLabel())) {
                    FileTreeNode node = new FileTreeNode();
                    node.setLabel(file.getFilename());
                    node.setFolder(false);
                    node.setCollapsedIcon(file.getIcon());
                    node.setExpandedIcon(file.getIcon());
                    node.setData(mapper.convertToDtoLazyContent(file));
                    subfolder.getChildren().add(node);
                }
            });
        });
    }

}