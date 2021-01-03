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
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.transform.Source;

import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.EventItem;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;
import res.managit.dbo.relations.manytomany.EventWorker;
import res.managit.dbo.relations.manytomany.cross.EventCustomerCross;
import res.managit.dbo.relations.manytomany.cross.EventProductCross;
import res.managit.dbo.relations.manytomany.cross.EventSupplyCross;
import res.managit.dbo.relations.manytomany.cross.EventWorkerCross;

public abstract class DatabaseFunctions {
    static String[] entities = {"Category", "Contact", "Customer", "Event", "Product", "Supply",
            "Worker", "EventCustomerCross", "EventProductCross", "EventSupplyCross", "EventWorkerCross"};

    public static WarehouseDb createDatabase(Context context, String name) {
        if (PublicDatabaseAcces.databaseNameList.contains(name))
            return null;

        WarehouseDb Db = Room.databaseBuilder(context, WarehouseDb.class, name).build();
        PublicDatabaseAcces.databaseList.add(Db);
        PublicDatabaseAcces.databaseNameList.add(name);
        return Db;
    }

    public static void reloadDatabases(Context context) {
        for (String name : PublicDatabaseAcces.databaseNameList) {
            if (name.isEmpty())
                continue;
            WarehouseDb Db = Room.databaseBuilder(context, WarehouseDb.class, name).build();
            PublicDatabaseAcces.databaseList.add(Db);
        }
    }

