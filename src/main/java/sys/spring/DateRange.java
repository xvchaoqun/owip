package sys.spring;

import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.util.Date;

/**
 * Created by fafa on 2017/5/11.
 */
public class DateRange {

    private Date start;
    private Date end;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String toString() {
        return toString(SystemConstants.DATERANGE_SEPARTOR, DateUtils.YYYY_MM_DD);
    }

    public String toString(String separtor, String format) {

        if (start != null && end != null) {
            return DateUtils.formatDate(start, format)
                    + separtor +
                    DateUtils.formatDate(end, format);
        }

        return null;
    }
}
