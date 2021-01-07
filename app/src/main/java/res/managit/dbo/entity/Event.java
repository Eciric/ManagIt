package res.managit.dbo.entity;

import androidx.room.*;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(indices = @Index(value = {"date","action", "worker_Id", "supplier_Id", "customer_Id"} , unique = true))
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Event {
    @PrimaryKey(autoGenerate = true)
    public long eventId;
    public LocalDateTime date;
    public String action;
    public List<Long> worker_Id;
    public List<Long> supplier_Id;
    public List<Long> customer_Id;
    public boolean isExecuted;


    public Event(LocalDateTime date, String action, List<Long> worker_Id, List<Long> supplier_Id, List<Long> customer_Id, boolean isExecuted) {
        this.date = date;
        this.action = action;
        this.worker_Id = worker_Id;
        this.supplier_Id = supplier_Id;
        this.customer_Id = customer_Id;
        this.isExecuted = isExecuted;
    }

    public long getEventId() {
        return eventId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<Long> getWorker_Id() {
        return worker_Id;
    }

    public void setWorker_Id(List<Long> worker_Id) {
        this.worker_Id = worker_Id;
    }

    public List<Long> getSupplier_Id() {
        return supplier_Id;
    }

    public void setSupplier_Id(List<Long> supplier_Id) {
        this.supplier_Id = supplier_Id;
    }

    public List<Long> getCustomer_Id() {
        return customer_Id;
    }

    public void setCustomer_Id(List<Long> customer_Id) {
        this.customer_Id = customer_Id;
    }

}
