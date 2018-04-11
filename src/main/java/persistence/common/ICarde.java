package persistence.common;

import domain.cadre.CadreView;
import sys.tags.CmTag;

/**
 * Created by lm on 2017/6/18.
 */
public class ICarde {

    public CadreView getCadre(){

        return CmTag.getCadreById(id);
    }

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
