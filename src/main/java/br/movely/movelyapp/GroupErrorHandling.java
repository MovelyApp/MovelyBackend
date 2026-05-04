package br.movely.movelyapp;


import br.movely.movelyapp.DTO.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GroupErrorHandling {

    @ExceptionHandler(GroupNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleGroupNotFoundException(GroupNotFoundException ex,
                                                 HttpServletRequest request) {

        log.error("Group not found");

        ErrorDTO errorDTO =  new ErrorDTO();
        errorDTO.setMensagem("Group not found");
        errorDTO.setData(LocalDateTime.now());
        errorDTO.setCodigoHttp(HttpStatus.NOT_FOUND.value());
        errorDTO.setCodigoErro("GROUP_NOT_FOUND");
        errorDTO.setPath(request.getRequestURI());
        return  errorDTO;

    }

    @ExceptionHandler(GroupAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorDTO handleGroupAlreadyExistsException(GroupAlreadyExistsException ex,
                                                      HttpServletRequest request) {

        ErrorDTO errorDTO =  new ErrorDTO();
        errorDTO.setMensagem("Group already exists");
        errorDTO.setData(LocalDateTime.now());
        errorDTO.setCodigoHttp(HttpStatus.CONFLICT.value());
        errorDTO.setCodigoErro("GROUP_ALREADY_EXISTS");
        errorDTO.setPath(request.getRequestURI());
        return  errorDTO;

    }


}
