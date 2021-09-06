package br.com.anderson.costa.desafio.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {

    @JsonProperty("status_code")
    private int statusCode;
    private String message;
}
