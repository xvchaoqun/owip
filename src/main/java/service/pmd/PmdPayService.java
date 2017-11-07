package service.pmd;

import controller.pmd.PayNotifyBean;
import domain.pmd.PmdNotifyLog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lm on 2017/11/7.
 */
@Service
public class PmdPayService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 处理页面返回结果
    public void returnPage(PayNotifyBean bean) {

        logger.info("bean.toString()=" + bean.toString());
        saveNotifyBean(bean);
    }

    // 处理服务器结果通知
    public boolean notifyPage(PayNotifyBean bean) {

        logger.info("bean.toString()=" + bean.toString());
        saveNotifyBean(bean);
        return true;
    }


    // 保存结果
    public int saveNotifyBean(PayNotifyBean bean) {

        String ip = ContextHelper.getRealIp();
        try {

            PmdNotifyLog record = new PmdNotifyLog();

            record.setOrderDate(DateUtils.parseDate(StringUtils.trim(bean.getOrderDate()), "yyyyMMddHHmmss"));
            record.setOrderNo(StringUtils.trim(bean.getOrderNo()));
            record.setAmount(new BigDecimal(StringUtils.trim(bean.getAmount())));
            record.setJylsh(StringUtils.trim(bean.getJylsh()));
            record.setTranStat(Byte.valueOf(StringUtils.trim(bean.getTranStat())));
            record.setReturnType(Byte.valueOf(StringUtils.trim(bean.getReturn_type())));
            record.setSign(StringUtils.trim(bean.getSign()));
            record.setRetTime(new Date());
            record.setIp(ip);

            return pmdNotifyLogMapper.insertSelective(record);
        } catch (Exception ex) {

            HttpServletRequest request = ContextHelper.getRequest();
            logger.error(String.format("保存支付通知失败，报文内容：%s, IP:%s",
                    JSONUtils.toString(request.getParameterMap(), false), ip), ex);
            return 0;
        }
    }
}
