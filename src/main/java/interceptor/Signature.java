package interceptor;

import org.apache.commons.lang.StringUtils;
import service.base.ApiKeyService;
import sys.tags.CmTag;
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

    public String sign(String app){
        ApiKeyService apiKeyService = CmTag.getBean(ApiKeyService.class);
        if (apiKeyService.getApiInfoByName(app)==null){
            return null;
        }
        String keyApi = apiKeyService.findAll().get(app);
        if(keyApi==null){
            throw new SignParamsException("app不存在");
        }
        String key = keyApi;

        if(params.size()==0 || StringUtils.isBlank(key)) return null;
        
        return SignatureUtil.signature(params, app, key);
    }

    public boolean verify(String app, String sign){
        if(StringUtils.isBlank(sign))
            return false;
        return StringUtils.equalsIgnoreCase(sign, sign(app));
    }
}
