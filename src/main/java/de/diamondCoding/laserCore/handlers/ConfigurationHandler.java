package de.diamondCoding.laserCore.handlers;

import com.google.gson.*;
import de.diamondCoding.laserCore.LaserCore;
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
    private final Map<String, Object> values;

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
        values.put("gunCooldown", configJson.get("gunCooldown").getAsInt());
    }

    public Object getValue(String name) {
        return values.get(name);
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
