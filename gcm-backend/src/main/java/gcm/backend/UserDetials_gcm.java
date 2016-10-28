package gcm.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by chandra on 6/6/16.
 */
@Entity
public class UserDetials_gcm {

    @Id
    Long id;

    @Index
    String regId;

    String name;
    String username;
    String password;
    String mobileNum;
    String email;
    Date MemberSince;

    public UserDetials_gcm() {}

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getMemberSince() {
        return MemberSince;
    }

    public void setMemberSince(Date memberSince) {
        MemberSince = memberSince;
    }

    public UserDetials_gcm(String name, String username, String password, String mobileNum, String email, Date memberSince) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.mobileNum = mobileNum;
        this.email = email;
        MemberSince = memberSince;
    }

    public UserDetials_gcm(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }


    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}