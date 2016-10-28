package application.backend;

/**
 * Created by chandra on 21/5/16.
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/** The Objectify object model for device registrations we are persisting */
@Entity
public class StoreToDatastore {
    @Id
    Long id;

    private String regId;
    // you can add more fields...

    public StoreToDatastore() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}
