package com.example.cashcard;

import com.example.cashcard.dto.CashCardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The @JsonTest annotation marks the CashCardJsonTest as a test class which uses the Jackson framework (which is included as part of Spring).
 */
@JsonTest
public class CashCardDTOJsonTest {

    /**
     * JacksonTester is a convenience wrapper to the Jackson JSON parsing library.
     * It handles serialization and deserialization of JSON objects.
     */
    @Autowired
    private JacksonTester<CashCardDTO> json;

    @Test
    void cashCardSerializationTest() throws IOException {
        CashCardDTO cashCardDTO = new CashCardDTO(99L, 123.45);
        assertThat(json.write(cashCardDTO)).isStrictlyEqualToJson("expected.json");
        assertThat(json.write(cashCardDTO)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(cashCardDTO)).extractingJsonPathNumberValue("@.id").isEqualTo(99);
        assertThat(json.write(cashCardDTO)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCardDTO)).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                  "id":99,
                  "amount":123.45
                }
                """;

        assertThat(json.parse(expected)).isEqualTo(new CashCardDTO(99L, 123.45));
        assertThat(json.parseObject(expected).getCashCardId()).isEqualTo(99);
        assertThat(json.parseObject(expected).getAmount()).isEqualTo(123.45);
    }
}
