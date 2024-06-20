package net.revenda;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;

//@Configuration
public class WebSecurityConfigMock {
    
    //@Bean
    //@Primary
    public UserDetailsService userDetailsService(){
        return Mockito.mock(UserDetailServiceImpl.class);
    }
}
