package com.sstang.questionnaire.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyq7on on 18-4-15.
 */

public class Questionnaire {
    public String mKey;
    public String mCode;
    public String mTilte;
    public String mTeacherCode;
    public List<Subject> mContents = new ArrayList<>();
}
