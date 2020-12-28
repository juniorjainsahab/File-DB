import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Database {

    private final File databaseRegistry;
    private final String DATABASE_FILE_NAME = "Database.txt";
    private String path = null;


    public Database() {
        this.databaseRegistry = new File(DATABASE_FILE_NAME);
    }

    public Database(String path) throws IOException {
        this.path = path + File.separator;
        File folder = new File(this.path);
        folder.mkdirs();
        databaseRegistry = new File(folder, DATABASE_FILE_NAME);
        databaseRegistry.createNewFile();
    }

    public boolean create(String key, String json) throws IOException {
        FileManager fileManager = new FileManager();
        String resourceFileName = key + ".txt";
        fileManager.write(databaseRegistry, key + ":" + resourceFileName, true);
        File resourceFile = createResourceFile(resourceFileName);

        fileManager.write(resourceFile, "{\"" + key + "\" : " + json + "}", false);
        String read = fileManager.read(resourceFile);
        return new ObjectMapper().readTree(read).get(key) != null;
    }

    private File createResourceFile(String resourceFileName) throws IOException {
        File resourceFile;
        if (path != null) {
            resourceFile = new File(path + resourceFileName);
            resourceFile.createNewFile();
            return resourceFile;
        }
        return new File(resourceFileName);
    }
}
