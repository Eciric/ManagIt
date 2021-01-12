package res.managit.dbo;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import res.managit.dbo.entity.Category;
import res.managit.dbo.entity.Contact;
import res.managit.dbo.entity.Customer;
import res.managit.dbo.entity.Event;
import res.managit.dbo.entity.EventItem;
import res.managit.dbo.entity.Product;
import res.managit.dbo.entity.Supply;
import res.managit.dbo.entity.Worker;
import res.managit.dbo.relations.manytomany.cross.EventCustomerCross;
import res.managit.dbo.relations.manytomany.cross.EventSupplyCross;
import res.managit.dbo.relations.manytomany.cross.EventWorkerCross;
import res.managit.dbo.relations.onetoone.EventItemProduct;

public abstract class DatabaseFunctions {
    static String[] entities = {"Category", "Contact", "Customer", "EventItem", "Event", "Product", "Supply",
            "Worker", "EventCustomerCross", "EventSupplyCross", "EventWorkerCross"};

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
                                    String[] workers = tempNames[3].split(",");
                                    String[] suppliers = tempNames[4].split(",");
                                    String[] customers = tempNames[5].split(",");



                                    Event event = new Event(localDateTime, tempNames[2], convertStrLong(workers), convertStrLong(suppliers), convertStrLong(customers), false);
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
                                }  else if (entity == "EventSupplyCross") {
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


    public static void uploadDatabaseBackUp(){
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance("https://managit-2df96-default-rtdb.firebaseio.com/");
        Thread saveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(String entity : entities){
                    DatabaseReference reference = database.getReference(PublicDatabaseAcces.currentDatabaseName+'/'+entity);
                    if (entity == "Category") {
                        reference.setValue(PublicDatabaseAcces.currentDatabase.categoryDao().getAll());
                    } else if (entity == "Contact") {
                        reference.setValue(PublicDatabaseAcces.currentDatabase.contactDao().getAll());
                    } else if (entity == "Customer") {
                        reference.setValue(PublicDatabaseAcces.currentDatabase.customerDao().getAll());
                    } else if(entity == "EventItem"){
                        reference.setValue(PublicDatabaseAcces.currentDatabase.eventItemDao().getAll());
                    }
                    else if (entity == "Event") {
                        reference.setValue(PublicDatabaseAcces.currentDatabase.eventDao().getAll());
                    } else if (entity == "Product") {
                        reference.setValue(PublicDatabaseAcces.currentDatabase.productDao().getAll());
                    } else if (entity == "Supply") {
                        reference.setValue(PublicDatabaseAcces.currentDatabase.supplyDao().getAll());
                    } else if (entity == "Worker") {
                        reference.setValue(PublicDatabaseAcces.currentDatabase.workerDao().getAll());
                    }
                }
            }
        });

        saveThread.start();
        try {
            saveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static void downloadDatabaseBackUp(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance("https://managit-2df96-default-rtdb.firebaseio.com/");
        Thread thread = new Thread(()-> {
            for(String entity : entities){
                DatabaseReference reference = database.getReference(PublicDatabaseAcces.currentDatabaseName+'/'+entity);
                if (entity == "Category") {
                    PublicDatabaseAcces.currentDatabase.categoryDao().deleteAll();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snap : snapshot.getChildren()){
                                Category category = snap.getValue(Category.class);
                                Executors.newSingleThreadExecutor().execute(()->{
                                    PublicDatabaseAcces.currentDatabase.categoryDao().insertCategory(category);
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else if (entity == "Contact") {
                    PublicDatabaseAcces.currentDatabase.contactDao().deleteAll();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snap : snapshot.getChildren()){
                                Contact contact = snap.getValue(Contact.class);
                                Executors.newSingleThreadExecutor().execute(()->{
                                    PublicDatabaseAcces.currentDatabase.contactDao().insertContact(contact);
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else if (entity == "Customer") {
                    PublicDatabaseAcces.currentDatabase.customerDao().deleteAll();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snap : snapshot.getChildren()){
                                Customer customer = snap.getValue(Customer.class);
                                Executors.newSingleThreadExecutor().execute(()->{
                                    PublicDatabaseAcces.currentDatabase.customerDao().insertCustomer(customer);
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else if(entity == "EventItem"){
                    PublicDatabaseAcces.currentDatabase.eventItemDao().deleteAll();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snap : snapshot.getChildren()){
                                EventItem eventItem = snap.getValue(EventItem.class);
                                Executors.newSingleThreadExecutor().execute(()->{
                                    PublicDatabaseAcces.currentDatabase.eventItemDao().insertEventItem(eventItem);
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                else if (entity == "Event") {
                    PublicDatabaseAcces.currentDatabase.eventDao().deleteAll();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snap : snapshot.getChildren()){
                                String str = snap.getValue().toString();
                                System.out.println("STR: " + str);
                                String[] variables = str.split(",");


                                String hour = variables[1].split("=")[1];
                                if(Integer.parseInt(hour) < 10)
                                    hour = "0" + hour;
                                String minutes = variables[10].split("=")[1];
                                if(Integer.parseInt(minutes) < 10)
                                    minutes = "0" + minutes;
                                String day = variables[3].split("=")[1];
                                if(Integer.parseInt(day) < 10)
                                    day = "0" + day;
                                String month = variables[6].split("=")[1];
                                if(Integer.parseInt(month) < 10)
                                    month = "0"+month;
                                String year = variables[5].split("=")[1];
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                                LocalDateTime date = LocalDateTime.parse(day + "-" + month + "-" + year + " " + hour + ":" + minutes,formatter);
                                long eventId = Long.parseLong(variables[12].split("=")[1]);
                                boolean isExecuted = variables[13].contains("true");
                                String action = variables[14].split("=")[1];
                                String[] customers = new String[0];
                                String[] suppliers = new String[0];
                                String[] workers = new String[0];
                                List<Long> customer_Id = new ArrayList<>();
                                List<Long> supplier_Id = new ArrayList<>();
                                List<Long> worker_Id = new ArrayList<>();

                                int temp = 0;
                                Matcher m = Pattern.compile("\\[(.*?)\\]").matcher(str);
                                while(m.find()) {
                                    System.out.println("|"+m.group(1)+"|");
                                    if(action.equals("loading")){
//                                    suppliers jest puste
                                        if(temp == 0){
                                            customers = m.group(1).split(",");
                                            temp++;
                                        }
                                        else {
                                            workers = m.group(1).split(",");
                                        }
                                    }
                                    else{
//                                        customers jest puste
                                        if(temp == 0){
                                            suppliers = m.group(1).split(",");
                                            temp++;
                                        }
                                        else {
                                            workers = m.group(1).split(",");
                                        }
                                    }
                                }

                                for(String s : customers){
                                    s = s.replaceAll(" ","");
                                    customer_Id.add(Long.parseLong(s));
                                }
                                for(String s : suppliers){
                                    s = s.replaceAll(" ","");
                                    supplier_Id.add(Long.parseLong(s));
                                }
                                for(String s : workers){
                                    s = s.replaceAll(" ","");
                                    worker_Id.add(Long.parseLong(s));
                                }
                                Event event = new Event(eventId,date,action,worker_Id,supplier_Id,customer_Id,isExecuted);
                                Executors.newSingleThreadExecutor().execute(()->{
                                    PublicDatabaseAcces.currentDatabase.eventDao().insertEvent(event);
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else if (entity == "Product") {
                    PublicDatabaseAcces.currentDatabase.productDao().deleteAll();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snap : snapshot.getChildren()){
                                Product product = snap.getValue(Product.class);
                                Executors.newSingleThreadExecutor().execute(()->{
                                    PublicDatabaseAcces.currentDatabase.productDao().insertProduct(product);
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else if (entity == "Supply") {
                    PublicDatabaseAcces.currentDatabase.supplyDao().deleteAll();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snap : snapshot.getChildren()){
                                Supply supply = snap.getValue(Supply.class);
                                Executors.newSingleThreadExecutor().execute(()->{
                                    PublicDatabaseAcces.currentDatabase.supplyDao().insertSupply(supply);
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else if (entity == "Worker") {
                    PublicDatabaseAcces.currentDatabase.workerDao().deleteAll();
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snap : snapshot.getChildren()){
                                Worker worker = snap.getValue(Worker.class);
                                Executors.newSingleThreadExecutor().execute(()->{
                                    PublicDatabaseAcces.currentDatabase.workerDao().insertWorker(worker);
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}



