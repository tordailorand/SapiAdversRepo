package mananaog.sapiadvers.listing;

class AdverItem  {
    private String title;
    private String description;
    private int visitors;
    private String adverUrl;
    private String profilePicture;

    public AdverItem(String title, String description, int visitors, String adverUrl, String profilePicture) {
        this.title = title;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                ", description='" + description + '\'' +
                ", visitors=" + visitors +
                ", adverUrl='" + adverUrl + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }

}
