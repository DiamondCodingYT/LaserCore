package de.diamondCoding.laserCore.configuration.exceptions;

import lombok.Getter;

public class ConfigValueCanNotBeParsedFromStringException extends Exception {

    @Getter
    private final String reason;

    public ConfigValueCanNotBeParsedFromStringException(String input, String reason) {
        super("The Config Value could not be parsed from string (Input: "+input+"; Reason: "+reason+")");
        this.reason = reason;
    }

}
