package net.crossager.suolib.files;

import com.google.gson.Gson;
import net.crossager.suolib.SuoLib;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.Objects;

public class ObjectJsonFileManager<T extends Object> {
    private final Class<T> type;
    private final File file;
    private final String name;
    private T data;
    private final Gson gson;
    private final Plugin plugin;

    public ObjectJsonFileManager(Class<T> clazz, File file, Plugin plugin){
        Objects.requireNonNull(plugin, "Plugin cannot be null");
        if(!file.isFile()){
            this.file = file;
            this.name = file.getName();
            this.type = clazz;
            this.plugin = plugin;
            this.gson = new Gson();
            reload();
        } else {
            throw new IllegalArgumentException("File cannot be a directory");
        }
    }

    public ObjectJsonFileManager(Class<T> clazz, File file) {
        this(clazz, file, SuoLib.getProvider().getPlugin());
    }

    public ObjectJsonFileManager(Class<T> clazz, String name, Plugin plugin){
        this(clazz, new File(plugin.getDataFolder(), name), plugin);
    }

    public T set(T data){
        T temp = data;
        this.data = data;
        return temp;
    }

    public T getData(){
        return data;
    }

    public boolean save() {
        try {
            if (!file.exists()) file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            out.write(gson.toJson(data).getBytes());
            out.flush();
            out.close();
            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public void reload() {
        try {
            FileInputStream in = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader breader = new BufferedReader(reader);

            StringBuilder builder = new StringBuilder();
            String line;
            while((line = breader.readLine()) != null){
                builder.append(line);
            }
            breader.close();
            reader.close();
            in.close();

            data = gson.fromJson(builder.toString(), type);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