    public static void reloadDatabaseNames(Context context, String path) {

        try {
            FileInputStream fis = context.openFileInput(path);
            StringBuffer fileContent = new StringBuffer("");
            byte[] bytes = new byte[1024];
            int n;
            while ((n = fis.read(bytes)) != -1) {
                fileContent.append(new String(bytes, 0, n));
            }
            fis.close();
            String[] tempNames = new String(fileContent).split(",");
            for (String s : tempNames) {
                if (s.isEmpty())
                    continue;
                PublicDatabaseAcces.databaseNameList.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDatabaseNames(Context context, String filePath) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(filePath, Context.MODE_PRIVATE);
            StringBuilder data = new StringBuilder();
            for (String s : PublicDatabaseAcces.databaseNameList) {
                data.append(s + ",");
            }
            fileOutputStream.write(data.toString().getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportDatabase(String dbName) {
        //sciezka do folderu na telefonie Environment.getDataDirectory()
        //tworzy nowy folder(jesli nie istnieje) dla bazy danych o danej nazwie
        File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), '/' + dbName);

        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        int dbIndex = PublicDatabaseAcces.databaseNameList.indexOf(dbName);
        WarehouseDb db = PublicDatabaseAcces.getDatabaseList().get(dbIndex);

        for (String entity : entities) {
            File file = new File(exportDir, '/' + entity + ".csv");
            try {
                file.createNewFile();
                CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
                Cursor curCSV = db.query("SELECT * FROM " + entity, null);
                csvWriter.writeNext(curCSV.getColumnNames());
                while (curCSV.moveToNext()) {
                    String arrStr[] = new String[curCSV.getColumnCount()];
                    for (int i = 0; i < curCSV.getColumnCount(); i++) {
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

    public static void importDatabase(String location, String dbName) {
        //todo pomyslec nad wyborem lokacji - wybieranie z listy folderow znajdujacych sie na smartphonie
        int dbIndex = PublicDatabaseAcces.databaseNameList.indexOf(dbName);
        WarehouseDb db = PublicDatabaseAcces.getDatabaseList().get(dbIndex);

        for (String entity : entities) {
            try {
                CSVReader csvReader = new CSVReader(new FileReader(location + entity + ".csv"));
                String[] nextLine;
                int count = 0;
                StringBuilder columns = new StringBuilder();

                while ((nextLine = csvReader.readNext()) != null) {
                    StringBuilder value = new StringBuilder();
                    for (int i = 0; i < nextLine.length; i++) {
                        if (count == 0) {
                            if (i == nextLine.length - 1)
                                columns.append(nextLine[i]);
                            else
                                columns.append(nextLine[i] + ",");
                        } else {
                            if (i == nextLine.length - 1) {
                                value.append(nextLine[i]);

                                String[] tempNames = new String(value).split("'");

                                if (entity == "Category") {
                                    Category category = new Category(tempNames[1]);
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        db.categoryDao().insertCategory(category);
                                    });
                                } else if (entity == "Contact") {

                                    Contact contact = new Contact(tempNames[1], Integer.parseInt(tempNames[2]), Integer.parseInt(tempNames[3]), tempNames[4], tempNames[5], tempNames[6]);
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        db.contactDao().insertContact(contact);
                                    });
                                } else if (entity == "Customer") {
                                    Customer customer = new Customer(tempNames[1], Long.parseLong(tempNames[2]));
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        db.customerDao().insertCustomer(customer);
                                    });
                                } else if(entity == "EventItem"){
                                    EventItem eventItem = new EventItem(Integer.parseInt(tempNames[1]),Long.parseLong(tempNames[2]),Long.parseLong(tempNames[3]));
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        db.eventItemDao().insertEventItem(eventItem);
                                    });
                                }
                                else if (entity == "Event") {
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm    dd-MM-yyyy");
                                    LocalDateTime localDateTime = LocalDateTime.parse(tempNames[1], formatter);
                                    String[] eventItems = tempNames[3].split(",");
                                    String[] workers = tempNames[4].split(",");
                                    String[] suppliers = tempNames[5].split(",");
                                    String[] customers = tempNames[6].split(",");
                                    String[] products = tempNames[7].split(",");


                                    Event event = new Event(localDateTime, tempNames[2], convertStrLong(eventItems), convertStrLong(workers), convertStrLong(suppliers), convertStrLong(customers), convertStrLong(products));
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        db.eventDao().insertEvent(event);
                                    });
                                } else if (entity == "Product") {
                                    Product product = new Product(tempNames[1], Integer.parseInt(tempNames[2]), Long.parseLong(tempNames[3]));
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        db.productDao().insertProduct(product);
                                    });
                                } else if (entity == "Supply") {
                                    Supply supply = new Supply(tempNames[1], Long.parseLong(tempNames[2]));
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        db.supplyDao().insertSupply(supply);
                                    });
                                } else if (entity == "Worker") {
                                    Worker worker = new Worker(tempNames[1], tempNames[2], tempNames[3], Long.parseLong(tempNames[4]));
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        db.workerDao().insertWorker(worker);
                                    });
                                } else if (entity == "EventCustomerCross") {
                                    EventCustomerCross eventCustomerCross = new EventCustomerCross(Long.parseLong(tempNames[0]), Long.parseLong(tempNames[1]));
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        db.eventCustomerCrossDao().insertEventCustomerCross(eventCustomerCross);
                                    });
                                } else if (entity == "EventProductCross") {
                                    EventProductCross eventProductCross = new EventProductCross(Long.parseLong(tempNames[0]), Long.parseLong(tempNames[1]));
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        db.eventProductCrossDao().insertEventProductCross(eventProductCross);
                                    });
                                } else if (entity == "EventSupplyCross") {
                                    EventSupplyCross eventSupplyCross = new EventSupplyCross(Long.parseLong(tempNames[0]), Long.parseLong(tempNames[1]));
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        db.eventSupplyCrossDao().insertEventSupplyCross(eventSupplyCross);
                                    });
                                } else if (entity == "EventWorkerCross") {
                                    EventWorkerCross eventWorkerCross = new EventWorkerCross(Long.parseLong(tempNames[0]), Long.parseLong(tempNames[1]));
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        db.eventWorkerCrossDao().insertEventWorkerCross(eventWorkerCross);
                                    });
                                }
                            } else
                                value.append(nextLine[i] + "'");
                        }
                    }
                    count = 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Long> convertStrLong(String[] array) {
        List<Long> digits = new ArrayList<>();
        if (array.length == 1 && array[0].equals("[]")) {
            return digits;
        }

        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                array[i] = array[i].substring(1);
            }
            if (i == array.length - 1) {
                int len = array[i].length();
                array[i] = array[i].substring(0, len - 1);
            }
            digits.add(Long.parseLong(array[i]));
        }
        return digits;
    }


}


