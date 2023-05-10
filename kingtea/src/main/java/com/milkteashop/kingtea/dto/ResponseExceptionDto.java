package com.milkteashop.kingtea.dto;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseExceptionDto {
	private String message;
	private String path;
	private HttpStatus status;
	private Date date;
}
