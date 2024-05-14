package project.model;

import lombok.*;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.Embeddable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Locale {
    private String en;
    private String ru;
    private String uz;

    public String locale() {
        return switch (LocaleContextHolder.getLocale().toString()) {
            case "ru" -> ru;
            case "en" -> en;
            default -> uz;
        };
    }
}
