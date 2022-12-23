package platform.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import platform.api.code.CodeModel;
import platform.api.code.CodeService;

import java.util.UUID;

@Controller
public class FrontCode {
    @Autowired
    private CodeService codeService;

    @GetMapping("/code/{id}")
    public String findCode(Model model, @PathVariable UUID id) {
        CodeModel codeModel = codeService.viewCodeModelById(id);
        model.addAttribute("date", codeModel.getFormattedData());
        model.addAttribute("code", codeModel.getScript());
        model.addAttribute("isViews", codeModel.isViews());
        model.addAttribute("views", codeModel.getViews());
        model.addAttribute("isTime", codeModel.isTime());
        model.addAttribute("time", codeModel.getTime());

        return "findCodeById";
    }

    @GetMapping("/code/new")
    public String addCode() {
        return "addCode";
    }

    @GetMapping("/code/latest")
    public String getLastTenCodeModel(Model model) {
        model.addAttribute("modelList", codeService.getLastTenCodeModel());
        return "latest";
    }
}
