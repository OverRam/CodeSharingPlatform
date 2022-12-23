package platform.api.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CodeService {

    @Autowired
    private CodeModelRepo codeModelRepo;

    public String addCode(CodeModel codeModel) {
        codeModel.setDate(LocalDateTime.now());
        codeModel.setIsTime(codeModel.getTime() > 0);
        codeModel.setIsViews(codeModel.getViews() > 0);
        return codeModelRepo.save(codeModel).getId().toString();
    }

    public List<CodeModel> getLastTenCodeModel() {
        List<CodeModel> codeList = codeModelRepo.findAllByTimeLessThanEqualAndViewsLessThanEqual(0L, 0);
        int codeListSize = codeList.size();

        List<CodeModel> resList = new ArrayList<>();
        CodeModel model;

        for (int i = 0; i < codeListSize && i < 10; i++) {
            model = codeList.get(codeListSize - 1 - i);
            resList.add(model);

        }
        return resList;
    }

    /**
     * Increases the views field by 1 and checks if the CodeModel has the correct constraints, if they are correct,
     * it updates the database entry, otherwise it removes it from the database and throws a ResponseStatusException
     *
     * @param id ID of the CodeModel you are looking for
     * @return Returns the searched CodeModel
     * @throws ResponseStatusException if not found
     */
    public CodeModel viewCodeModelById(UUID id) throws ResponseStatusException {
        CodeModel model = findById(id);
        model.setActualViews(model.getActualViews() + 1);
        boolean isValid = manageCodeModel(model);

        model.setTime(model.getTime() > 0 ?
                model.getTime() - ChronoUnit.SECONDS.between(model.getDate(), LocalDateTime.now()) : 0);
        model.setViews(model.getViews() > 0 ? model.getViews() - model.getActualViews() : 0);

        if (!isValid) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no such index.");
        }

        return model;
    }

    /**
     * @param id ID of the CodeModel you are looking for
     * @return Returns the searched CodeModel
     * @throws ResponseStatusException if not found
     */
    public CodeModel findById(UUID id) throws ResponseStatusException {
        return codeModelRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no such index."));
    }

    /**
     * @param model CodeModel to check.
     * @return Return true if is valid otherwise false.
     */
    public boolean isViewsValid(CodeModel model) {
        if (model.getViews() <= 0) return true;
        return model.getViews() >= model.getActualViews();
    }

    /**
     * @param model CodeModel to check.
     * @return Return true if is valid otherwise false.
     */
    public boolean isTimeValid(CodeModel model) {
        if (model.getTime() == 0) return true;
        return model.getTime() > ChronoUnit.SECONDS.between(model.getDate(), LocalDateTime.now());
    }

    /**
     * It accepts the updated model and then checks whether it meets the requirements, if it meets them,
     * it saves the updated state to the database, otherwise it deletes it from the database.
     *
     * @param model CodeModel to manage.
     * @return Return true if is update otherwise return false and delete.
     */
    public boolean manageCodeModel(CodeModel model) {
        boolean isTimeValid = isTimeValid(model);
        boolean isViewsValid = isViewsValid(model);

        boolean isValid = isTimeValid && isViewsValid;

        if (isValid) {
            codeModelRepo.save(model);
        } else {
            codeModelRepo.deleteById(model.getId());
        }
        return isValid;
    }
}
