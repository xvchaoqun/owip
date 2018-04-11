package persistence.crs.common;

import domain.crs.CrsExpertView;

/**
 * Created by lm on 2017/8/4.
 */
public class ICrsExpert extends CrsExpertView {

    public Integer postCount;

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }
}
