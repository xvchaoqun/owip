package persistence.common.bean;

import domain.cadre.CadreView;
import domain.cadre.CadreWork;
import sys.tags.CmTag;

/**
 * Created by lm on 2017/6/17.
 */
public class ICadreWork extends CadreWork {

    public CadreView getCadre(){

        return CmTag.getCadreById(getCadreId());
    }
}
