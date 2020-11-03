package mk.beauty.services.parlours.Users;

public class Posts
{
    public  String uid,time,date,serviceimage,profileimage,phonenumber, location,servicename,fullname;

    public  Posts(){

    }

    public Posts(String uid, String time, String date, String serviceimage, String profileimage, String phonenumber, String location, String servicename, String fullname) {
        this.uid = uid;
        this.time = time;
        this.date = date;
        this.serviceimage = serviceimage;
        this.profileimage = profileimage;
        this.phonenumber = phonenumber;
        this.location = location;
        this.servicename = servicename;
        this.fullname = fullname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServiceimage() {
        return serviceimage;
    }

    public void setServiceimage(String serviceimage) {
        this.serviceimage = serviceimage;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
