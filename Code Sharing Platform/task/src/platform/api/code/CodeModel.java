package platform.api.code;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonPropertyOrder({
        "code",
        "date",
        "time",
        "views"
})
@Entity
public class CodeModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @JsonIgnore
    private UUID id;
    @Size(min = 1, message = "The code must be at least 1 character long.")
    @JsonProperty(required = true, value = "code")
    @NotNull
    private String script;
    private LocalDateTime date;
    private long time;
    private int views;
    @JsonIgnore
    private int actualViews;
    @JsonIgnore
    private boolean isTime;
    @JsonIgnore
    private boolean isViews;

    @JsonProperty("date")
    public String getFormattedData() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeModel codeModel = (CodeModel) o;

        if (time != codeModel.time) return false;
        if (views != codeModel.views) return false;
        if (actualViews != codeModel.actualViews) return false;
        if (!Objects.equals(id, codeModel.id)) return false;
        if (!script.equals(codeModel.script)) return false;
        return Objects.equals(date, codeModel.date);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + script.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + views;
        result = 31 * result + actualViews;
        return result;
    }

    public boolean isTime() {
        return isTime;
    }

    public void setIsTime(boolean isTime) {
        this.isTime = isTime;
    }

    public boolean isViews() {
        return isViews;
    }

    public void setIsViews(boolean isViews) {
        this.isViews = isViews;
    }
}
