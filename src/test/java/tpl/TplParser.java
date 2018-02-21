package tpl;

import bean.ColumnBean;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import service.DBOperator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class TplParser {
	
	@Autowired
	DBOperator dbOperator;

	@Test
	public void execute() throws Exception{
		
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-ow.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-sys.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-sys2.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-dispatch.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-modify.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-cadre.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-cis.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-crp.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-cpc.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-train.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-crs.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-oa.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-pmd.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-scMatter.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-scLetter.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-scGroup.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-scCommittee.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-scPublic.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-scDispatch.json";
		String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-scAd.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-pcs.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-verify.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-base.json";
		//String pathname = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\json\\tables-abroad.json";

		ObjectMapper m = new ObjectMapper();
		JsonNode jsonNode = m.readTree(new File(pathname));

		String tablePrefix = jsonNode.path("tablePrefix").getTextValue();
		//String folder = tablePrefix.substring(0, tablePrefix.length() - 1);
		String folder = jsonNode.path("folder").getTextValue();
		String resFolder = jsonNode.path("resFolder").getTextValue();
		String schema = jsonNode.path("schema").getTextValue();
		String cpath = jsonNode.path("cpath").getTextValue() + "\\" + folder+ "\\";
		String spath = jsonNode.path("spath").getTextValue() + "\\" + folder+ "\\";
		String vpath = jsonNode.path("vpath").getTextValue() + "\\" + folder+ "\\";

		JsonNode tablesNode = jsonNode.path("tables");
		Iterator<JsonNode> iterator = tablesNode.iterator();
		
		while(iterator.hasNext()){
			
			JsonNode tableNode = iterator.next();
			
			boolean ignore = tableNode.path("ignore").getBooleanValue();
			if(ignore==true) continue;
			
			String tablename = tableNode.path("table").getTextValue();
			String key = tableNode.path("key").getTextValue();
			String searchColumns = tableNode.path("searchColumns").getTextValue();
			String logType = tableNode.path("logType").getTextValue();

			genService(folder, tablePrefix, tablename, key, spath);
			
			String outpath4Page = vpath + TableNameMethod.formatStr(tablename, "tableName") + "\\";
			String cnTableName = dbOperator.getTableComments(tablePrefix + tablename, schema);
			
			Map<String, ColumnBean> tableColumnsMap = dbOperator.getTableColumnsMap(tablePrefix + tablename, schema);

			List<ColumnBean> searchColumnBeans = new ArrayList<>();
			for (String searchColumn : searchColumns.split(",")) {
				ColumnBean columnBean = tableColumnsMap.get(searchColumn);
				if(columnBean!=null) searchColumnBeans.add(columnBean);
			}

			String listPageShowColumns = tableNode.path("showColumns").getTextValue();
			List<ColumnBean> listPageTableColumns= dbOperator.getTableColumns(tablePrefix + tablename, schema, listPageShowColumns, true);
			genPageJsp(resFolder, tablename, key,  cnTableName , searchColumnBeans, listPageTableColumns, outpath4Page );

			genController(folder, resFolder, tablePrefix, tablename, key, searchColumnBeans, logType, cnTableName, listPageTableColumns, cpath);

			String savePageExcludeColumns = tableNode.path("excludeEditColumns").getTextValue();
			List<ColumnBean> savePageTableColumns= dbOperator.getTableColumns(tablePrefix + tablename, schema, savePageExcludeColumns, false);
			genCuJsp(resFolder, tablename, cnTableName, savePageTableColumns, outpath4Page);
		
		}
	}
	
	public static void genController(String folder, String resFolder, String tablePrefix, String tablesqlname, String key, List<ColumnBean> searchColumnBeans,
									 String logType, String cnTableName, List<ColumnBean> tableColumns, String outpath) throws IOException, TemplateException{
		
		String curPath = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\";
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
		cfg.setDirectoryForTemplateLoading(new File(curPath));
		cfg.setSharedVariable("tbn", new TableNameMethod());
		
		Map root = new HashMap();
		root.put("folder", folder);
		root.put("resFolder", resFolder);
		root.put("tablePrefix", tablePrefix);
		root.put("tablesqlname", StringUtils.lowerCase(tablesqlname));
		root.put("key",  StringUtils.lowerCase(key));
		root.put("searchColumnBeans",  searchColumnBeans);
		root.put("cnTableName", cnTableName);
		root.put("tableColumns", tableColumns);
		root.put("logType", logType);

		Template tpl = cfg.getTemplate("${tbn(tablesqlname, 'TableName')}Controller.java.ftl");

		String content = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, root);
		
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate("myTemplate",tpl.getName());
		
		 cfg.setTemplateLoader(stringLoader);

		Template temp = cfg.getTemplate("myTemplate","utf-8");

		String processTemplateIntoString = FreeMarkerTemplateUtils.processTemplateIntoString(temp, root);
		String filename = processTemplateIntoString.split(".ftl")[0];
		
		
		saveFile( System.getProperty("user.dir") +  outpath, filename, false, content, "");
	}

	public static void genService(String folder, String tablePrefix, String tablesqlname, String key, String outpath) throws IOException, TemplateException{

		String curPath = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\";
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
		cfg.setDirectoryForTemplateLoading(new File(curPath));
		cfg.setSharedVariable("tbn", new TableNameMethod());

		Map root = new HashMap();
		root.put("folder", folder.replaceAll("\\/", "\\."));
		root.put("tablePrefix", tablePrefix);
		root.put("tablesqlname", StringUtils.lowerCase(tablesqlname));
		root.put("key",  StringUtils.lowerCase(key));

		Template tpl = cfg.getTemplate("${tbn(tablesqlname, 'TableName')}Service.java.ftl");

		String content = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, root);

		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate("myTemplate",tpl.getName());

		cfg.setTemplateLoader(stringLoader);

		Template temp = cfg.getTemplate("myTemplate","utf-8");

		String processTemplateIntoString = FreeMarkerTemplateUtils.processTemplateIntoString(temp, root);
		String filename = processTemplateIntoString.split(".ftl")[0];


		saveFile( System.getProperty("user.dir") +  outpath, filename, false, content, "");
	}
	
	public static void genCuJsp( String resFolder, String tablesqlname, String cnTableName,
			List<ColumnBean> tableColumns, String outpath
			) throws IOException, TemplateException{
		
		String curPath = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\";
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
		cfg.setDirectoryForTemplateLoading(new File(curPath));
		
		cfg.setSharedVariable("tbn", new TableNameMethod());
		
		Map root = new HashMap();
		root.put("resFolder", resFolder);
		root.put("tablesqlname", StringUtils.lowerCase(tablesqlname));
		root.put("cnTableName", cnTableName);
		root.put("tableColumns", tableColumns);

		Template tpl = cfg.getTemplate("${tbn(tablesqlname, 'tableName')}_au.jsp.ftl","utf-8");

		String content = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, root);
		
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate("myTemplate",tpl.getName());
		
		 cfg.setTemplateLoader(stringLoader);

		Template temp = cfg.getTemplate("myTemplate","utf-8");

		String processTemplateIntoString = FreeMarkerTemplateUtils.processTemplateIntoString(temp, root);
		String filename = processTemplateIntoString.split(".ftl")[0];
		
		saveFile( System.getProperty("user.dir") +  outpath, filename, false, content,"utf-8");
	}
	
	public static void genPageJsp( String resFolder, String tablesqlname, String key, String cnTableName,
								  List<ColumnBean>  searchColumnBeans, List<ColumnBean> tableColumns, String outpath
			) throws IOException, TemplateException{
		
		String curPath = System.getProperty("user.dir")+ "\\src\\test\\java\\tpl\\";
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
		cfg.setDirectoryForTemplateLoading(new File(curPath));
		
		cfg.setSharedVariable("tbn", new TableNameMethod());
		
		Map root = new HashMap();
		root.put("resFolder", resFolder);
		root.put("tablesqlname", StringUtils.lowerCase(tablesqlname));
		root.put("key", key);
		root.put("searchColumnBeans", searchColumnBeans);
		root.put("cnTableName", cnTableName);
		
		root.put("tableColumns", tableColumns);
	
		Template tpl = cfg.getTemplate("${tbn(tablesqlname, 'tableName')}_page.jsp.ftl","utf-8");

		String content = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, root);
		
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate("myTemplate",tpl.getName());
		
		 cfg.setTemplateLoader(stringLoader);

		Template temp = cfg.getTemplate("myTemplate","utf-8");

		String processTemplateIntoString = FreeMarkerTemplateUtils.processTemplateIntoString(temp, root);
		String filename = processTemplateIntoString.split(".ftl")[0];
		
		saveFile( System.getProperty("user.dir") +  outpath, filename, false, content,"utf-8");
	}
	
	public static void saveFile(String filepath, String filename, boolean overwrite, String content, String charset) throws IOException{
		
		File folder = new File(filepath);
		if(!folder.exists()) folder.mkdirs();
		
		File file = new File(folder + File.separator +filename);
		if(!overwrite && file.exists()) {
			System.out.println(filename + " is exists. will not gen");
			return ;
		}
		// &{ => ${
		//System.out.println(content.indexOf("\\&\\{"));
		content = content.replaceAll("\\&\\{", "\\$\\{");
		file.createNewFile();
		
		OutputStream out = new FileOutputStream(file);
		if(StringUtils.isNotBlank(charset))
			out.write(content.getBytes(charset));
		else
			out.write(content.getBytes());
		out.close();
		
		System.out.println(filename + " gen success.");
	}
}
