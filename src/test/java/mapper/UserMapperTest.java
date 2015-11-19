package mapper;

import domain.SysUser;
import domain.SysUserExample;
import persistence.SysUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.SysUserService;
import shiro.PasswordHelper;
import sys.utils.MD5Util;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class UserMapperTest {

	@Autowired
	SysUserMapper userMapper;
	@Autowired
	SysUserService userService;
	@Autowired
	PasswordHelper passwordHelper;

	@Test
	public void testInsert() {

		/*User record = new User();
		record.setUsername("admin");
		record.setPasswd(MD5Util.md5Hex("admin" + "1", "UTF-8"));
		record.setUnitId(null);
		record.setCode("0");
		record.setPostId(null);
		record.setTypeId(null);
		record.setCreateTime(new Date());
		record.setYear((short) 2014);
		userMapper.insert(record );*/

		System.out.println(MD5Util.md5Hex("1", "UTF-8"));
	}

	@Test
	public void resetPasswd(){
		String username = "admin";
		SysUser user = userService.findByUsername(username);
		System.out.println(user.getRealname());
		String encrypt = passwordHelper.encryptBySalt("111111", user.getSalt());
		System.out.println(encrypt);
		SysUser record = new SysUser();
		record.setId(user.getId());
		record.setPasswd(encrypt);

		userService.updateByPrimaryKeySelective(record, user.getUsername());
	}

	@Test
	public void testSelectByExample() {

		SysUserExample example =  new SysUserExample();
		example.createCriteria().andUsernameEqualTo("admin");
		List<SysUser> userList = userMapper.selectByExample(example );
		System.out.println(userList.size());
	}

}
