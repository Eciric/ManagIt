package res.managit.dbo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Worker {
    @PrimaryKey(autoGenerate = true)
    public long workerId;
    @ColumnInfo(name = "worker_name")
    public String name;
    @ColumnInfo(name = "worker_lastName")
    public String lastName;
    public String role;
    public long contact_Id;

    public Worker(String name, String lastName, String role, long contact_Id) {
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.contact_Id = contact_Id;
    }
}
