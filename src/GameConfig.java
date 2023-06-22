import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GameConfig {
    private static final String CONFIG_FILE = "./src/config.properties";
    private Properties properties;

    public GameConfig() {
        loadProperties();
    }

    private void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getScoreRequiredForLevelComplete() {
        return Integer.parseInt(properties.getProperty("scoreRequiredForLevelComplete"));
    }

    public int getScoreRequirementIncrease(){
        return Integer.parseInt(properties.getProperty("scoreRequirementIncrease"));
    }

    public int getGapSize(){
        return Integer.parseInt(properties.getProperty("gapSize"));
    }

    public float getGravity(){
        return Float.parseFloat(properties.getProperty("gravity"));
    }

    public float getFlapStrength(){
        return Float.parseFloat(properties.getProperty("flapStrength"));
    }

    public int getWindowHeight(){
        return Integer.parseInt(properties.getProperty("windowHeight"));
    }

    public int getWindowWidth(){
        return Integer.parseInt(properties.getProperty("windowWidth"));
    }

    public long[] getLevelSeeds() {
        String levelSeedsString = properties.getProperty("levelSeeds");
        String[] seedStrings = levelSeedsString.split(",");
        long[] levelSeeds = new long[seedStrings.length];
        for (int i = 0; i < seedStrings.length; i++) {
            levelSeeds[i] = Long.parseLong(seedStrings[i].trim());
        }
        return levelSeeds;
    }
}
