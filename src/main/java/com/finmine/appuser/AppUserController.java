package com.finmine.appuser;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static com.finmine.security.AuthenticationFilter.APPLICATION_JSON_VALUE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@AllArgsConstructor
public class AppUserController {

    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;

    // TODO: Move logic to appUserService
    @RequestMapping(value = "/get_current_user", method = RequestMethod.GET)
    @ResponseBody
    public Principal currentUserPrincipal(HttpServletRequest request) {
        // AppUser appUser = appUserRepository.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("Email is not found"));
        // return String.valueOf(appUser.getAppUserRole());
        // TODO: Check if subscribed(check for subscription status on stripe)
        return request.getUserPrincipal();
    }


    @RequestMapping("/setrole")
    public String setUserRole(@RequestBody String role) {
        return "role set";
    }


    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        appUserService.refreshToken(request, response);
    }

}
