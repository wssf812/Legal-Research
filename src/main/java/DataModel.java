public class DataModel {
    private String id;
    private String highLightTitle;
    private String highLightContent;

    public DataModel(String id, String highLightTitle, String highLightContent) {
        this.id = id;
        this.highLightTitle = highLightTitle;
        this.highLightContent = highLightContent;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "id='" + id + '\'' +
                ", highLightTitle='" + highLightTitle + '\'' +
                ", highLightContent='" + highLightContent + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHighLightTitle() {
        return highLightTitle;
    }

    public void setHighLightTitle(String highLightTitle) {
        this.highLightTitle = highLightTitle;
    }

    public String getHighLightContent() {
        return highLightContent;
    }

    public void setHighLightContent(String highLightContent) {
        this.highLightContent = highLightContent;
    }
}
