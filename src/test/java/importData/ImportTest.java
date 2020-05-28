package importData;

import domain.sys.SysUser;
import domain.sys.SysUserExample;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.sys.SysUserMapper;
import service.sys.SysUserService;
import shiro.PasswordHelper;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.SaltPassword;
import sys.utils.ExcelUtils;
import sys.utils.IdcardValidator;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fafa on 2015/11/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ImportTest {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    protected PasswordHelper passwordHelper;
    @Autowired
    protected SysUserService sysUserService;

    // 自动生成学工号,T开头+7位数字
    private String genCode(){

        String code = "T" + RandomStringUtils.randomNumeric(7);
        SysUserExample example = new SysUserExample();
        example.createCriteria().andCodeEqualTo(code);
        long count = sysUserMapper.countByExample(example);

        return (count==0)?code:genCode();
    }

    public final static int userXLSColumnCount = 4;
    public static List<XlsUser> fetchUsers(XSSFSheet sheet) {

        List<XlsUser> rows = new ArrayList<XlsUser>();
        XSSFRow rowTitle = sheet.getRow(0);
        if (null == rowTitle)
            return rows;
        int cellCount = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum();
        if (cellCount < userXLSColumnCount)
            return rows;

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            if (row == null) {// 如果为空，不处理
                continue;
            }

            XlsUser dataRow = new XlsUser();
            XSSFCell cell = row.getCell(0);
            if (null != cell) {
                String realname = ExcelUtils.getCellValue(cell);
                if (StringUtils.isBlank(realname)) {
                    continue;
                }
                dataRow.setRealname(realname);
            } else {
                continue;
            }

            cell = row.getCell(1);
            if (null != cell) {
                String _gender = ExcelUtils.getCellValue(cell);
                if (StringUtils.isBlank(_gender)) {
                    continue;
                }
                dataRow.setGender((byte) (StringUtils.equals(_gender, "男") ? 1 : 2));
            } else {
                continue;
            }

            cell = row.getCell(2);
            if (null != cell) {
                String idcard = ExcelUtils.getCellValue(cell);
                if (StringUtils.isBlank(idcard)) {
                    continue;
                }
                dataRow.setIdcard(idcard);
            } else {
                continue;
            }

            cell = row.getCell(3);
            dataRow.setUnit(ExcelUtils.getCellValue(cell));

            rows.add(dataRow);
        }

        return rows;
    }

    @Test
    public void get() throws Exception {
        File xlsx = new File("C:\\Users\\fafa\\Desktop\\教工党员问题汇总（已核实20160420） - 副本.xlsx");
        OPCPackage pkg = OPCPackage.open(xlsx);
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);

        List<XlsUser> xlsUsers = fetchUsers(sheet);

        for (XlsUser xlsUser : xlsUsers) {
            //System.out.print(xlsUser.getRealname() + " " + xlsUser.getGender() + " " + xlsUser.getIdcard());
            if(!IdcardValidator.valid(xlsUser.getIdcard())){
                System.out.println(xlsUser.getRealname() + " " + xlsUser.getIdcard() + " " + xlsUser.getUnit());
                //continue;
            }
            String idcard = xlsUser.getIdcard();
            if(idcard.length()==15){
                idcard = IdcardValidator.convertIdcarBy15bit(idcard);
                System.out.print(" 15 ");
            }
            String birth = idcard.substring(6, 14);
            System.out.print(" " + birth);
            System.out.println();

            String username = genCode();

            SysUser sysUser = new SysUser();
            sysUser.setUsername(username);
            sysUser.setCode(username);
            sysUser.setLocked(false);
            SaltPassword encrypt = passwordHelper.encryptByRandomSalt(birth);
            sysUser.setSalt(encrypt.getSalt());
            sysUser.setPasswd(encrypt.getPassword());
            sysUser.setCreateTime(new Date());
            /*sysUser.setRealname(xlsUser.getRealname());
            sysUser.setBirth(DateUtils.parseDate(birth, DateUtils.YYYYMMDD));
            sysUser.setIdcard(xlsUser.getIdcard());*/
            sysUser.setType(SystemConstants.USER_TYPE_JZG);
            sysUser.setSource(SystemConstants.USER_SOURCE_ADMIN);
            sysUser.setRoleIds(sysUserService.buildRoleIds(RoleConstants.ROLE_GUEST));
            sysUserService.insertSelective(sysUser);
        }

        System.out.println(xlsUsers.size());

    }
}
