package com.webapp.accompanyingparents.controller;

import com.webapp.accompanyingparents.config.security.jwt.JwtTokenUtil;
import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.repository.AccountRepository;
import com.webapp.accompanyingparents.view.dto.ApiMessageDto;
import com.webapp.accompanyingparents.view.dto.ErrorCode;
import com.webapp.accompanyingparents.view.dto.token.AuthenticationTokenDto;
import com.webapp.accompanyingparents.view.form.AuthenticationFrom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/token")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    AccountRepository accountRepository;

    /**
     * Đăng nhập tạo token
     */
    @PostMapping
    public ApiMessageDto<AuthenticationTokenDto> login(@RequestBody @Valid AuthenticationFrom request) {
        ApiMessageDto<AuthenticationTokenDto> apiMessageDto = new ApiMessageDto<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            Account user = (Account) authentication.getPrincipal();
            // Tạo token
            String accessToken = jwtTokenUtil.generateAccessToken(user);
            AuthenticationTokenDto authenticationTokenDto = new AuthenticationTokenDto();
            authenticationTokenDto.setToken(accessToken);
            authenticationTokenDto.setRole(user.getRole().getName());
            authenticationTokenDto.setEmail(user.getUsername());
            // setLastLogin
            Account account = accountRepository.findAccountByEmail(user.getUsername());
            account.setLastLogin(new Date());
            accountRepository.save(account);

            apiMessageDto.setResult(true);
            apiMessageDto.setData(authenticationTokenDto);
            return apiMessageDto;
        } catch (BadCredentialsException ex) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.UNAUTHORIZED);
            apiMessageDto.setMessage("Accessed Denied");
            return apiMessageDto;
        }
    }
}
