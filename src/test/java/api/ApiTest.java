package api;

import bean.ColumnBean;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.member.Member;
import domain.member.MemberApply;
import domain.member.MemberApplyExample;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import org.apache.axiom.om.util.CommonUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import persistence.common.CommonMapper;
import persistence.member.MemberApplyMapper;
import persistence.sys.SysUserMapper;
import service.DBServcie;
import service.party.BranchService;
import service.party.PartyService;
import service.sys.LogService;
import service.sys.SysUserService;
import sys.constants.LogConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;
import sys.utils.ContentUtils;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;
import sys.utils.MD5Util;

import javax.swing.plaf.ProgressBarUI;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2018/3/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ApiTest {

    @Autowired
    private PartyService partyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private MemberApplyMapper memberApplyMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private LogService logService;

    private static String tableName = "ow_member_apply";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void memberApplySync() throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException {

        String url = "http://localhost:8080/api/member/memberApply_sync";
        String app = "ma";
        String key = "04acf931d0d58b6d3240407c3b168d68";
        String code = "201811200928";

        String _signStr = String.format("app=%s&code=%s&key=%s", app, code, key);
        String sign = MD5Util.md5Hex(_signStr, "utf-8");

        //System.out.println("sign = " + sign);
        SysUserView sysUser = sysUserService.findByCode(code);
        Integer userId = sysUser.getUserId();
        MemberApply _memberApply = memberApplyMapper.selectByPrimaryKey(userId);
        String stage = null;
        if (_memberApply != null) {
            stage = _memberApply.getStage()+"";
        }

        List<BasicNameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("stage", stage));
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("app", app));
        urlParameters.add(new BasicNameValuePair("sign", sign));
        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(postParams);
        CloseableHttpResponse res = httpclient.execute(httppost);

        //System.out.println(res.getStatusLine().getStatusCode());

        Map map= JSON.parseObject(EntityUtils.toString(res.getEntity()), Map.class);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        MemberApply memberApply = gson.fromJson(map.get("bean").toString(), MemberApply.class);
        String partyCode = map.get("partyCode").toString();
        String branchCode = map.get("branchCode").toString();
        Party party = partyService.getByCode(partyCode);
        Integer partyId = party.getId();
        memberApply.setPartyId(partyId);
        if (!PartyHelper.isDirectBranch(partyId) && StringUtils.isNotBlank(branchCode)){
            Branch branch = branchService.getByCode(branchCode);
            memberApply.setBranchId(branch.getId());
        }

        if (_memberApply == null) {
            memberApply.setUserId(userId);
            memberApply.setFillTime(new Date());
            memberApply.setIsRemove(false);
            memberApplyMapper.insertSelective(memberApply);
        }else if (_memberApply.getStage()>memberApply.getStage()){
            logger.info(logService.log(LogConstants.LOG_MEMBER,
                    "发展阶段有误，未推送学生党员发展信息："+memberApply.getUserId()));
        }

        List<String> hasChangeField = new ArrayList<>();
        int count = 0;

        Class clazz = memberApply.getClass();
        PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz,
                Object.class).getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {// 所有的属性

            String name = pd.getName();// 属性名
            String columnName = ContentUtils.camelToUnderline(name);

            Method readMethod = pd.getReadMethod();// get方法
            // 在memberApply上调用get方法等同于获得memberApply的属性值
            Object o1 = readMethod.invoke(memberApply);
            // 在_memberApply上调用get方法等同于获得_memberApply的属性值
            Object o2 = readMethod.invoke(_memberApply);

            if (o1 instanceof Date) {
                o1 = DateUtils.formatDate((Date) o1, DateUtils.YYYY_MM_DD_HH_MM_CHINA);
            }
            if (o2 instanceof Date) {
                o2 = DateUtils.formatDate((Date) o2, DateUtils.YYYY_MM_DD_HH_MM_CHINA);
            }

            if (o1 == null && o2 == null) {
                continue;
            } else if (o1 == null && o2 != null) {
                if (o1 instanceof Date || StringUtils.equals(columnName, "branch_id")) {
                    commonMapper.excuteSql("update " + tableName + " set " + columnName + "=null where user_id=" + userId);
                    hasChangeField.add(columnName);
                    count++;
                }
                continue;
            }
            if (!o1.equals(o2)) {// 比较这两个值是否相等,不等放入list
                hasChangeField.add(columnName);
            }
        }

        if (hasChangeField.size() - count > 0){
            memberApply.setUserId(userId);
            MemberApplyExample memberApplyExample = new MemberApplyExample();
            memberApplyExample.createCriteria().andUserIdEqualTo(userId);
            memberApplyMapper.updateByExampleSelective(memberApply, memberApplyExample);
        }

        if (hasChangeField.size() > 0){
            logger.info(logService.log(LogConstants.LOG_MEMBER,
                    "推送学生党员发展信息成功："+memberApply.getUserId()+",更新字段为："+ org.apache.commons.lang3.StringUtils.join(hasChangeField, ",")));
        }

        //System.out.println(memberApply.getApplyTime());
    }

    @Test
    public void abroad() throws IOException {

        String url = "https://zzbgz.bnu.edu.cn/api/abroad/approve_count";
        String app = "oa";
        String key = "b887e286bf5d82b7b9712ed03d3e6e0e";
        String code = "11112016098";
        //String code = "zzbgz";
        String _signStr = String.format("app=%s&code=%s&key=%s", app, code, key);
        String sign = MD5Util.md5Hex(_signStr, "utf-8");

        System.out.println("sign = " + sign);

        List<BasicNameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("app", app));
        urlParameters.add(new BasicNameValuePair("sign", sign));
        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(postParams);
        CloseableHttpResponse res = httpclient.execute(httppost);

        System.out.println(res.getStatusLine().getStatusCode());

        System.out.println(_signStr);

        // "参数app为空"
        // "参数sign为空"
        // {"Message":"参数code不能为空","Success":false}
        // {"Message":"签名错误","Success":false}
        // {"Message":"系统访问出错","Success":false}
        // {"Message":"没有这个学工号","Success":false}
        // {"Message":"没有审批权限","Success":false}
        // {"Success":true,"Count":0}

        System.out.println(EntityUtils.toString(res.getEntity()));
    }

    @Test
    public void memberOut() throws IOException {

        String url = "http://localhost:8081/api/member/print";
        String app = "zcdy";
        String key = "5931e054d3b59be97b3481f6e604afe6";
        String code = "201722010162";

        String _signStr = String.format("app=%s&code=%s&key=%s", app, code, key);
        String sign = MD5Util.md5Hex(_signStr, "utf-8");

        System.out.println("sign = " + sign);

        List<BasicNameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("app", app));
        urlParameters.add(new BasicNameValuePair("sign", sign));
        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(postParams);
        CloseableHttpResponse res = httpclient.execute(httppost);

        System.out.println(res.getStatusLine().getStatusCode());

        System.out.println(_signStr);

        // "参数app为空"
        // "参数sign为空"
        // {"Message":"参数code不能为空","Success":false}
        // {"Message":"签名错误","Success":false}
        // {"Message":"系统访问出错","Success":false}
        // {"Message":"没有这个学工号","Success":false}
        // {"Message":"没有审批权限","Success":false}
        // {"Success":true,"Count":0}

        System.out.println(EntityUtils.toString(res.getEntity()));
    }
}
