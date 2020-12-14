 package res.managit.dbo;

import android.content.Context;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Environment;
import android.util.Log;

import androidx.room.Room;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.transform.Source;

import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;
import res.managit.dbo.relations.manytomany.EventWorker;
import res.managit.dbo.relations.manytomany.cross.EventCustomerCross;
import res.managit.dbo.relations.manytomany.cross.EventProductCross;
import res.managit.dbo.relations.manytomany.cross.EventSupplyCross;
import res.managit.dbo.relations.manytomany.cross.EventWorkerCross;

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

    public static void exportDatabase(String dbName){
        //sciezka do folderu na telefonie Environment.getDataDirectory()
        //tworzy nowy folder(jesli nie istnieje) dla bazy danych o danej nazwie
        File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),'/'+dbName);

        if(!exportDir.exists()) {
            exportDir.mkdirs();
        }
        String[] entities = {"Category", "Contact", "Customer", "Event", "Product", "Supply",
                "Worker", "EventCustomerCross", "EventProductCross", "EventSupplyCross", "EventWorkerCross"};
        int dbIndex = PublicDatabaseAcces.databaseNameList.indexOf(dbName);
        WarehouseDb db = PublicDatabaseAcces.getDatabaseList().get(dbIndex);

        for(String entity : entities){
            File file = new File(exportDir,'/'+entity + ".csv");
            try {
                file.createNewFile();
                CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
                Cursor curCSV = db.query("SELECT * FROM " + entity,null);
                csvWriter.writeNext(curCSV.getColumnNames());
                while(curCSV.moveToNext()){
                    String arrStr[] = new String[curCSV.getColumnCount()];
                    for(int i = 0; i<curCSV.getColumnCount();i++){
                        arrStr[i] = curCSV.getString(i);
                    }
                    csvWriter.writeNext(arrStr);
                }
                csvWriter.close();
                curCSV.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}


