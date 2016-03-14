package service.sys;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import domain.ShortMsg;
import domain.ShortMsgExample;
import domain.SysUser;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.SpringProps;
import service.sys.SysUserService;

import java.io.IOException;
import java.util.*;

@Service
public class ShortMsgService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SpringProps springProps;

    public boolean send(int sender, int receiver, String content, String type, String ip){

        SysUser sysUser = sysUserService.findById(receiver);
        if(sysUser==null){
            throw new RuntimeException("用户不存在。");
        }
        String mobile = sysUser.getMobile();
        if(StringUtils.length(StringUtils.trimToNull(mobile)) != 11){
            throw new RuntimeException("用户的手机号码有误。");
        }
        if(StringUtils.isBlank(content)){
            throw new RuntimeException("发送内容为空。");
        }

        String url= springProps.shortMsgUrl;
        String formStatusData="{\"mobile\":\""+ mobile +"\", \"content\":\""+ content +"\"}";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new StringEntity(formStatusData,"UTF-8"));
        httppost.addHeader("content-type", "application/json");
        CloseableHttpResponse res = null;
        try {
            if(springProps.shortMsgSend){
                res = httpclient.execute(httppost);
                if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                    String ret = EntityUtils.toString(res.getEntity());
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(ret, JsonObject.class);
                    JsonElement errcode = jsonObject.get("errcode");
                    boolean status = (errcode.getAsInt() == 0);

                    ShortMsg record = new ShortMsg();
                    record.setCreateTime(new Date());
                    record.setMobile(mobile);
                    record.setContent(content);
                    record.setReceiverId(receiver);
                    record.setSenderId(sender);
                    record.setType(type);
                    record.setStatus(status);
                    shortMsgMapper.insertSelective(record);

                    return status;
                }
            }else{
                ShortMsg record = new ShortMsg();
                record.setCreateTime(new Date());
                record.setMobile(mobile);
                record.setContent(content);
                record.setReceiverId(receiver);
                record.setSenderId(sender);
                record.setType(type);
                record.setRemark("test");
                record.setStatus(false);
                shortMsgMapper.insertSelective(record);

                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException("系统错误。");
        }
        return false;
    }

    @Transactional
    public int insertSelective(ShortMsg record){

        return shortMsgMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        shortMsgMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ShortMsgExample example = new ShortMsgExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        shortMsgMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ShortMsg record){
        return shortMsgMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, ShortMsg> findAll() {

        ShortMsgExample example = new ShortMsgExample();
        example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<ShortMsg> shortMsges = shortMsgMapper.selectByExample(example);
        Map<Integer, ShortMsg> map = new LinkedHashMap<>();
        for (ShortMsg shortMsg : shortMsges) {
            map.put(shortMsg.getId(), shortMsg);
        }

        return map;
    }
}
