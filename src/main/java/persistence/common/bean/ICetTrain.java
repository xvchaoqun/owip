package persistence.common.bean;

import domain.cet.CetTrain;

/**
 * Created by lm on 2018/3/14.
 */
public class ICetTrain extends CetTrain {

    private Integer courseCount;

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }
}
