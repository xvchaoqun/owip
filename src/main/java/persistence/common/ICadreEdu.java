package persistence.common;

import domain.cadre.CadreEdu;
import domain.cadre.CadreView;
import sys.tags.CmTag;

/**
 * Created by lm on 2017/6/17.
 */
public class ICadreEdu extends CadreEdu {

    public CadreView getCadre(){

        return CmTag.getCadreById(getCadreId());
    }
}
