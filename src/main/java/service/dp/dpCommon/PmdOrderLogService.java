package service.dp.dpCommon;

import controller.global.OpException;
import domain.dp.PmdOrderLog;
import domain.dp.PmdOrderLogExample;
import domain.dp.PmdOrderSumLog;
import domain.dp.PmdOrderSumLogExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.dp.PmdOrderLogMapper;
import persistence.dp.PmdOrderSumLogMapper;
import sys.utils.DateUtils;

import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class PmdOrderLogService {

    @Autowired
    private PmdOrderLogMapper pmdOrderLogMapper;
    @Autowired
    private PmdOrderSumLogMapper pmdOrderSumLogMapper;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String fileLimitChar = "&";
    public static final int fieldAllCount = 17;
    private int count;

    @Transactional
    public int loadFile(String path,String openFileStyle, String name){
        try {
            RandomAccessFile raf = new RandomAccessFile(path, openFileStyle);

            String line_record = raf.readLine();
            while (line_record != null) {
                // 解析每一条记录
                if (line_record.startsWith("#totalcount")){
                    line_record = raf.readLine();
                    totalParseRecord(line_record, name);
                }else if (line_record.startsWith("#")) {
                    line_record = raf.readLine();
                    continue;
                }

                parseRecord(line_record, name);
                line_record = raf.readLine();
            }
            logger.info(name + ".txt文件中共有合法的记录" + count + "条");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    //总金额
    public void totalParseRecord(String line_record, String name) throws Exception{
        //拆分记录
        String[] files = line_record.split(fileLimitChar);
        String _fileId = name.substring(name.length() - 12,name.length() - 4);

        //删除已存在的相同记录
        PmdOrderSumLogExample example= new PmdOrderSumLogExample();
        example.createCriteria().andFileIdEqualTo(_fileId);
        List<PmdOrderSumLog> pmdOrderSumLogs = pmdOrderSumLogMapper.selectByExample(example);
        if (pmdOrderSumLogs.size() != 0){
            for (PmdOrderSumLog pmdOrderSumLog : pmdOrderSumLogs) {
                try {
                    pmdOrderSumLogMapper.deleteByPrimaryKey(pmdOrderSumLog.getId());
                }catch (Exception e){
                    throw new OpException("总金额插入失败！");
                }

            }
        }

        System.out.println(tranStr(files[1]));
        if (tranStr(files[1]).equals("总行数")){
            return;
        }else {
            String fileId = _fileId;
            Integer totalCount = Integer.valueOf(tranStr(files[0]));
            String _totalMoney = tranStr(files[1]);
            Integer totalMoney = Integer.valueOf(_totalMoney.substring(0,_totalMoney.length()-1));
            PmdOrderSumLog record = new PmdOrderSumLog();
            record.setFileId(fileId);
            record.setTotalCount(totalCount);
            record.setTotalMoney(totalMoney);
            pmdOrderSumLogMapper.insert(record);
            logger.info("插入" + fileId + "文件中党费总金额成功");
        }
    }

    //各条明细
    @Transactional
    public void parseRecord(String line_record, String name) throws Exception {

        String _fileId = name.substring(name.length() - 12,name.length() - 4);

        //拆分记录

        String[] fields = line_record.split(fileLimitChar);
        String _thirdOrderId = tranStr(fields[1]);
        PmdOrderLogExample example = new PmdOrderLogExample();
        example.createCriteria().andThirdOrderIdEqualTo(_thirdOrderId);
        List<PmdOrderLog> pmdOrderLogs = pmdOrderLogMapper.selectByExample(example);
        if (pmdOrderLogs.size() != 0) {
            for (PmdOrderLog pmdOrderLog : pmdOrderLogs) {
                pmdOrderLogMapper.deleteByPrimaryKey(pmdOrderLog.getId());
            }
        }
        if (fields.length == fieldAllCount) {
            String _sign = tranStr(fields[16]);
            PmdOrderLog pmdOrderLog = new PmdOrderLog();
            pmdOrderLog.setDateId(Integer.valueOf(_fileId));
            pmdOrderLog.setAccount(Integer.valueOf(tranStr(fields[0])));
            pmdOrderLog.setThirdOrderId(tranStr(fields[1]));
            pmdOrderLog.setToAccount(Integer.valueOf(tranStr(fields[2])));
            pmdOrderLog.setTranamt(Integer.valueOf(tranStr(fields[3])));
            pmdOrderLog.setOrderId(tranStr(fields[4]));
            pmdOrderLog.setReforderId(tranStr(fields[5]));
            pmdOrderLog.setOperType(Integer.valueOf(tranStr(fields[6])));
            pmdOrderLog.setOrderDesc(tranStr(fields[7]));
            pmdOrderLog.setPraram1(tranStr(fields[8]));
            pmdOrderLog.setSno(tranStr(fields[9]));
            pmdOrderLog.setActuaLamt(Integer.valueOf(tranStr(fields[10])));
            pmdOrderLog.setState(Byte.valueOf(tranStr(fields[11])) == 1 ? true :false);
            pmdOrderLog.setPayName(tranStr(fields[12]));
            pmdOrderLog.setRzDate(DateUtils.parseStringToDate(tranStr(fields[13])));
            pmdOrderLog.setJyDate(DateUtils.parseStringToDate(tranStr(fields[14])));
            pmdOrderLog.setThirdSystem(tranStr(fields[15]));
            pmdOrderLog.setSign(_sign.substring(0,_sign.length()-1));
            count++;
            try {
                pmdOrderLogMapper.insert(pmdOrderLog);
            }catch (Exception e){
                throw new OpException(tranStr(fields[1]) + "订单插入数据库失败！");
            }



        }
    }

    private String tranStr(String oldstr) {
        String newstr = "";
        try {
            String str = new String(oldstr.getBytes("ISO-8859-1"), "GBK");
            String[] arr = str.split("=", 2);
            if (arr.length == 1){
                newstr = null;
            }else {
                newstr = arr[1];
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newstr;
    }

}
