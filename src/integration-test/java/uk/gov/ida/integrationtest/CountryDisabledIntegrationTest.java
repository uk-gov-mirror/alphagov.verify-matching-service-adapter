package uk.gov.ida.integrationtest;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;
import uk.gov.ida.integrationtest.helpers.MatchingServiceAdapterAppRule;
import uk.gov.ida.matchingserviceadapter.MatchingServiceAdapterConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryDisabledIntegrationTest {

    @ClassRule
    public static final DropwizardAppRule<MatchingServiceAdapterConfiguration> applicationRule = new MatchingServiceAdapterAppRule(true,
            ConfigOverride.config("europeanIdentity.enabled", "false"),
            ConfigOverride.config("europeanIdentity.metadata.trustStore.path", "/file/doesNotExist"));

    @Test
    public void shouldNotFetchCountryMetadataWhenCountryConfigIsPresentButDisabled() {
        assertThat(applicationRule.getEnvironment().healthChecks().getNames()).doesNotContain("CountryMetadataHealthCheck");
    }
}
