package de.diamondCoding.laserCore.handlers;

import com.google.gson.*;
import de.diamondCoding.laserCore.LaserCore;
import de.diamondCoding.laserCore.configuration.ConfigValue;
import de.diamondCoding.laserCore.configuration.IntValue;
import de.diamondCoding.laserCore.configuration.PositiveIntValue;
import de.diamondCoding.laserCore.configuration.exceptions.UnknownConfigValueException;
import de.diamondCoding.laserCore.configuration.exceptions.WrongConfigValueTypeException;
import lombok.Getter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationHandler {

    private final Gson gson;
    private File configFile;
    @Getter
    private JsonObject configJson;
    @Getter
    private final boolean initSuccess;
    @Getter
    private final Map<String, ConfigValue> values;

    public ConfigurationHandler() {
        //create gson
        gson = new GsonBuilder().setPrettyPrinting().create();
        //init values map
        values = new HashMap<>();
        //set initSuccess to whatever loadConfig says
        initSuccess = loadConfig();
    }

    public boolean loadConfig() {
        //make sure DataFolder exists
        File dataFolder = LaserCore.getLaserCore().getDataFolder();
        if(!dataFolder.exists()) {
            try {
                boolean success = dataFolder.mkdir();
                if(!success) {
                    System.out.println("Cloud not create DataFolder directory");
                    return false;
                }
            } catch (SecurityException securityException) {
                System.out.println("Cloud not create DataFolder directory:");
                securityException.printStackTrace();
                return false;
            }
        }
        //make sure config.json exists
        configFile = new File(dataFolder.getAbsolutePath()+"/config.json");
        if(!configFile.exists()) {
            try {
                boolean success = configFile.createNewFile();
                if(!success) {
                    System.out.println("Cloud not create config.json");
                    return false;
                }
            } catch (SecurityException | IOException securityException) {
                System.out.println("Cloud not create config.json:");
                securityException.printStackTrace();
                return false;
            }
        }
        //read the file data into json object
        readConfigFile();
        //add defaults
        addDefaults();
        //write defaults
        writeConfigFile();
        //load values into variables
        loadValues();
        return true;
    }

    private void addDefaults() {
        if(!configJson.has("gunCooldown")) configJson.add("gunCooldown", new JsonPrimitive(1500));
    }

    public void loadValues() {
        values.put("gunCooldown", PositiveIntValue.fromJson(configJson.get("gunCooldown")));
    }

    public ConfigValue getValue(String name) throws UnknownConfigValueException {
        if(!values.containsKey(name)) throw new UnknownConfigValueException(name);
        return values.get(name);
    }

    public IntValue getIntValue(String name) throws WrongConfigValueTypeException, UnknownConfigValueException {
        ConfigValue value = getValue(name);
        if(!(value instanceof IntValue)) throw new WrongConfigValueTypeException(name, value.getClass().getSimpleName());
        return (IntValue) value;
    }

    private void readConfigFile() {
        String jsonString = "";
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonString += line;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        if(jsonString.equals("")) { //empty file
            configJson = new JsonObject();
        } else {
            configJson = new JsonParser().parse(jsonString).getAsJsonObject();
        }
    }

    public void writeConfigFile() {
        String jsonString = gson.toJson(configJson);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
            writer.write(jsonString);
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
