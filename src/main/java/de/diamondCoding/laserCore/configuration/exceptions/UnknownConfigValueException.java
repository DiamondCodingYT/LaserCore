package de.diamondCoding.laserCore.configuration.exceptions;

public class UnknownConfigValueException extends Exception {

    public UnknownConfigValueException(String name) {
        super("The requested Config Value does not exist (Name: "+name+")");
    }

}
