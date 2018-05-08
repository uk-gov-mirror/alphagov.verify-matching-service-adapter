package uk.gov.ida.verifymatchingservicetesttool.scenarios;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.gov.ida.verifymatchingservicetesttool.configurations.ApplicationConfiguration;
import uk.gov.ida.verifymatchingservicetesttool.resolvers.ApplicationConfigurationResolver;
import uk.gov.ida.verifymatchingservicetesttool.resolvers.FileUtilsResolver;
import uk.gov.ida.verifymatchingservicetesttool.utils.FileUtils;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.time.Instant;

import static java.time.temporal.ChronoUnit.DAYS;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@ExtendWith(ApplicationConfigurationResolver.class)
@ExtendWith(FileUtilsResolver.class)
public class LevelOfAssuranceTwoScenario extends ScenarioBase {

    public LevelOfAssuranceTwoScenario(ApplicationConfiguration configuration, FileUtils fileUtils) {
        super(configuration, fileUtils);
    }

    @Test
    @DisplayName("Simple request with level of assurance 2")
    public void runForWhenAllElementsAreVerifiedAndNoMultipleValues() throws Exception {
        Response response = client.target(configuration.getLocalMatchingServiceMatchUrl())
            .request(APPLICATION_JSON)
            .post(Entity.json(fileUtils.readFromResources("LoA2-simple-case.json")));

        assertMatchNoMatch(response);
    }

    @Test
    @DisplayName("Complex request with level of assurance 2")
    public void runForComplexCase() throws Exception {
        String jsonString = fileUtils.readFromResources("LoA2-extensive-case.json")
            .replace("%yesterdayDate%", Instant.now().minus(1, DAYS).toString())
            .replace("%within405days-100days%", Instant.now().minus(100, DAYS).toString())
            .replace("%within405days-101days%", Instant.now().minus(150, DAYS).toString())
            .replace("%within405days-200days%", Instant.now().minus(400, DAYS).toString())
            .replace("%within180days-100days%", Instant.now().minus(100, DAYS).toString())
            .replace("%within180days-101days%", Instant.now().minus(140, DAYS).toString())
            .replace("%within180days-150days%", Instant.now().minus(160, DAYS).toString());

        Response response = client.target(configuration.getLocalMatchingServiceMatchUrl())
            .request(APPLICATION_JSON)
            .post(Entity.json(jsonString));

        assertMatchNoMatch(response);
    }
}
