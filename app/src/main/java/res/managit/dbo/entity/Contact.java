package res.managit.dbo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
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
public class Contact {
    @PrimaryKey(autoGenerate = true)
    public long contactId;
    public String streetName;
    public int streetNumber;
    public int postalCode;
    public String city;
    public String country;
    public String phoneNumber;

    public Contact(String streetName, int streetNumber, int postalCode, String city, String country, String phoneNumber) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
    }
}
