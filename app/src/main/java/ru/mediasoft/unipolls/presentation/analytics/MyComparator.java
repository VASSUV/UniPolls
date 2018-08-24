package ru.mediasoft.unipolls.presentation.analytics;

import java.util.Comparator;

public class MyComparator implements Comparator<AnsChoices> {

    @Override
    public int compare(AnsChoices o1, AnsChoices o2) {
        int i1 = o1.answered;
        int i2 = o2.answered;
        return (Integer.compare(i2, i1));
    }
}