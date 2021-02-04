package interceptor;

import domain.base.ApiKey;
import org.apache.commons.lang.StringUtils;
import service.base.ApiKeyService;
import sys.tags.CmTag;
import sys.utils.PatternUtils;
import sys.utils.SignatureUtil;

import java.util.TreeMap;

public class Signature {


    private TreeMap<String,Object> params;

    public Signature(){

        params = new TreeMap<String,Object>();
    }

    public Signature put(String key, Object obj){

        params.put(key, obj);
        return this;
    }

    public String sign(String requestUri, String app){

        ApiKeyService apiKeyService = CmTag.getBean(ApiKeyService.class);
        ApiKey apiKey = apiKeyService.findAll().get(app);
        if (apiKey == null){
            throw new SignParamsException(-4, "app不存在");
        }

        if(StringUtils.isBlank(apiKey.getRequestUri())
                || !PatternUtils.match(apiKey.getRequestUri(), requestUri)){
            
            throw new SignParamsException(-5, "非法请求");
        }

        String key = apiKey.getSecret();

        if(params.size()==0 || StringUtils.isBlank(key)) return null;
        
        return SignatureUtil.signature(params, app, key);
    }

    public boolean verify(String requestUri, String app, String sign){

        if(StringUtils.isBlank(sign))
            return false;
        return StringUtils.equalsIgnoreCase(sign, sign(requestUri, app));
    }
}
