/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package application.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import gcm.backend.UserDetials_gcm;

import static com.googlecode.objectify.ObjectifyService.ofy;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.application",
    ownerName = "backend.application",
    packagePath=""
  )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    @ApiMethod(name = "retrive")
    public List<StoreToDatastore> retrive() {
        List<String> names = new ArrayList<String>();
        Query<StoreToDatastore> query = ofy().load().type(StoreToDatastore.class);
        List<StoreToDatastore> records = new ArrayList<StoreToDatastore>();
        QueryResultIterator<StoreToDatastore> iterator = query.iterator();
        while (iterator.hasNext()) {
            records.add(iterator.next());
        }
        return records;
    }



    @ApiMethod(name = "retriveAccToUser")
    public List<ProjectDetails> retriveAccToUser(@Named("userName") String userName) {

        ObjectifyService.register(ProjectDetails.class);
        ObjectifyService.register(UserDetials_gcm.class);

        ProjectDetails objjj = new ProjectDetails();
        Query<ProjectDetails> query = ofy().load().type(ProjectDetails.class);
        QueryResultIterator<ProjectDetails> iterator = query.iterator();
        List<ProjectDetails> recc = new ArrayList<>();
        while (iterator.hasNext()) {
            recc.add(iterator.next());
        }

        Query<UserDetials_gcm> query_User = ofy().load().type(UserDetials_gcm.class);
        List<UserDetials_gcm> all = new ArrayList<>();
        QueryResultIterator<UserDetials_gcm> user_iterator = query_User.iterator();

        UserDetials_gcm temp = new UserDetials_gcm();

        List<ProjectDetails> finalList = new ArrayList<>();

        while (user_iterator.hasNext()) {
            temp = user_iterator.next();
            for (ProjectDetails aa: recc) {
                ProjectDetails fetched = ofy().load().entity(aa).now();
                UserDetials_gcm uObj = fetched.userId.get();
                if(uObj.getUsername().equals(temp.getUsername()) && uObj.getUsername().equals(userName) ){
                    finalList.add(aa);
                }
            }
        }
        return finalList;
    }
    //In this service we capture project details.
    @ApiMethod(name = "projectRetrieve")
    public ProjectDetails projectRetrieve(@Named("userName") String userName,@Named("projName") String projName) {
        ObjectifyService.register(ProjectDetails.class);
        ObjectifyService.register(UserDetials_gcm.class);
        Query<ProjectDetails> query = ofy().load().type(ProjectDetails.class);
        QueryResultIterator<ProjectDetails> iterator = query.iterator();
        ProjectDetails projRec = new ProjectDetails();
        while (iterator.hasNext()) {
            projRec = iterator.next();
            if(projRec.getProjName().toString().equals(projName)){
                break;
            }
        }
        return projRec;
    }
    @ApiMethod(name = "taskRetrieve")
    public TaskDetails taskRetrieve(@Named("taskName") String taskName) {
        ObjectifyService.register(TaskDetails.class);
        Query<TaskDetails> query = ofy().load().type(TaskDetails.class);
        QueryResultIterator<TaskDetails> iterator = query.iterator();
        TaskDetails projRec = new TaskDetails();
        while (iterator.hasNext()) {
            projRec = iterator.next();
            if(projRec.getTaskName().toString().equals(taskName)){
                break;
            }
        }
        return projRec;
    }


    @ApiMethod(name = "retriveMemAccToUser")
    public List<UserDetials_gcm> retiveMemAccToUser(@Named("userName") String userName) {
        ObjectifyService.register(TrabajoMembers.class);
        ObjectifyService.register(UserDetials_gcm.class);

        List<TrabajoMembers> task = new ArrayList<>();
        Query<TrabajoMembers> projQuery = ofy().load().type(TrabajoMembers.class);
        QueryResultIterator<TrabajoMembers> iterat = projQuery.iterator();
        while (iterat.hasNext()) {
            task.add(iterat.next());
        }

        List<UserDetials_gcm> uObj = new ArrayList<>();
        for (TrabajoMembers aa: task) {
            TrabajoMembers fetched = ofy().load().entity(aa).now();
            UserDetials_gcm usr = fetched.userIdOfUsername.get();
            UserDetials_gcm temp = fetched.MemberuserId.get();
            if(usr.getUsername().equals(userName))
                uObj.add(temp);
        }

        return uObj;

    }

    @ApiMethod(name="proMembers")
    public List<UserDetials_gcm> proMembers(@Named("projName") String projName) {
        ObjectifyService.register(ProjectDetails.class);
        ObjectifyService.register(UserDetials_gcm.class);
        ObjectifyService.register(TaskDetails.class);
        List<TaskDetails> task = new ArrayList<>();
        Query<TaskDetails> projQuery = ofy().load().type(TaskDetails.class);
        QueryResultIterator<TaskDetails> iterat = projQuery.iterator();
        while (iterat.hasNext()) {
            task.add(iterat.next());
        }
        UserDetials_gcm user = new UserDetials_gcm();
        Query<UserDetials_gcm> userQry = ofy().load().type(UserDetials_gcm.class);
        QueryResultIterator<UserDetials_gcm> userItr = userQry.iterator();
        ProjectDetails proj = new ProjectDetails();
        Query<ProjectDetails> query = ofy().load().type(ProjectDetails.class);
        QueryResultIterator<ProjectDetails> iterator = query.iterator();
        List<TaskDetails> finalList = new ArrayList<>();
        while (iterator.hasNext()) {
            proj = iterator.next();
            for (TaskDetails aa : task) {
                TaskDetails fetched = ofy().load().entity(aa).now();
                ProjectDetails tObj = fetched.projId.get();
                if (tObj.getProjName().equals(proj.getProjName()) && tObj.getProjName().equals(projName)) {
                    finalList.add(aa);
                }
            }
        }

        List<UserDetials_gcm> listUser =new ArrayList<>();
        while(userItr.hasNext()){
            user = userItr.next();
            for(TaskDetails aa:finalList) {
                TaskDetails fetched = ofy().load().entity(aa).now();
                UserDetials_gcm uobj = fetched.userIdFor.get();
                if (uobj.getUsername().equals(user.getUsername())) {
                    if(!listUser.contains(user))
                        listUser.add(user);
                }
            }
        }
        return listUser;
    }

    @ApiMethod(name = "proTaskRetrieve")
    public List<TaskDetails> proTaskRetrieve(@Named("projName") String projName){
        ObjectifyService.register(TaskDetails.class);
        ObjectifyService.register(ProjectDetails.class);

        List<TaskDetails> task =new ArrayList<>();
        Query<TaskDetails> projQuery = ofy().load().type(TaskDetails.class);
        QueryResultIterator<TaskDetails> iterat = projQuery.iterator();
        while (iterat.hasNext()){
            task.add(iterat.next());
        }

//        TaskDetails task = new TaskDetails();
        ProjectDetails proj =new ProjectDetails();
        Query<ProjectDetails> query =ofy().load().type(ProjectDetails.class);
        QueryResultIterator<ProjectDetails> iterator =query.iterator();

        List<TaskDetails> finalList =new ArrayList<>();

        while (iterator.hasNext()) {
            proj = iterator.next();
            for (TaskDetails aa: task) {
                TaskDetails fetched = ofy().load().entity(aa).now();
                ProjectDetails tObj = fetched.projId.get();
                if(tObj.getProjName().equals(proj.getProjName()) && tObj.getProjName().equals(projName) ){
                    finalList.add(aa);
                }
            }
        }
        return finalList;
    }


    @ApiMethod(name = "addMemberbyUser")
        public List<String> addMemByUser(@Named("userName") String userName, @Named("phnNumOfMem") String phoneNumber) {
        ObjectifyService.register(UserDetials_gcm.class);
        ObjectifyService.register(TrabajoMembers.class);

        TrabajoMembers tMObj = new TrabajoMembers();

        Query<UserDetials_gcm> query = ofy().load().type(UserDetials_gcm.class);
        List<UserDetials_gcm> records = new ArrayList<UserDetials_gcm>();
        QueryResultIterator<UserDetials_gcm> iterator = query.iterator();
        while (iterator.hasNext()) {
            records.add(iterator.next());
        }

        UserDetials_gcm user = new UserDetials_gcm();
        UserDetials_gcm mem = new UserDetials_gcm();

        for (UserDetials_gcm aa : records) {
            if(aa.getUsername().equals(userName)) {
                user = aa;
            }
            if(aa.getMobileNum().equals(phoneNumber)) {
                mem = aa;
            }
        }

        tMObj.userIdOfUsername = Ref.create(user);
        tMObj.MemberuserId = Ref.create(mem);

        ofy().save().entity(tMObj).now();

        List<String> returnUser = new ArrayList<>();
        returnUser.add(user.getUsername());
        returnUser.add(mem.getUsername());

        return returnUser;




    }

    @ApiMethod(name = "retriveTaskAccToUser")
    public List<TaskDetails> retriveTasksAccToUser(@Named("userName") String userName) {

        ObjectifyService.register(TaskDetails.class);
        ObjectifyService.register(UserDetials_gcm.class);

        TaskDetails objjj = new TaskDetails();
        Query<TaskDetails> query = ofy().load().type(TaskDetails.class);
        QueryResultIterator<TaskDetails> iterator = query.iterator();
        List<TaskDetails> recc = new ArrayList<>();
        while (iterator.hasNext()) {
            recc.add(iterator.next());
        }

        Query<UserDetials_gcm> query_User = ofy().load().type(UserDetials_gcm.class);
        List<UserDetials_gcm> all = new ArrayList<>();
        QueryResultIterator<UserDetials_gcm> user_iterator = query_User.iterator();

        UserDetials_gcm temp = new UserDetials_gcm();

        List<TaskDetails> finalList = new ArrayList<>();

        while (user_iterator.hasNext()) {
            temp = user_iterator.next();
            for (TaskDetails aa: recc) {
                TaskDetails fetched = ofy().load().entity(aa).now();
                UserDetials_gcm uObj = fetched.userIdFor.get();
                if(uObj.getUsername().equals(temp.getUsername()) && uObj.getUsername().equals(userName) ){
                    finalList.add(aa);
                }
            }
        }
        return finalList;
    }

    @ApiMethod(name = "createTask")
    public TaskDetails newTaskCreated(@Named("username") String username, @Named("projName") String projName, @Named("TaskName") String taskName, @Named("taskDesc") String taskDesc, @Named("taskStart") Date taskStart, @Named("taskEnd") Date taskEnd) {
        ObjectifyService.register(ProjectDetails.class);
        ObjectifyService.register(UserDetials_gcm.class);
        ObjectifyService.register(TaskDetails.class);

        TaskDetails task = new TaskDetails();
        task.setTaskDesc(taskDesc);
        task.setTaskName(taskName);
        task.setStartDate(taskStart);
        task.setEndDate(taskEnd);
        //////////////////////////////selecting particular user name///////////////////////////
        Query<UserDetials_gcm> query_user = ofy().load().type(UserDetials_gcm.class);
        List<UserDetials_gcm> records = new ArrayList<>();
        QueryResultIterator<UserDetials_gcm> iterator = query_user.iterator();
        while (iterator.hasNext()) { records.add(iterator.next());}
        UserDetials_gcm aaId = new UserDetials_gcm();
        for (UserDetials_gcm aa: records) {
            if(aa.getUsername().equals(username)) { aaId = aa; }
        }
        ///////////////////////////////////////////////////////////////////////////////////////
        ///////////////selecting particular project name///////////////////////////////////////
        Query<ProjectDetails> query_project = ofy().load().type(ProjectDetails.class);
        List<ProjectDetails> projRec = new ArrayList<ProjectDetails>();
        QueryResultIterator<ProjectDetails> proj_itr = query_project.iterator();

        while(proj_itr.hasNext()) {
            projRec.add(proj_itr.next());
        }

        ProjectDetails pid = new ProjectDetails();

        for (ProjectDetails tpid: projRec) {
            if (tpid.getProjName().equals(projName)) { pid= tpid; }
        }
        //////////////////////////////////////////////////////////////////////////////////////
        task.userId = Ref.create(aaId);
        task.projId = Ref.create(pid);
        ofy().save().entity(task).now();
        return task;
    }

    @ApiMethod(name = "createTaskAccToProj")
    public ArrayList<String> newTaskCreatedAccToProj(@Named("username") String username, @Named("projName") String projName, @Named("TaskName") String taskName, @Named("taskDesc") String taskDesc, @Named("taskStart") Date taskStart, @Named("taskEnd") Date taskEnd, @Named("phnNumOfUser") String phnNumOfUser ) {

        ArrayList<String> returnResult = new ArrayList<>();

        ObjectifyService.register(ProjectDetails.class);
        ObjectifyService.register(UserDetials_gcm.class);
        ObjectifyService.register(TaskDetails.class);

        TaskDetails task = new TaskDetails();
        task.setTaskDesc(taskDesc);
        task.setTaskName(taskName);
        task.setStartDate(taskStart);
        task.setEndDate(taskEnd);
        //////////////////////////////selecting particular user name///////////////////////////
        Query<UserDetials_gcm> query_user = ofy().load().type(UserDetials_gcm.class);
        List<UserDetials_gcm> records = new ArrayList<>();
        QueryResultIterator<UserDetials_gcm> iterator = query_user.iterator();
        while (iterator.hasNext()) { records.add(iterator.next());}


        UserDetials_gcm aaId = new UserDetials_gcm();
        for (UserDetials_gcm aa: records) {
            if(aa.getUsername().equals(username)) { aaId = aa; }
        }

        UserDetials_gcm bbId = new UserDetials_gcm();

        for (UserDetials_gcm bb : records) {
            if(bb.getMobileNum().equals(phnNumOfUser)){
                bbId = bb;
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////
        ///////////////selecting particular project name///////////////////////////////////////
        Query<ProjectDetails> query_project = ofy().load().type(ProjectDetails.class);
        List<ProjectDetails> projRec = new ArrayList<ProjectDetails>();
        QueryResultIterator<ProjectDetails> proj_itr = query_project.iterator();

        while(proj_itr.hasNext()) {
            projRec.add(proj_itr.next());
        }

        ProjectDetails pid = new ProjectDetails();

        for (ProjectDetails tpid: projRec) {
            if (tpid.getProjName().equals(projName)) { pid= tpid; }
        }
        //////////////////////////////////////////////////////////////////////////////////////
        task.userId = Ref.create(aaId);
        task.userIdFor = Ref.create(bbId);
        task.projId = Ref.create(pid);
        ofy().save().entity(task).now();

        returnResult.add(username + " Assigned " + taskName + " to You ");
        returnResult.add(bbId.getUsername());

        return returnResult;
    }

    @ApiMethod(name = "createProject")
    public ProjectDetails newProjectCreated(@Named("username") String username, @Named("projName") String projName, @Named("projDesc") String projDesc, @Named("projStart") Date projStart, @Named("projEnd") Date projEnd) {
        ObjectifyService.register(ProjectDetails.class);
        ObjectifyService.register(UserDetials_gcm.class);

        ProjectDetails newObj = new ProjectDetails();
        newObj.setDesc(projDesc);
        newObj.setProjName(projName);
        newObj.setEndDate(projEnd);
        newObj.setStartDate(projStart);


//        Car car = new Car();
//        car.driver = Ref.create(driverKey);
        Query<UserDetials_gcm> query = ofy().load().type(UserDetials_gcm.class);

        List<UserDetials_gcm> records = new ArrayList<>();
        QueryResultIterator<UserDetials_gcm> iterator = query.iterator();
        while (iterator.hasNext()) {
            records.add(iterator.next());
        }

        UserDetials_gcm aaId = new UserDetials_gcm();


        for (UserDetials_gcm aa: records) {
            if(aa.getUsername().equals(username)) {
                aaId = aa;
            }
        }

        newObj.userId = Ref.create(aaId);

        ofy().save().entity(newObj).now();

        return newObj;
    }

    @ApiMethod(name = "register")
    public StoreToDatastore storeData(@Named("regId") String regId) {
        ObjectifyService.register(StoreToDatastore.class);
        if(findRecord(regId) != null) {
//            log.info("Device " + regId + " already registered, skipping register");
            return null;
        }
        StoreToDatastore record = new StoreToDatastore();
        record.setRegId(regId);
        ofy().save().entity(record).now();
        return record;
    }


    @ApiMethod(name = "loginUsergcm")
    public UserDetials_gcm logINUserGcm(@Named("userName") String userName, @Named("pass") String pass) {

        UserDetials_gcm aaa = new UserDetials_gcm();
        aaa.setUsername(userName);
        aaa.setPassword(pass);
        Boolean check = false;

        ObjectifyService.register(UserDetials_gcm.class);
        Query<UserDetials_gcm> query = ofy().load().type(UserDetials_gcm.class);
        List<UserDetials_gcm> records = new ArrayList<UserDetials_gcm>();
        QueryResultIterator<UserDetials_gcm> iterator = query.iterator();
        while (iterator.hasNext()) {
            records.add(iterator.next());
        }
        for (UserDetials_gcm a:records) {
            if(a.getUsername().equals(aaa.getUsername())) {
                if(a.getPassword().equals(aaa.getPassword())) {
//                    return userName;
                    check = true;
                } else {
//                    return "password INcorrect";
                }
            } else {
//                return "userName Incorrect";
            }

        }
        if(check)
            return aaa;
        else return null;
    }

    private StoreToDatastore findRecord(String regId) {
        return ofy().load().type(StoreToDatastore.class).filter("regId", regId).first().now();
    }

}
