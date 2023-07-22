package com.sqs.spring.restcrud.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*

Aspect Oriented Programming (AOP) : @ControllerAdvice
 
O código no qual a AOP propõe-se a manter, não faz parte do domínio do sistema. 
Portanto, é considerado um requisito sistêmico, como: Auditoria, Controle de Falhas, etc.

Aspect: É a unidade modular que encapsula um interesse que atravessa vários 
objetos dentro do sistema.

Join Point: É um ponto durante a execução do programa que será afetado pelo 
aspecto. Este ponto pode ser a execução de um método, a modificação do valor 
de uma variável ou outros.

Advice: É a ação executada pelo aspecto em um join point particular. 

Pointcut: É um elemento do aspecto representado por uma expressão escrita 
em uma linguagem específica para a finalidade de identificar join points.

*/

@ControllerAdvice
public class StudentRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc) {

	StudentErrorResponse error = new StudentErrorResponse();

	error.setStatus(HttpStatus.NOT_FOUND.value());
	error.setMessage(exc.getMessage());
	error.setTimeStamp(System.currentTimeMillis());

	return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc) {

	StudentErrorResponse error = new StudentErrorResponse();

	error.setStatus(HttpStatus.BAD_REQUEST.value());
	error.setMessage(exc.getMessage());
	error.setTimeStamp(System.currentTimeMillis());

	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
