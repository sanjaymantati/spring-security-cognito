package com.photoapp.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
public class BaseResponseDto {

    protected String responseCode;

    protected String responseMessage;
    protected Object data;


    public BaseResponseDto(Object data) {
        this.data = data;
    }
}
