package mananaog.sapiadvers.listing;

import java.io.Serializable;
import java.util.ArrayList;

public class AdverItem implements Serializable {
    private String id;
    private String title;
    private String shortDescription;
    private String longDescription;
    private int visitors;
    private String phone;
    private String location;
    private ArrayList<String> images = new ArrayList<>();

    public AdverItem() {
    }

    public AdverItem(String id, String title, String shortDescription, String longDescription, int visitors, String phone, String location, ArrayList<String> images) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.visitors = visitors;
        this.phone = phone;
        this.location = location;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    @Override

    public String toString() {
        return "AdverItem{" +
                "title='" + title + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", visitors=" + visitors +
                ", images=" + images.toString() +
                '}';
    }

}
