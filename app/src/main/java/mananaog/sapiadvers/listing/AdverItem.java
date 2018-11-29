package mananaog.sapiadvers.listing;

import java.io.Serializable;

public class AdverItem  implements Serializable {
    private String title;
    private String shortDescription;
    private String longDescription;
    private int visitors;
    private String adverUrl;
    private String profilePicture;

    public AdverItem(String title, String shortDescription, String longDescription, int visitors, String adverUrl, String profilePicture) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.visitors = visitors;
        this.adverUrl = adverUrl;
        this.profilePicture = profilePicture;
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

    public String getLongDescription() {
        return longDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
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

    public String getAdverUrl() {
        return adverUrl;
    }

    public void setAdverUrl(String adverUrl) {
        this.adverUrl = adverUrl;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {
        return "AdverItem{" +
                "title='" + title + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", visitors=" + visitors +
                ", adverUrl='" + adverUrl + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }

}
