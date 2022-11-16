package com.example.UserLoginAndRegistration.appuser;

import com.example.UserLoginAndRegistration.registration.token.ConfirmationToken;
import com.example.UserLoginAndRegistration.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppUserRepository appUserRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser){
       boolean userExist = appUserRepository
               .findByEmail(appUser.getEmail())
               .isPresent();

       if(userExist){
           // TODO: if not not confirmed send confirmation email

           throw new IllegalStateException("email already taken");
       }
         String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

       appUser.setPassword(encodedPassword);
       appUserRepository.save(appUser);
        String token = UUID.randomUUID().toString();

       //TODO: SEND CONFIRMATION TOKEN

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //TODO: SEND EMAIL
        return token;
    }
    public void enableAppUser(String email) {
        appUserRepository.enableAppUser(email);
    }
}
