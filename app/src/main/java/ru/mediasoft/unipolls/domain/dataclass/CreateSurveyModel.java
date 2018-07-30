package ru.mediasoft.unipolls.domain.dataclass;

public class CreateSurveyModel {
    public Integer responseCount;
    public Integer pageCount;
    public String dateCreated;
    public ButtonsText buttonsText;
    public String folderId;
    public CustomVariables customVariables;
    public String nickname;
    public String id;
    public Integer questionCount;
    public String category;
    public String preview;
    public Boolean isOwner;
    public String language;
    public Boolean footer;
    public String dateModified;
    public String analyzeUrl;
    public String summaryUrl;
    public String href;
    public String title;
    public String collectUrl;
    public String editUrl;

    public class ButtonsText {
        public String doneButton;
        public String prevButton;
        public String exitButton;
        public String nextButton;
    }

    public class CustomVariables {
    }
}
