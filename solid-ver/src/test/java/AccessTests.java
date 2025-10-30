/*
  @author Orynchuk
  @project moneycare
  @class AccessTests
  @version 1.0.0
  @since 30.10.2025 - 23.10
*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Income.DemoApplication.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class AccessTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeAll() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithAnonymousUser
    void whenAnonymThenStatusUnautorized() throws Exception {
        mockMvc.perform(get("/api/v1/incomes"))
                .andExpect(status().isUnauthorized());
    }

    // GET ALL (everyone)

    @Test
    @WithMockUser(username = "user", password = "user", roles = {"USER"})
    void whenUserGetAllIncomesThenStatusOk() throws Exception {
        mockMvc.perform(get("/api/v1/incomes"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void whenAdminGetAllThenOk() throws Exception {
        mockMvc.perform(get("/api/v1/incomes"))
                .andExpect(status().isOk());
    }

    // GET BY ID (admin and superadmin)

    @Test
    @WithMockUser(username = "user", password = "user", roles = {"USER"})
    void whenUserGetByIdThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/incomes/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void whenAdminGetByIdThenOk() throws Exception {
        mockMvc.perform(get("/api/v1/incomes/2"))
                .andExpect(status().isOk());
    }

    // CREATE (admin and superadmin)

    @Test
    @WithMockUser(username = "user", password = "user", roles = {"USER"})
    void whenUserCreateThenForbidden() throws Exception {
        String json = """
            {"title":"Test income","amount":1000}
        """;

        mockMvc.perform(post("/api/v1/incomes")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenAdminCreateThenOk() throws Exception {
        String json = """
            {"title":"Test income","amount":1000}
        """;

        mockMvc.perform(post("/api/v1/incomes")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }

    // UPDATE BY ID (admin and superadmin)

    @Test
    @WithMockUser(username = "user", password = "user", roles = {"USER"})
    void whenUserUpdateThenForbidden() throws Exception {
        String json = """
            {"title":"Test income","amount":1000}
        """;

        mockMvc.perform(put("/api/v1/incomes/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void whenAdminUpdateThenOk() throws Exception {
        String json = """
            {"title":"Test income","amount":1000}
        """;

        mockMvc.perform(put("/api/v1/incomes/2")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "superadmin", password = "superadmin", roles = {"SUPERADMIN"})
    void whenSuperadminUpdateThenOk() throws Exception {
        String json = """
            {"title":"Test income","amount":1000}
        """;

        mockMvc.perform(put("/api/v1/incomes/3")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }

    // DELETE BY ID (only superadmin)

    @Test
    @WithMockUser(username = "user", password = "user", roles = {"USER"})
    void whenUserDeleteThenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/incomes/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void whenAdminDeleteThenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/incomes/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "superadmin", password = "superadmin", roles = {"SUPERADMIN"})
    void whenSuperAdminDeleteThenOk() throws Exception {
        mockMvc.perform(delete("/api/v1/incomes/1"))
                .andExpect(status().isNoContent());
    }
}
