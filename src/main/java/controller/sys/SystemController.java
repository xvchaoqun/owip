package controller.sys;

import controller.BaseController;
import org.apache.commons.lang3.StringUtils;
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
import sys.constants.LogConstants;
import sys.security.Base64Utils;
import sys.spring.UserResUtils;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("system:db")
    @RequestMapping("db")
    public String db(ModelMap modelMap) throws IOException {

        return "sys/system/db";
    }

    @RequiresPermissions("system:cmd")
    @RequestMapping("cmd")
    public String cmd(ModelMap modelMap) throws IOException {

        modelMap.put("isLinux", SystemInfo.isOSLinux());

        return "sys/system/cmd";
    }

    @RequiresPermissions("system:cmd")
    @RequestMapping(value = "cmd", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cmd(String cmd) throws Exception {

        cmd = new String(Base64Utils.decode(cmd), "utf-8");
        List<String> returnLines = CMDUtils.run(cmd);

        logger.info(addLog(LogConstants.LOG_ADMIN, "执行cmd:%s", cmd));

        Map<String, Object> resultMap = success();
        resultMap.put("cmd", cmd);
        resultMap.put("lines", returnLines);

        return resultMap;
    }

    @RequiresPermissions("system:cmd")
    @RequestMapping(value = "cmd_export")
    public void cmd_export(String cmd, HttpServletRequest request, HttpServletResponse response) throws Exception {

        cmd = new String(Base64Utils.decode(cmd), "utf-8");

        String tmpFile = "/tmp/" + DateUtils.formatDate(new Date(), "YYYYMMddHHmmss") + ".log.gz";
        cmd += " | gzip >" + tmpFile;

        try {

            logger.debug("start cmd:{}", cmd.trim());

            Process process = Runtime.getRuntime().exec(
                    new String[]{"/bin/sh", "-c", cmd.trim()});
            process.waitFor();

            //DownloadUtils.addFileDownloadCookieHeader(response);
            DownloadUtils.download(request, response, tmpFile);
            return;

        } catch (IOException e) {
            logger.error("异常", e);
        } catch (InterruptedException e) {
            logger.error("异常", e);
        }

        response.setHeader("Set-Cookie", "fileDownload=false; path=/");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        return;
    }

    @RequiresPermissions("system:sql")
    @RequestMapping("sql")
    public String sql(ModelMap modelMap) throws IOException {

        return "sys/system/sql";
    }

    @RequiresPermissions("system:sql")
    @RequestMapping(value = "sql", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sql(String sql) throws Exception {

        sql = new String(Base64Utils.decode(sql), "utf-8");

        if(SystemInfo.isOSLinux()) {
            sql = sql.replaceAll("`", "\\\\`");
        }

        String host = PatternUtils.withdraw("//(.*):", PropertiesUtils.getString("jdbc_url"));
        String user = PropertiesUtils.getString("jdbc_user");
        String password = PropertiesUtils.getString("jdbc_password");
        String schema = PropertiesUtils.getString("db.schema");
        List<String> returnLines = MySqlUtils.excuteSql(host, user, password, schema, sql);

        logger.info(addLog(LogConstants.LOG_ADMIN, "执行sql:%s", sql));

        Map<String, Object> resultMap = success();
        resultMap.put("lines", returnLines);
        return resultMap;
    }
    @RequiresPermissions("system:sql")
    @RequestMapping(value = "sql_export", method = RequestMethod.POST)
    @ResponseBody
    public Map do_sql_export(String sql) throws Exception {

        sql = new String(Base64Utils.decode(sql), "utf-8");

        if(SystemInfo.isOSLinux()) {
            sql = sql.replaceAll("`", "\\\\`");
        }

        String host = PatternUtils.withdraw("//(.*):", PropertiesUtils.getString("jdbc_url"));
        String user = PropertiesUtils.getString("jdbc_user");
        String password = PropertiesUtils.getString("jdbc_password");
        String schema = PropertiesUtils.getString("db.schema");
        MySqlUtils.Response response = MySqlUtils.exportAsCsv(host, user, password, schema, springProps.uploadPath, sql);

        logger.info(addLog(LogConstants.LOG_ADMIN, "导出sql:%s", sql));

        Map<String, Object> resultMap = success();
        resultMap.put("ret", response.success);
        resultMap.put("filePath", UserResUtils.sign(response.filePath));
        resultMap.put("lines", response.lines);

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

        /*boolean superAccount = CmTag.isSuperAccount(ShiroHelper.getCurrentUsername());
        if (!superAccount) {
            return failed("没有权限。");
        }*/

        String filename = "spring.properties";
        Resource resource = new ClassPathResource(filename);
        File file = resource.getFile();
        String path = file.getParent();

        FileUtils.writerText(path, content, filename, false);

        return success();
    }

    @RequiresPermissions("system:db")
    @RequestMapping(value = "/db_backup")
    public void db_backup(HttpServletRequest request, String db, HttpServletResponse response) throws InterruptedException, IOException {

        /*boolean superAccount = CmTag.isSuperAccount(ShiroHelper.getCurrentUsername());
        if (!superAccount) {
            response.setHeader("Set-Cookie", "fileDownload=false; path=/");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            return;
        }*/

        if(StringUtils.isBlank(db)) {
            db = PropertiesUtils.getString("db.schema");
        }
        String fileName = db + "(" + DateUtils.formatDate(new Date(), "YYYYMMddHHmmss") + ").sql";

        String tmpdir = System.getProperty("java.io.tmpdir") + FILE_SEPARATOR +
                DateUtils.getCurrentTimeMillis() + FILE_SEPARATOR + "dbbackup";
        FileUtils.mkdirs(tmpdir, false);
        String host = PatternUtils.withdraw("//(.*):", PropertiesUtils.getString("jdbc_url"));

        MySqlUtils.Response resp = MySqlUtils.dbBackup(host, PropertiesUtils.getString("jdbc_user"),
                PropertiesUtils.getString("jdbc_password"), tmpdir, fileName, db);

        // 打成压缩包下载
        Map<String, File> fileMap = new LinkedHashMap<>();
        fileMap.put(fileName, new File(tmpdir + File.separator + fileName));

        DownloadUtils.addFileDownloadCookieHeader(response);
        DownloadUtils.zip(fileMap, fileName, request, response);
        // 下载后从服务器删除
        FileUtils.deleteDir(new File(tmpdir));

        logger.info(log(LogConstants.LOG_ADMIN, "下载备份数据库：{0}，结果：{1}，{2}",
                db, resp.success, StringUtils.join(resp.lines, "\n")));
    }
}
