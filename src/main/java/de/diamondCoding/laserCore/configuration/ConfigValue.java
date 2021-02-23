package de.diamondCoding.laserCore.configuration;

import com.google.gson.JsonElement;
import de.diamondCoding.laserCore.configuration.exceptions.ConfigValueCanNotBeParsedFromStringException;

public abstract class ConfigValue {

    public abstract JsonElement buildJson();

    public abstract ConfigValue fromString(String input) throws ConfigValueCanNotBeParsedFromStringException;

    public abstract String getDisplayableValueString();

}
