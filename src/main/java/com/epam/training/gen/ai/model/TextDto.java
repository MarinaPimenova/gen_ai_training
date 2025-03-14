package com.epam.training.gen.ai.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class TextDto {
    @JsonIgnore
    private String created;
    List<Attachment> attachments;

    private static String convertToDate(Long val) {
        if (val == null) {
            return "";
        }
        try {
            Date date = new Date(val);
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(date);
        } catch (Exception ex) {
            log.error(format("Failed convert to Date: %d. Caused by %s", val, ex.getMessage()));
            return "";
        }
    }

    public static TextDto of(GeneratorResponse generatorResponse) {
        String createdDate = convertToDate(generatorResponse.getCreated());
        List<Attachment> attachments = new ArrayList<>();
        if (generatorResponse.getChoices() != null) {
            for (GeneratorResponse.Choice choice : generatorResponse.getChoices()) {
                if (choice.getMessage() != null && choice.getMessage().getCustomContent() != null) {
                    attachments.addAll(choice.getMessage().getCustomContent().getAttachments());
                }
            }
        }

        return TextDto.builder()
                .created(createdDate)
                .attachments(attachments)
                .build();
    }
}
