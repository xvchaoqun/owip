import org.junit.Test;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

/**
 * Created by fafa on 2016/8/24.
 */
public class TTtest {

    @Test
    public void t(){
        System.out.println(SystemConstants.ROLE_MAP);
    }

    public static void main(String[] args) {
        //System.out.println(DateUtils.formatDateTimeMillis(1500889489690L, DateUtils.YYYY_MM_DD_HH_MM_SS));


        System.out.println(DateUtils.parseDate("2017-06", DateUtils.YYYY_MM_DD));
    }
}
