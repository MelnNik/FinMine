package com.finmine.registration;

import com.finmine.appuser.AppUser;
import com.finmine.appuser.AppUserRole;
import com.finmine.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private EmailValidator emailValidator;

    public void register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        appUserService.signUpUser(new AppUser(
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER
        ));
    }
}
