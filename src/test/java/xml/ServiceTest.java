package xml;

import bean.CadreInfoForm;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.dom4j.DocumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.cadre.CadreAdformService;
import service.cadre.CadreService;
import service.sys.SysUserService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ServiceTest {

	@Autowired
	CadreAdformService cadreAdformService;
	@Autowired
	CadreService cadreService;
	@Autowired
	SysUserService sysUserService;

	@Test
	public void zzbrm() throws IOException, DocumentException {

		String code = "06172";
		SysUserView uv = sysUserService.findByCode(code);
		CadreView cv = cadreService.dbFindByUserId(uv.getUserId());
		int cadreId = cv.getId();

		FileWriter output = new FileWriter(new File("D:/tmp/zzbrm/"+uv.getRealname()+".lrmx"));
		CadreInfoForm adform = cadreAdformService.getCadreAdform(cadreId);
		cadreAdformService.zzb(adform, output);
	}

}
