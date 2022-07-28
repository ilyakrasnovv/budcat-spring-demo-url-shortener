package ru.ilkras.budcat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.ilkras.budcat.api.MainRouting;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(
        listeners = {
                DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionalTestExecutionListener.class,
        }
)
@AutoConfigureMockMvc
class BudcatApplicationTests {
    private BudcatApplication app = new BudcatApplication();
    private MainRouting mainRouting = new MainRouting();
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    private MockMvc mockMvc;

    public BudcatApplicationTests() throws IOException {
        mockMvc = MockMvcBuilders.standaloneSetup(new MainRouting()).build();
    }

    @Test
    void contextLoads() {
        assertThat(mainRouting).isNotNull();
    }

    @Test
    void testHomePageViaMockMvc() throws Exception {
        String homePageLocation =
        mockMvc
                .perform(get("/"))
                .andDo(print())
                .andExpect(status().isFound())
                .andReturn()
                .getResponse().getHeader("Location");
        assertThat(homePageLocation).isEqualTo("/index.html");
    }

    String generateUrl() {
        return "https://" + RandomStringUtils.randomAlphabetic(5) + ".com";
    }

    Long getId(String response) {
        String toSearch = "0.0.0.0:8080/u/";
        Integer indexOfToSearch = response.indexOf(toSearch);
        int indexOfEndBraces = response.indexOf("\"}");
        Integer toSearchLength = toSearch.length();
        String idToParse = response.substring(indexOfToSearch + toSearchLength, indexOfEndBraces);
        return Long.parseLong(idToParse);
    }

    @DirtiesContext
    @RepeatedTest(5)
    void tryToShortenAndExpand() {
        String url = generateUrl();
        String serverUrl = "0.0.0.0:" + port;
        String ans = restTemplate.getForObject(
                "http://" + serverUrl + "/url/shorten?longUrl=" + url, String.class
        );
        Long id = getId(ans);
        assertThat(restTemplate.getForObject(
                "http://" + serverUrl + "/url/expand?id=" + id, String.class
        )).contains(url);
    }

    @DirtiesContext
    @RepeatedTest(5)
    void tryToShortenAndUseShortened() throws Exception {
        String url = generateUrl();
        String response = (
                mockMvc
                        .perform(get("/url/shorten").param("longUrl", url))
                        .andReturn()
        ).getResponse().getContentAsString();
        Long id = getId(response);
        String location =
        mockMvc
                .perform(get("/u/" + id))
                .andExpect(status().isMovedPermanently())
                .andReturn()
                .getResponse()
                .getHeader("Location");
        assertThat(location).isEqualTo(url);
    }
}
