package br.com.anderson.costa.desafio.config.security;

import br.com.anderson.costa.desafio.model.entity.Profile;
import br.com.anderson.costa.desafio.model.entity.User;
import br.com.anderson.costa.desafio.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository repository;

    private AuthenticationService authenticationService;

    @Test
    public void mustReturnUserLoggedByUsername() {
        User user = getUser();

        authenticationService = new AuthenticationService(repository);
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        final UserDetails userDetails = authenticationService.loadUserByUsername("email@email.com");
        Assertions.assertEquals(user.getName(), userDetails.getUsername());
    }

    @Test
    public void mustReturnExceptionWhenUserNotFound() {
        authenticationService = new AuthenticationService(repository);
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> authenticationService.loadUserByUsername("email@email.com"));
    }

    private User getUser() {
        User user = new User();
        user.setName("Name");
        user.setEmail("email@email.com");
        user.setPassword("passwrod");
        Profile profile = new Profile();
        profile.setId(1L);
        profile.setName("USER");
        List<Profile> profiles = new ArrayList<>();
        profiles.add(profile);
        user.setProfiles(profiles);
        return user;
    }
}
