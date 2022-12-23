package platform.api.code;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CodeModelRepo extends JpaRepository<CodeModel, UUID> {
    List<CodeModel> findAllByTimeLessThanEqualAndViewsLessThanEqual(Long time, Integer viewers);
}
