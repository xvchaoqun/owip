package persistence.cet.common;

import domain.cet.CetTrainCourse;

/**
 * Created by lm on 2018/4/11.
 */
public class ICetTrainCourse extends CetTrainCourse {

    private Boolean canQuit;

    public Boolean getCanQuit() {
        return canQuit;
    }

    public void setCanQuit(Boolean canQuit) {
        this.canQuit = canQuit;
    }
}
