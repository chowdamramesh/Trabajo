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
public class TaskDetails {
    @Load
    Ref<UserDetials_gcm> userId;

    @Load
    Ref<UserDetials_gcm> userIdFor;

    @Load
    Ref<ProjectDetails> projId;

    @Id
    public Long taskId;
    String taskName;
    String taskDesc;
    Date startDate;
    Date endDate;
    boolean end = false, submitt = false, accept = false;

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public boolean isSubmitt() {
        return submitt;
    }

    public void setSubmitt(boolean submitt) {
        this.submitt = submitt;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public TaskDetails(String taskName, String desc, Date startDate, Date endDate) {
        this.taskName = taskName;
        this.taskDesc = desc;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public TaskDetails() {
    }
    public Long getTaskId() {
        return taskId;
    }
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getTaskDesc() {
        return taskDesc;
    }
    public void setTaskDesc(String desc) {
        this.taskDesc = desc;
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

