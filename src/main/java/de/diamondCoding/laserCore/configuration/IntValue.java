package de.diamondCoding.laserCore.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import de.diamondCoding.laserCore.configuration.exceptions.ConfigValueCanNotBeParsedFromStringException;
import lombok.Getter;

public class IntValue extends ConfigValue {

    @Getter
    final int value;
    IntValue(int value) {
        this.value = value;
    }

    @Override
    public JsonElement buildJson() {
        return new JsonPrimitive(value);
    }

    public static IntValue fromJson(JsonElement jsonElement) {
        return new IntValue(jsonElement.getAsInt());
    }

    @Override
    public IntValue fromString(String input) throws ConfigValueCanNotBeParsedFromStringException {
        int value;
        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException numberFormatException) {
            throw new ConfigValueCanNotBeParsedFromStringException(input, "Input could not be parsed as integer.");
        }
        return new IntValue(value);
    }

    @Override
    public String getDisplayableValueString() {
        return String.valueOf(value);
    }

}
