package controller;

import domain.sys.SysResource;
import domain.sys.SysResourceExample;
import job.Test;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.apache.commons.lang.math.RandomUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sys.quartz.QuartzManager;
import sys.spring.Base64File;
import sys.spring.RequestBase64Image;
import sys.utils.ConfigUtil;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.*;
import java.util.function.Consumer;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by fafa on 2016/1/18.
 */
@Controller
@RequestMapping("/m/test")
public class TestController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //@Autowired
    //TestServcie testServcie;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @RequestMapping(value = "/takePhoto")
    @ResponseBody
    public Map takePhoto(MultipartFile _photo){

        String folder = "test";
        upload(_photo, folder);

        return success();

    }

    @RequestMapping(value = "/startjob1")
    @ResponseBody
    public String startjob1() throws SchedulerException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.SECOND, RandomUtils.nextInt(10) + 5);

        String jobName = "job_1";
        Scheduler sched = schedulerFactoryBean.getScheduler();
        QuartzManager.startJob(sched, jobName, Test.class, cal.getTime());

        return "start job " + jobName + " at " + DateUtils.formatDate(cal.getTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    @RequestMapping(value = "/startjob")
    @ResponseBody
    public String startjob(int userId) throws SchedulerException {

        String jobName = "job_"+userId;

        JobDetail jobDetail = newJob(Test.class).withIdentity(jobName, "job_group_oa")
                .usingJobData("userId", userId).build();

        Scheduler sched = schedulerFactoryBean.getScheduler();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.SECOND, 10);

        Trigger trigger = newTrigger().withIdentity(jobName, "trigger_group_oa")
                .startAt(cal.getTime()).build();// 触发器时间设定

        sched.scheduleJob(jobDetail, trigger);
        if (!sched.isShutdown()) {
            sched.start();// 启动
        }

        return "start job " + jobName + " at " + DateUtils.getCurrentDateTime(DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

    @RequestMapping(value = "/stopjob")
    @ResponseBody
    public String stopjob(int userId) throws SchedulerException {

        Scheduler sched = schedulerFactoryBean.getScheduler();

        String jobName = "job_"+userId;
        TriggerKey triggerKey = new TriggerKey(jobName, "trigger_group_oa");
        sched.pauseTrigger(triggerKey);// 停止触发器
        sched.unscheduleJob(triggerKey);// 移除触发器
        JobKey jobKey = new JobKey(jobName, "job_group_oa");

        sched.deleteJob(jobKey);// 删除任务

        return "stop job " + jobName + " at " + DateUtils.getCurrentDateTime(DateUtils.YYYY_MM_DD_HH_MM);
    }

    @RequestMapping(value = "/jobs")
    @ResponseBody
    public Map jobs(){

        Scheduler sched = schedulerFactoryBean.getScheduler();
        Map<String, Map<String, Object>> stringMapMap = QuartzManager.queryAllJobs(sched);

        return stringMapMap;
    }

    @RequestMapping(value = "/updateSysResource")
    @ResponseBody
    public Map updateSysResource(){

        SysResourceExample example = new SysResourceExample();
        List<SysResource> sysResources = sysResourceMapper.selectByExample(example);

        for (SysResource sysResource : sysResources) {

            Integer parentId = sysResource.getParentId();
            if(parentId!=null) {
                SysResource parent = sysResourceMapper.selectByPrimaryKey(parentId);
                if(parent!=null) {
                    SysResource record = new SysResource();
                    record.setId(sysResource.getId());
                    record.setParentIds(parent.getParentIds() + parentId + "/");
                    sysResourceMapper.updateByPrimaryKeySelective(record);
                }
            }
        }
        return success();
    }

    // 保存base64图片
    //@RequestMapping(value = "/base64", method = RequestMethod.POST)
    @RequestMapping(value = "/base64")
    @ResponseBody
    public String base64(@RequestBase64Image Base64File file, @RequestBase64Image List<Base64File> file2) {
        try {
            System.out.println(Arrays.toString(file.getBytes()));

            System.out.println("=============================");
            file2.stream().forEach(new Consumer<Base64File>() {

                @Override
                public void accept(Base64File t) {
                    try {
                        System.out.println(Arrays.toString(t.getBytes()));
                    } catch (IOException e) {
                        logger.error("异常", e);
                    }
                }
            });

            file.transferTo(new File("d:/test.jpg"));
        } catch (IOException e) {
            logger.error("异常", e);
        }
        return FormUtils.SUCCESS;
    }

    //@RequestMapping("/toMember")
   /* @ResponseBody
    public String toMember(int userId) {

        testServcie.toMember(userId);
        return FormUtils.SUCCESS;
    }

    //@RequestMapping("/toGuest")
    @ResponseBody
    public String toGuest(int userId) {

        testServcie.toGuest(userId);
        return FormUtils.SUCCESS;
    }
*/
    //@RequestMapping(value = "/report2")
    public String report2(Integer type, Model model) throws IOException {

        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "12312");
        map.put("name", "张三");
        map.put("partyName", "历史学院党总支");
        map.put("age", "25");
        map.put("nation", "汉");
        map.put("from", "历史学院党总支");
        map.put("to", "历史学院党总支");
        map.put("idcard", "360502198547544784");
        map.put("year", "2016");
        map.put("month", "5");
        map.put("day", "15");
        map.put("tel", "010-84572014");
        map.put("fax", "010-84572014");
        map.put("postCode", "100875");
        map.put("imagePath", ConfigUtil.defaultConfigPath() + FILE_SEPARATOR + "jasper" + FILE_SEPARATOR);
        data.add(map);
        // 报表数据源
        JRDataSource jrDataSource = new JRMapCollectionDataSource(data);

        ///BaseFont font = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        // 动态指定报表模板url
        model.addAttribute("url", "/WEB-INF/jasper/report2.jasper");
        //model.addAttribute("url", "/WEB-INF/jasper/2.jasper");
        if (type != null)
            model.addAttribute("url", "/WEB-INF/jasper/report2_noimg.jasper");
        model.addAttribute("format", "pdf"); // 报表格式
        model.addAttribute("jrMainDataSource", jrDataSource);

        return "iReportView"; // 对应jasper-defs.xml中的bean id
    }
}
