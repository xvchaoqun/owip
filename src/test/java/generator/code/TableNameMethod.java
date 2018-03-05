package generator.code;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import java.util.List;

public class TableNameMethod implements TemplateMethodModelEx {

	
	public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		
		return formatStr(args.get(0).toString(), args.get(1).toString());
	}
	
	public static String formatStr(String beforeName, String formatStyle){
		
		try{

			String returnTableName = "";
			
			String[] temp = beforeName.split("_");
			
			for(int i = 0; i<temp.length; i++){
				
				if(StringUtils.equals(formatStyle, "tablename")){
					
					returnTableName += temp[i];
				}
				
				if(StringUtils.equals(formatStyle, "tableName")){
					
					if(i==0)
						returnTableName += temp[i];
					else
						returnTableName += WordUtils.capitalize(temp[i]);
				}
				
				if(StringUtils.equals(formatStyle, "TableName")){
					
					returnTableName += WordUtils.capitalize(temp[i]);
				}
			}
			
			return returnTableName;
		}catch(RuntimeException e){
			return null;
		}
	}

}
