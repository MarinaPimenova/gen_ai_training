package com.epam.training.gen.ai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneratorResponse {
    private String id;
    private String object;
    private Long created;
    private List<Choice> choices;
    private Usage usage;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Attachment {
        String title;
        String data;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CustomContent {
        List<Attachment> attachments;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Message {
        String role;
        String content;
        @JsonProperty("custom_content")
        CustomContent customContent;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Choice {
        Integer index;
        Message message;
        @JsonProperty("finish_reason")
        String finishReason;
    }

    public static class Usage {
        @JsonProperty("prompt_tokens")
        Integer tokens;
        @JsonProperty("completion_tokens")
        Integer completionTokens;
        @JsonProperty("total_tokens")
        Integer totalTokens;
    }

}
