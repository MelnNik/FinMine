package com.finmine.appuser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
@AllArgsConstructor
@Api(value = "User actions")
public class AppUserController {
    private final AppUserService appUserService;


    @RequestMapping(value = "/get_current_user", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Get a current user", response = Iterable.class)
    public Principal currentUserPrincipal(HttpServletRequest request) {
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
