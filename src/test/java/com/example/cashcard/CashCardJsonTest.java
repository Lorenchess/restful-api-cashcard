package com.example.cashcard;

import com.example.cashcard.entity.CashCard;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
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
public class CashCardJsonTest {

    /**
     * JacksonTester is a convenience wrapper to the Jackson JSON parsing library.
     * It handles serialization and deserialization of JSON objects.
     */
    @Autowired
    private JacksonTester<CashCard> json;

    @Autowired
    private JacksonTester<CashCard[]> jsonList;

    private CashCard[] cashCards;

    @BeforeEach
    void setUp() {
        cashCards = Arrays.array(
                new CashCard(99L, 123.45, "lorenchess"),
                new CashCard(100L, 1.00, "lorenchess"),
                new CashCard(101L, 150.00, "lorenchess"));
    }

    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = new CashCard(99L, 123.45, "lorenchess");
        assertThat(json.write(cashCard)).isStrictlyEqualToJson("expected.json");
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.cashCardId");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.cashCardId").isEqualTo(99);
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
        assertThat(json.write(cashCard)).hasJsonPathStringValue("@.owner");
        assertThat(json.write(cashCard)).extractingJsonPathStringValue("@.owner").isEqualTo("lorenchess");
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                  "cashCardId": 99,
                  "amount": 123.45,
                  "owner": "lorenchess"
                }
                """;

        assertThat(json.parse(expected)).isEqualTo(new CashCard(99L, 123.45,"lorenchess"));
        assertThat(json.parseObject(expected).getCashCardId()).isEqualTo(99);
        assertThat(json.parseObject(expected).getAmount()).isEqualTo(123.45);
        assertThat(json.parseObject(expected).getOwner()).isEqualTo("lorenchess");
    }

    @Test
    void cashCardListSerializationTest() throws IOException {
        assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson("list.json");
    }

    @Test
    void cashCardListDeserializationTest() throws IOException {
        String expected="""
         [
            { "cashCardId": 99, "amount": 123.45, "owner": "lorenchess" },
            { "cashCardId": 100, "amount": 1.00, "owner": "lorenchess" },
            { "cashCardId": 101, "amount": 150.00, "owner": "lorenchess" }
         ]
         """;
        assertThat(jsonList.parse(expected)).isEqualTo(cashCards);
    }


}
