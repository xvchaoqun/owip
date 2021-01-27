package interceptor;

import domain.base.ApiKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.base.ApiKeyMapper;
import sys.tags.CmTag;
import sys.utils.SignatureUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Signature {

    private ApiKeyMapper apiKeyMapper = CmTag.getBean(ApiKeyMapper.class);

    private TreeMap<String,Object> params;

    public Signature(){

        params = new TreeMap<String,Object>();
    }

    public Signature put(String key, Object obj){

        params.put(key, obj);
        return this;
    }

    public final static Map<String, String> appKeyMap = new HashMap<>();

    public String sign(String app){
        String keyapi = apiKeyMapper.getApiInfoByName(app);
        if(keyapi==null){
            throw new SignParamsException("工号不存在或签名错误");
        }
        String key = keyapi;
        System.out.println(key);
        System.out.println(app);


        if(params.size()==0 || StringUtils.isBlank(key)) return null;

        return key;
    }

    public boolean verify(String app, String sign){

        if(StringUtils.isBlank(sign))
            return false;

        return StringUtils.equalsIgnoreCase(sign, sign(app));
    }
}
