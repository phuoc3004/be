package com.example.PBL6.persistance.user;

import com.example.PBL6.util.UserGenderDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = UserGenderDeserializer.class)
public enum UserGender {
    MALE, FEMALE, OTHER, UNSPECIFIED
}
