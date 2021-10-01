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
        Principal principal = request.getUserPrincipal();
        // AppUser appUser = appUserRepository.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("Email is not found"));
        //return String.valueOf(appUser.getAppUserRole());
        return principal;
    }

    @GetMapping("/secret")
    public String secret(@RequestParam String lol) {
        return lol.toLowerCase();
    }


    // set subscribed/user roles
    @PostMapping("/role/setrole")
    public ResponseEntity<?> setRole(@RequestBody String role) {
        return ResponseEntity.ok().build();
    }


    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("b4dc9ad7992de87ce0d39d268086a7f41636b36b5a57754da8c67ead991621fd".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String email = decodedJWT.getSubject();
                AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email is not found"));

                String accessToken = JWT.create().withSubject(appUser.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString()).withClaim("appUserRole", appUser.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.toList())).sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);

                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                // response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());

                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }


        } else {
            throw new RuntimeException("Refresh token is missing");
        }


    }

}
