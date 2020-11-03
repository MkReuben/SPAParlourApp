package mk.beauty.services.parlours.model;

public class Saloons {


    private String name, location,saloon,servicename,category, saloonPhone,image, price,sid, date, time;

    public Saloons(String name, String location, String saloon, String servicename, String category, String saloonPhone, String image, String price, String sid, String date, String time) {
        this.name = name;
        this.location = location;
        this.saloon = saloon;
        this.servicename = servicename;
        this.category = category;
        this.saloonPhone = saloonPhone;
        this.image = image;
        this.price = price;
        this.sid = sid;
        this.date = date;
        this.time = time;
    }

    public Saloons() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSaloon() {
        return saloon;
    }

    public void setSaloon(String saloon) {
        this.saloon = saloon;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSaloonPhone() {
        return saloonPhone;
    }

    public void setSaloonPhone(String saloonPhone) {
        this.saloonPhone = saloonPhone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
