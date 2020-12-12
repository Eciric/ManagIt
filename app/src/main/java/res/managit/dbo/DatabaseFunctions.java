package res.managit.dbo;

import android.content.Context;

import androidx.room.Room;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.transform.Source;

public abstract class DatabaseFunctions {
    public static WarehouseDb createDatabase(Context context, String name){
        if (PublicDatabaseAcces.databaseNameList.contains(name))
            return null;

        WarehouseDb Db = Room.databaseBuilder(context, WarehouseDb.class, name).build();
        PublicDatabaseAcces.databaseList.add(Db);
        PublicDatabaseAcces.databaseNameList.add(name);
        return Db;
    }
    public static void reloadDatabases(Context context){
        for(String name : PublicDatabaseAcces.databaseNameList){
            WarehouseDb Db = Room.databaseBuilder(context, WarehouseDb.class, name).build();
            PublicDatabaseAcces.databaseList.add(Db);
        }
    }
    public static void reloadDatabaseNames(Context context, String path){

        try {
            FileInputStream fis = context.openFileInput(path);
            StringBuffer fileContent = new StringBuffer("");
            byte[] bytes = new byte[1024];
            int n;
            while ((n = fis.read(bytes)) != -1){
                fileContent.append(new String(bytes,0,n));
            }
            fis.close();
            String[] tempNames = new String(fileContent).split(",");
            for(String s : tempNames){
                PublicDatabaseAcces.databaseNameList.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDatabaseNames(Context context, String filePath){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(filePath, Context.MODE_PRIVATE);
            StringBuilder data = new StringBuilder();
            for(String s : PublicDatabaseAcces.databaseNameList){
                data.append(s+",");
            }
            fileOutputStream.write(data.toString().getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
