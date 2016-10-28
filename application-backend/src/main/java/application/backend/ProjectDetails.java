package application.backend;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

import java.util.Date;

import gcm.backend.UserDetials_gcm;

/**
 * Created by chandra on 7/6/16.
 */
@Entity
public class ProjectDetails {

    @Load
    Ref<UserDetials_gcm> userId;

    @Id public Long projId;

    String projName;
    String Desc;
    Date startDate;
    Date endDate;



    public ProjectDetails(String projName, String desc, Date startDate, Date endDate) {
        this.projName = projName;
        Desc = desc;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ProjectDetails() {
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
