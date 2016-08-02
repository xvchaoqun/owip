package bean;

import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.dispatch.DispatchCadreRelate;
import sys.tags.CmTag;

import java.util.List;

/**
 * Created by fafa on 2016/7/1.
 */
public class DispatchCadreRelateBean {

    private List<DispatchCadreRelate> all;
    private Dispatch first = null;
    private Dispatch last = null;

    public DispatchCadreRelateBean(List<DispatchCadreRelate> all) {

        this.all = all;
        for (DispatchCadreRelate dispatchCadreRelate : all) {
            Integer dispatchCadreId = dispatchCadreRelate.getDispatchCadreId();
            DispatchCadre dispatchCadre = CmTag.getDispatchCadre(dispatchCadreId);
            Dispatch dispatch = dispatchCadre.getDispatch();
            if (first == null) {
                first = dispatch;
                last = dispatch;
            }else {
                if(first.getWorkTime().after(dispatch.getWorkTime())){
                    first = dispatch;
                }
                if (last.getWorkTime().before(dispatch.getWorkTime())) {
                    last = dispatch;
                }
            }
        }
    }

    public List<DispatchCadreRelate> getAll() {
        return all;
    }

    public void setAll(List<DispatchCadreRelate> all) {
        this.all = all;
    }

    public Dispatch getFirst() {
        return first;
    }

    public void setFirst(Dispatch first) {
        this.first = first;
    }

    public Dispatch getLast() {
        return last;
    }

    public void setLast(Dispatch last) {
        this.last = last;
    }
}
