package com.epam.training.gen.ai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromptPayload {
    private List<UserPrompt> messages;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserPrompt {
        String role;
        String content;

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("role: ", role)
                    .append("content: ", content)
                    .toString();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("messages: ", String.join(", ", messages.toString()))
                .toString();
    }
}
