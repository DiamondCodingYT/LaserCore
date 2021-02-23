package de.diamondCoding.laserCore.configuration.exceptions;

public class WrongConfigValueTypeException extends Exception {

    public WrongConfigValueTypeException(String name, String typesSimpleName) {
        super("The requested Config Value is of the wrong type. (Name: '"+name+"'; Type: "+typesSimpleName+")");
    }

}
