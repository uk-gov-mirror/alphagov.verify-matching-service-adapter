package uk.gov.ida.matchingserviceadapter.validators;

import org.beanplanet.messages.domain.Messages;
import org.beanplanet.validation.Validator;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import static java.util.function.Function.identity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.beanplanet.messages.domain.MessagesImpl.messages;
import static uk.gov.ida.matchingserviceadapter.validators.StringValidators.STRING_VALUE_IS_EMPTY;
import static uk.gov.ida.matchingserviceadapter.validators.StringValidators.STRING_VALUE_NOT_ENUMERATED;

public class StringValidatorsTest {

    @Test
    public void shouldGenerateNoErrorsWhenStringIsNonEmpty() {
        Validator<String> validator = StringValidators.isNonEmpty(identity());

        Messages messages = validator.validate("foo", messages());

        assertThat(messages.hasErrors()).isFalse();
    }

    @Test
    public void shouldGenerateErrorWhenStringIsNull() {
        Validator<String> validator = StringValidators.isNonEmpty(identity());

        Messages messages = validator.validate(null, messages());

        assertThat(messages.hasErrorLike(STRING_VALUE_IS_EMPTY)).isTrue();
    }

    @Test
    public void shouldGenerateNoErrorsWhenStringIsEnumerated() {
        Validator<String> validator = StringValidators.isOneOf(identity(), "foo", "bar");

        Messages messages = validator.validate("foo", messages());

        assertThat(messages.hasErrors()).isFalse();
    }

    @Test
    public void shouldGenerateErrorWhenStringIsNotEnumerated() {
        Validator<String> validator = StringValidators.isOneOf(identity(), "foo", "bar");

        Messages messages = validator.validate("baz", messages());

        assertThat(messages.hasErrorLike(STRING_VALUE_NOT_ENUMERATED)).isTrue();
    }

}