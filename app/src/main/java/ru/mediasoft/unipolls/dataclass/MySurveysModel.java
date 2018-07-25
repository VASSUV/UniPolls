package ru.mediasoft.unipolls.dataclass;

import java.util.List;

public class MySurveysModel {
    String per_page;
    String total;
    List<Data> data;
    String page;
    Links links;

    public class Data{
        String href;
        String nickname;
        String id;
        String title;
    }

    private class Links{
        String self;
    }
}
