package de.diamondCoding.laserCore.configuration;

import com.google.gson.JsonElement;
import de.diamondCoding.laserCore.configuration.exceptions.ConfigValueCanNotBeParsedFromStringException;

public class PositiveIntValue extends IntValue {

    private PositiveIntValue(int value) {
        super(value);
    }

    public static PositiveIntValue fromJson(JsonElement jsonElement) {
        return new PositiveIntValue(jsonElement.getAsInt());
    }

    @Override
    public PositiveIntValue fromString(String input) throws ConfigValueCanNotBeParsedFromStringException {
        IntValue intValue = super.fromString(input);
        if(intValue.value < 0) {
            throw new ConfigValueCanNotBeParsedFromStringException(input, "The Input is not positive.");
        }
        return new PositiveIntValue(intValue.value);
    }

}
