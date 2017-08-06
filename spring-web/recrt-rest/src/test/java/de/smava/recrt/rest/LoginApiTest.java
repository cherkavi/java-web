package de.smava.recrt.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smava.recrt.model.AppUser;
import de.smava.recrt.persistence.model.AppUserEntity;
import de.smava.recrt.rest.config.SecurityConfig;
import de.smava.recrt.rest.config.WebMvcConfig;
import de.smava.recrt.rest.model.LoginResource;
import de.smava.recrt.service.AppUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestApiConfig.class, WebMvcConfig.class, SecurityConfig.class})
public class LoginApiTest {

    @Autowired
    AppUserService appUserService;

    @Autowired
    private WebApplicationContext webApplicationContext; // cached

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLogin() throws Exception {

        AppUser appUser = new AppUserEntity();
        appUser.setUsername("user1");
        appUser.setPassword("1111");

        expect(appUserService.get(anyString())).andReturn(appUser).once();
        replay(appUserService);

        ObjectMapper mapper = new ObjectMapper();

        MvcResult result = mockMvc.perform(post("/rest/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new LoginResource("user1", "1111"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(
                mapper.writeValueAsString(new LoginResource(true, "user1")),
                result.getResponse().getContentAsString());
    }
}