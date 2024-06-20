package net.revenda.controle;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import net.revenda.MyUserDetail;
import net.revenda.dominio.Usuario;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationMockTests {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    void anyWhenUnauthenticatedThenUnauthorized() throws Exception {
        this.mockMvc
            .perform(get("/veiculos"))
            .andExpect(unauthenticated());
    }

    @Test
    void loginWhenUnauthenticatedThenAuthorized() throws Exception {
        this.mockMvc
            .perform(get("/login"))
            .andExpect(status().isOk());
    }

    @Test
    void stylesWhenUnauthenticatedThenAuthorized() throws Exception {
        this.mockMvc
            .perform(get("/styles.css"))
            .andExpect(status().isOk());
    }

    @Test
    void modelosWhenUnauthenticatedThenUnauthorized() throws Exception {
        this.mockMvc
            .perform(get("/modelos"))
            .andExpect(unauthenticated());
    }

    @Test
    //@WithUserDetails("user")
    void modelosWhenAuthenticatedThenAuthorized() throws Exception {
        // fixture
        Usuario usuario = new Usuario(null, "Nome Stub", null, null, null, null, null, null);
        Mockito.when(
            userDetailsService.loadUserByUsername(any())
        ).thenReturn(
            new MyUserDetail(
                "username", 
                "senha", 
                new ArrayList<SimpleGrantedAuthority>(), 
                usuario
            )
        );
        // assert
        this.mockMvc
            .perform(get("/modelos"))
            .andExpect(status().isOk());
    }

    @Test
    void logoutWhenUnauthenticatedThenRedirect() throws Exception {
        this.mockMvc
            .perform(post("/logout").with(csrf()))
            .andExpect(unauthenticated())
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void logoutWhenAuthenticatedThenUnauthenticated() throws Exception {
        this.mockMvc
            .perform(post("/logout").with(csrf()))
            .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser(roles={"GERENTE"})
    void accessUsuariosPageWhenAuthorized() throws Exception {
        this.mockMvc
            .perform(get("/usuarios"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "ana")
    void accessUsuariosPageWhenUnauthorized() throws Exception {
        this.mockMvc
            .perform(get("/usuarios"))
            .andExpect(status().isForbidden());
    }
}