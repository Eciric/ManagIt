package res.managit.dbo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;




import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;
import lombok.ToString;

@Entity(indices = @Index(value = {"streetName", "streetNumber", "postalCode", "city", "country", "phoneNumber"} , unique = true))
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

    public long getContactId() {
        return contactId;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
