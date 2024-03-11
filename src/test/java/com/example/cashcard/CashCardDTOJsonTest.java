package com.example.cashcard;

import com.example.cashcard.dto.CashCardDTO;
import com.example.cashcard.entity.CashCard;
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
    private JacksonTester<CashCard> json;

    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = new CashCard(99L, 123.45);
        assertThat(json.write(cashCard)).isStrictlyEqualToJson("expected.json");
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.cashCardId");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.cashCardId").isEqualTo(99);
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                  "cashCardId":99,
                  "amount":123.45
                }
                """;

        assertThat(json.parse(expected)).isEqualTo(new CashCard(99L, 123.45));
        assertThat(json.parseObject(expected).getCashCardId()).isEqualTo(99);
        assertThat(json.parseObject(expected).getAmount()).isEqualTo(123.45);
    }
}
