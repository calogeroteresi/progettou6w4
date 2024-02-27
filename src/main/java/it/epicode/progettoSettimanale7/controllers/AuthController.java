package it.epicode.progettoSettimanale7.controllers;

import it.epicode.progettoSettimanale7.Models.DTO.LoginDTO;
import it.epicode.progettoSettimanale7.Models.DTO.UtenteDTO;
import it.epicode.progettoSettimanale7.Models.entities.CustomResponse;
import it.epicode.progettoSettimanale7.exceptions.BadRequestException;
import it.epicode.progettoSettimanale7.exceptions.ErrorResponse;
import it.epicode.progettoSettimanale7.servicies.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse register(@RequestBody @Validated UtenteDTO utenteDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(ErrorResponse.handleValidationMessages(bindingResult));
        return new CustomResponse(HttpStatus.CREATED.toString(), authService.save(utenteDTO));
    }

    @PostMapping("/auth/login")
    public CustomResponse login(@RequestBody @Validated LoginDTO loginDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(ErrorResponse.handleValidationMessages(bindingResult));
        return new CustomResponse(HttpStatus.OK.toString(), authService.login(loginDTO));
    }
}
