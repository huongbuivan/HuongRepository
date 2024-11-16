package com.rdjava.springboot.exception;

import com.rdjava.springboot.constant.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AppException extends RuntimeException{
	@Builder.Default
	private final ResponseCode responseCode = ResponseCode.FAILED;
	@Builder.Default
	private final String errorMessage = ResponseCode.FAILED.getDescription();
}
