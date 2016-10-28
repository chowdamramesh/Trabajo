package application.backend;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

import gcm.backend.UserDetials_gcm;

/**
 * Created by chandra on 9/6/16.
 */
@Entity
public class TrabajoMembers {

    @Id public Long tMemId;

    @Load
    Ref<UserDetials_gcm> userIdOfUsername;

    @Load
    Ref<UserDetials_gcm> MemberuserId;
}
