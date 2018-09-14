package controller.sys;

import controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.security.Base64Utils;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.DownloadUtils;
import sys.utils.FileUtils;
import sys.utils.MySqlUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system")
public class SystemController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("system:db")
    @RequestMapping("db")
    public String db(ModelMap modelMap) throws IOException {

        return "sys/system/db";
    }

    @RequiresPermissions("system:cmd")
    @RequestMapping("cmd")
    public String cmd(ModelMap modelMap) throws IOException {

        return "sys/system/cmd";
    }

    @RequiresPermissions("system:cmd")
    @RequestMapping(value = "cmd", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cmd(String cmd, ModelMap modelMap) throws IOException {

        boolean superAccount = CmTag.isSuperAccount(ShiroHelper.getCurrentUsername());
        if(!superAccount){
            return failed("没有权限。");
        }

        List<String> returnLines = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(
                    new String[]{"sh", "-c", cmd.trim()});
            BufferedReader inputBufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = inputBufferedReader.readLine()) != null) {

                returnLines.add(line);
            }
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                returnLines.add(e.getMessage());
            }

            logger.info(addLog(LogConstants.LOG_ADMIN, "执行cmd:%s", cmd));
        } catch (IOException e) {
            returnLines.add(e.getMessage());
        }
        Map<String, Object> resultMap = success();
        resultMap.put("cmd", cmd);
        resultMap.put("lines", returnLines);

        return resultMap;
    }

    @RequiresPermissions("system:cmd")
    @RequestMapping(value = "cmd_export")
    public void cmd_export(String cmd, HttpServletRequest request, HttpServletResponse response) throws Exception {

        boolean superAccount = CmTag.isSuperAccount(ShiroHelper.getCurrentUsername());
        if(!superAccount){
            return ;
        }

        cmd = new String(Base64Utils.decode(cmd), "utf-8");

        String tmpFile = "/tmp/" + DateUtils.formatDate(new Date(), "YYYYMMddHHmmss") + ".log";
        cmd += ">" + tmpFile;

        try {
            Process process = Runtime.getRuntime().exec(
                    new String[]{"sh", "-c", cmd});
            process.waitFor();

            response.setHeader("Set-Cookie", "fileDownload=true; path=/");
            DownloadUtils.download(request, response, tmpFile);
            return;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        response.setHeader("Set-Cookie", "fileDownload=false; path=/");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        return ;
    }

    @RequiresPermissions("system:sql")
    @RequestMapping("sql")
    public String sql(ModelMap modelMap) throws IOException {

        return "sys/system/sql";
    }

    @RequiresPermissions("system:sql")
    @RequestMapping(value = "sql", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sql(String sql, ModelMap modelMap) throws IOException {

        boolean superAccount = CmTag.isSuperAccount(ShiroHelper.getCurrentUsername());
        if(!superAccount){
            return failed("没有权限。");
        }

        String cmd = MessageFormat.format("mysql -u{0} -p\"{1}\" -e\"use {2};{3}\"",
                PropertiesUtils.getString("jdbc_user"),
                PropertiesUtils.getString("jdbc_password"),
                PropertiesUtils.getString("db.schema"), sql.replaceAll("\n", ";"));

        List<String> returnLines = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(
                    new String[]{"sh", "-c", cmd});
            BufferedReader inputBufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = inputBufferedReader.readLine()) != null) {
                returnLines.add(line);
            }
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                returnLines.add(e.getMessage());
            }

            logger.info(addLog(LogConstants.LOG_ADMIN, "执行sql:%s", sql));
        } catch (IOException e) {
            returnLines.add(e.getMessage());
        }

        Map<String, Object> resultMap = success();
        resultMap.put("cmd", cmd);
        resultMap.put("lines", returnLines);
        return resultMap;
    }

    @RequiresPermissions("properties:*")
    @RequestMapping("properties")
    public String properties(ModelMap modelMap) throws IOException {

        Resource resource = new ClassPathResource("spring.properties");
        String content = new String(FileUtils.getBytes(resource.getFile()));

        modelMap.put("content", content);

        return "sys/system/properties";
    }

    @RequiresPermissions("properties:*")
    @RequestMapping(value = "properties", method = RequestMethod.POST)
    @ResponseBody
    public Map do_properties(String content, ModelMap modelMap) throws IOException {

        boolean superAccount = CmTag.isSuperAccount(ShiroHelper.getCurrentUsername());
        if(!superAccount){
            return failed("没有权限。");
        }

        String filename = "spring.properties";
        Resource resource = new ClassPathResource(filename);
        File file = resource.getFile();
        String path = file.getParent();

        FileUtils.writerText(path, content, filename, false);

        return success();
    }

    @RequestMapping(value="/db_backup")
    public void db_backup(HttpServletRequest request, HttpServletResponse response) throws InterruptedException, IOException {

        boolean superAccount = CmTag.isSuperAccount(ShiroHelper.getCurrentUsername());
        if(!superAccount){
            response.setHeader("Set-Cookie", "fileDownload=false; path=/");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            return ;
        }

        String dbName = PropertiesUtils.getString("db.schema");
        String fileName = dbName + "(" + DateUtils.formatDate(new Date(), "YYYYMMddHHmmss") + ").sql";
        String savePath = PropertiesUtils.getString("upload.path") + File.separator + "backup";
        if(!FileUtils.exists(savePath)){
            new File(savePath).mkdir();
        }

        boolean backup = MySqlUtils.backup("localhost", PropertiesUtils.getString("jdbc_user"),
                PropertiesUtils.getString("jdbc_password"), savePath, fileName, dbName);
        if(backup) {
            // 打成压缩包下载
            Map<String, File> fileMap = new LinkedHashMap<>();
            fileMap.put(fileName, new File(savePath + File.separator + fileName));
            response.setHeader("Set-Cookie", "fileDownload=true; path=/");
            DownloadUtils.zip(fileMap, fileName, request, response);

            // 下载后从服务器删除
            FileUtils.delFile(savePath + File.separator + fileName);
        }

        logger.info(addLog(LogConstants.LOG_ADMIN, "下载备份数据库"));
    }
}
