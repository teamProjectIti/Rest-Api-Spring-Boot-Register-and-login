package com.spacek.Payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
abstract public class BaseResponse<T> {
    protected T data;
    protected String status;
    protected String message;

    public BaseResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public BaseResponse(String status) {
        this.status = status;
    }
}
