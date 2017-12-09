package pl.politechnika.ikms.repository.upload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.upload.TempFile;

@Repository
public interface TempFileRepository extends JpaRepository<TempFile, Long>{
}
