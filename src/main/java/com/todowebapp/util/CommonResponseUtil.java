package com.todowebapp.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@ApiModel
@Builder
@AllArgsConstructor
public class CommonResponseUtil<T> {

    @ApiModelProperty(value = "응답 상태 코드", example = "200")
    private int status = HttpStatus.OK.value();

    @ApiModelProperty(value = "프로세스 처리 결과 코드", example = "0000")
    private String resultCode;

    @ApiModelProperty(value = "프로세스 처리 결과 메세지", example = "OK")
    private String resultMsg;

    @ApiModelProperty(value = "응답 데이터")
    private T data;

    @ApiModelProperty(value = "응답 일시", example = "yyyy-MM-dd kk:mm:ss")
    private String responseDateTime;

    public CommonResponseUtil(String resultCode){
        this.resultCode = resultCode;
        this.responseDateTime = LocalDate.now() + " " + LocalTime.now();
    }

    public CommonResponseUtil(String resultCode, String resultMsg, T resultObj){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.data = resultObj;
        this.responseDateTime = LocalDate.now() + " " + LocalTime.now();
    }

    public CommonResponseUtil(int status, String resultCode, String resultMsg, T resultObj){
        this.status = status;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.data = resultObj;
        this.responseDateTime = LocalDate.now() + " " + LocalTime.now();
    }
}
