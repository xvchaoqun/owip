package importData;

import bean.XlsUpload;
import bean.XlsUser;
import domain.sys.SysUser;
import domain.sys.SysUserExample;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
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
import shiro.SaltPassword;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.IdcardValidator;

import java.io.File;
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
        int count = sysUserMapper.countByExample(example);

        return (count==0)?code:genCode();
    }
    @Test
    public void get() throws Exception {
        File xlsx = new File("C:\\Users\\fafa\\Desktop\\教工党员问题汇总（已核实20160420） - 副本.xlsx");
        OPCPackage pkg = OPCPackage.open(xlsx);
        XSSFWorkbook workbook = new XSSFWorkbook(pkg);
        XSSFSheet sheet = workbook.getSheetAt(0);

        List<XlsUser> xlsUsers = XlsUpload.fetchUsers(sheet);

        for (XlsUser xlsUser : xlsUsers) {
            //System.out.print(xlsUser.getRealname() + " " + xlsUser.getGender() + " " + xlsUser.getIdcard());
            IdcardValidator idcardValidator = new IdcardValidator();
            if(!idcardValidator.isValidatedAllIdcard(xlsUser.getIdcard())){
                System.out.println(xlsUser.getRealname() + " " + xlsUser.getIdcard() + " " + xlsUser.getUnit());
                //continue;
            }
            String idcard = xlsUser.getIdcard();
            if(idcard.length()==15){
                idcard = idcardValidator.convertIdcarBy15bit(idcard);
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
            sysUser.setRoleIds(sysUserService.buildRoleIds(SystemConstants.ROLE_GUEST));
            sysUserService.insertSelective(sysUser);
        }

        System.out.println(xlsUsers.size());

    }
}
