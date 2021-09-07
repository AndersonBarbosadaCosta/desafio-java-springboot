package br.com.anderson.costa.desafio.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageDTO {

    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("message")
    private String message;
}
