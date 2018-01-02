package pl.politechnika.ikms.domain.fileTree;

import com.google.common.collect.Lists;
import lombok.Data;
import pl.politechnika.ikms.rest.dto.upload.DidacticMaterialFileDto;

import java.util.List;

@Data
public class FileTreeNode {

    private String label;

    private DidacticMaterialFileDto data;

    private String expandedIcon;

    private String collapsedIcon;

    private boolean isFolder;

    private List<FileTreeNode> children = Lists.newArrayList();
}
