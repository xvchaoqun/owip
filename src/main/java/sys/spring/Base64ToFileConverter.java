package sys.spring;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base64ToFileConverter implements HandlerMethodArgumentResolver {

	private static Pattern pattern = Pattern.compile("data:([^;]*);base64,(.+)", Pattern.CASE_INSENSITIVE);
	
	public Base64ToFileConverter(){
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RequestBase64.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String[] values = webRequest.getParameterValues(parameter.getParameterName());
		if(ArrayUtils.isNotEmpty(values)){
			
			if(parameter.getParameterType().isAssignableFrom(Base64File.class)){
				return toContent(values[0]);
			}else if(parameter.getParameterType().isAssignableFrom(List.class)){
				List<Base64File> list = new ArrayList<>(values.length);
				for(String value : values){
					list.add(toContent(value));
				}
				return list;
			}else if(parameter.getParameterType().isArray()){
				Base64File[] bfs = new Base64File[values.length];
				int index = 0;
				for(String value : values){
					bfs[index++] = toContent(value);
				}
				return bfs;
			}
		}

		return null;
	}

	private Base64File toContent(String value){
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
        try   
        {  
        	Matcher matcher = pattern.matcher(value);
        	if(matcher.find()){
        		String imageType = matcher.group(1);
        		String content = matcher.group(2);
        		
        		//Base64解码  
                byte[] b = decoder.decodeBuffer(content);  
                return new Base64File(imageType, b);
        	}
        	
        	return null;
        }catch(Exception e){
        	throw new IllegalArgumentException(e);
        }
	}
	
	public static void main(String[] args) {
		String value = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABwAAAAeCAIAAACwp+nIAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAADxSURBVEhL7ZU7DoMwEESXVNBRUsKtuAHcgmNAyS3gZtCRER5FkYw/66AUUV4BK2OeZmWxZMdxyN08eL+VH5Cu69o0TeYAj+Z55lYbHNQldV1zh4M8z7nVwpm06zpWDvZ9Z2VDeRzjOPK1E65aKKTLspRlSZ9I3/d8YKGQVlVFn8i2bVy9Ilb63rgnoyFW+orZti2X3ERJ32P6GzdESVUxQViqjQnCUm1MEJAmxAQBaUJMEJj8GEimQExMEFMH8c3TaZpYnTOJVQwm8CVpvQNf+0VRmPmm6h342h+GAVd86brecRKepMl898f3CX/p3Yg8AZMYt/pV+plfAAAAAElFTkSuQmCC";
		Matcher matcher = pattern.matcher(value);
    	if(matcher.find()){
    		String imageType = matcher.group(1);
    		String content = matcher.group(2);
    		
    		System.out.println(imageType + "   " + content);
    	}
	}
}