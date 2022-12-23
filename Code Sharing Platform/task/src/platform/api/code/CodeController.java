package platform.api.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping(value = "/api/")
@Controller
public class CodeController {
    @Autowired
    private CodeService service;

    @GetMapping(value = "code/{id}")
    public ResponseEntity<CodeModel> findById(@Valid @PathVariable @Min(0) UUID id) {
        return ResponseEntity.ok()
                .body(service.viewCodeModelById(id));
    }

    @PostMapping(value = "code/new")
    public ResponseEntity<Object> updateCode(@Valid @RequestBody CodeModel codeModel) {
        String id = service.addCode(codeModel);
        return ResponseEntity.ok()
                .body(Map.of("id", id));
    }

    @GetMapping(value = "code/latest")
    public ResponseEntity<List<CodeModel>> getLastTenCodeModel() {
        return ResponseEntity.ok().body(service.getLastTenCodeModel());
    }


}
