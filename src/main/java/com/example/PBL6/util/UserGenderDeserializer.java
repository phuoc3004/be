package com.example.PBL6.util;

import com.example.PBL6.persistance.user.UserGender;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class UserGenderDeserializer extends JsonDeserializer<UserGender> {
    @Override
    public UserGender deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String gender = jsonParser.getValueAsString().trim();
        if (gender.isEmpty()) {
            return UserGender.UNSPECIFIED;
        }
        try {
            return UserGender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UserGender.UNSPECIFIED;
        }
    }
}
