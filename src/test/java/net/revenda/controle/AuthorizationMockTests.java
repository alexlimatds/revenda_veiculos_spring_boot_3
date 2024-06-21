package net.revenda.controle;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import net.revenda.WebSecurityMockConfig;

@SpringBootTest(classes = WebSecurityMockConfig.class)
@AutoConfigureMockMvc
public class AuthorizationMockTests {
    
    @Autowired
    private MockMvc mockMvc;

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
    @WithUserDetails(value = "ana")
    void modelosWhenAuthenticatedThenAuthorized() throws Exception {
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
    @WithUserDetails(value = "valdir")
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