package sys.tags;

import domain.MetaClass;
import domain.MetaType;
import domain.SysResource;
import domain.SysUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import service.*;

import java.util.*;

public class CmTag {

	static ApplicationContext context = ApplicationContextSupport.getContext();
	static SysUserService sysUserService = (SysUserService) context.getBean("sysUserService");
	static SysResourceService sysResourceService = (SysResourceService) context.getBean("sysResourceService");
	static MetaTypeService metaTypeService = (MetaTypeService) context.getBean("metaTypeService");
	static MetaClassService metaClassService = (MetaClassService) context.getBean("metaClassService");

	public static SysResource getSysResource(Integer id){

		Map<Integer, SysResource> sortedSysResources = sysResourceService.getSortedSysResources();

		return sortedSysResources.get(id);
	}
	public static SysResource getCurrentSysResource(String _path){

		return sysResourceService.getByUrl(_path);
	}

	public static MetaType getMetaType(String classCode, Integer id){

		if(StringUtils.isBlank(classCode) || id==null) return null;

		Map<Integer, MetaType> metaTypeMap = metaTypeService.metaTypes(classCode);
		return metaTypeMap.get(id);
	}

	public static MetaType getMetaTypeByCode(String code){

		if(StringUtils.isBlank(code)) return null;

		Map<String, MetaType> metaTypeMap = metaTypeService.codeKeyMap();
		return metaTypeMap.get(code);
	}

	public static Set getParentIdSet(String _path){

		Set parentIdSet = new LinkedHashSet();
		SysResource sysResource = sysResourceService.getByUrl(_path);
		if(sysResource==null) return parentIdSet;

		String parentIds = sysResource.getParentIds();
		for (String str : parentIds.split("/")) {
			parentIdSet.add(Integer.parseInt(str));
		}
		return parentIdSet;
	}

	public static SysUser getUserById(Integer id){

		return sysUserService.findById(id);
	}

	public static SysUser getUserByUsername(String username){

		return sysUserService.findByUsername(username);
	}

	// 判断类别ID和代码是否匹配，比如判断党组织是否是直属党支部
	public static Boolean typeEqualsCode(Integer typeId, String code){

		if(StringUtils.isBlank(code) || typeId==null) return false;

		Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
		MetaType metaType = metaTypeMap.get(typeId);

		return StringUtils.equalsIgnoreCase(metaType.getCode(), code);
	}

	@Deprecated
	public static Boolean classEqualsCode(Integer classId, String code){

		if(StringUtils.isBlank(code) || classId==null) return false;

		Map<Integer, MetaClass> metaClassMap = metaClassService.findAll();
		MetaClass metaClass = metaClassMap.get(classId);

		return StringUtils.equalsIgnoreCase(metaClass.getCode(), code);
	}
}
