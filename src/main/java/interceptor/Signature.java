package interceptor;

import domain.base.ApiKey;
import org.apache.commons.lang.StringUtils;
import service.base.ApiKeyService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.ContextHelper;
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
            throw new ApiException(SystemConstants.API_RETURN_ILLEGAL_APP);
        }

        if(StringUtils.isBlank(apiKey.getRequestUri())
                || !PatternUtils.match(apiKey.getRequestUri(), requestUri)){
            
            throw new ApiException(SystemConstants.API_RETURN_ILLEGAL_URI);
        }

        String requestIp = ContextHelper.getRealIp();
        if(StringUtils.isNotBlank(apiKey.getValidIp())
                && !PatternUtils.match(apiKey.getValidIp(), requestIp)){

            throw new ApiException(SystemConstants.API_RETURN_ILLEGAL_IP);
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
