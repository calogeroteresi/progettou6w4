package it.epicode.progettoSettimanale7.controllers;


import it.epicode.progettoSettimanale7.Models.DTO.CambiaRuolo;
import it.epicode.progettoSettimanale7.Models.DTO.UtenteDTO;
import it.epicode.progettoSettimanale7.Models.entities.CustomResponse;
import it.epicode.progettoSettimanale7.exceptions.BadRequestException;
import it.epicode.progettoSettimanale7.exceptions.ErrorResponse;
import it.epicode.progettoSettimanale7.exceptions.UnauthorizedException;
import it.epicode.progettoSettimanale7.security.JwtTools;
import it.epicode.progettoSettimanale7.servicies.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class UtenteController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTools jwtTools;


    @GetMapping("/utenti")
    public CustomResponse getAll(Pageable pageable){
        return new CustomResponse(HttpStatus.OK.toString(),authService.getAll(pageable));
    }

    @GetMapping("/utenti/{username}")
    public CustomResponse getByUsername(@PathVariable String username){
        return new CustomResponse(HttpStatus.OK.toString(), authService.getByUsername(username));
    }

    @PutMapping("/utenti/{username}")
    public CustomResponse update(@PathVariable String username, @RequestBody @Validated UtenteDTO utenteDTO, BindingResult bindingResult, @RequestHeader("Authorization") String jwt){
        if (bindingResult.hasErrors()) throw new BadRequestException(ErrorResponse.handleValidationMessages(bindingResult));
        checkUser(username, jwt);
        return new CustomResponse(HttpStatus.OK.toString(), authService.update(username, utenteDTO));
    }

    @PatchMapping("/utenti/{username}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public CustomResponse changeRole(@PathVariable String username, @RequestBody CambiaRuolo cambiaRuolo){
        return new CustomResponse(HttpStatus.OK.toString(), authService.changeRole(username, cambiaRuolo.getRuoloUtente()));
    }

    @DeleteMapping("/utenti/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CustomResponse delete(@PathVariable String username, @RequestHeader("Authorization") String jwt){
        checkUser(username, jwt);
        authService.delete(username);
        return new CustomResponse(HttpStatus.NO_CONTENT.toString(), null);
    }

    private void checkUser(String username, String jwt){
        if (!username.equals(jwtTools.extractUsernameFromToken(jwt.substring(7)))) throw new UnauthorizedException("Non sei autorizzato ad eseguire questa operazione");
    }
}
