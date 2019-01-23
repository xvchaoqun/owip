package service.base;

import domain.base.Sitemap;
import domain.base.SitemapExample;
import domain.sys.SysResource;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysResourceService;
import service.sys.SysUserService;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class SitemapService extends BaseMapper{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysResourceService sysResourceService;

	@Transactional
	@Caching(evict={
			@CacheEvict(value="_Sitemaps", allEntries=true)
	})
	public int insert(Sitemap sitemap){
		
		return sitemapMapper.insert(sitemap);
	}

	@Transactional
	@Caching(evict={
			@CacheEvict(value="_Sitemaps", allEntries=true)
	})
	public int updateByPrimaryKeySelective(Sitemap sitemap){
		
		return  sitemapMapper.updateByPrimaryKeySelective(sitemap);
	}
	
	@Transactional
	@Caching(evict={
			@CacheEvict(value="_Sitemaps", allEntries=true)
	})
	public void del(Integer id){
		
		sitemapMapper.deleteByPrimaryKey(id);
		
		SitemapExample example = new SitemapExample();
		example.createCriteria().andFidEqualTo(id);
		sitemapMapper.deleteByExample(example);
	}

	// 按层次递归读取
	private List<Sitemap> menuLoop(List<Sitemap> menuList, Integer fid){

		SitemapExample example = new SitemapExample();
		SitemapExample.Criteria criteria = example.createCriteria();
		if(fid==null){
			criteria.andFidIsNull();
		}else{
			criteria.andFidEqualTo(fid);
		}
		example.setOrderByClause("sort_order desc");
		List<Sitemap> list = sitemapMapper.selectByExample(example);

		for(Sitemap sitemap:list) {

			menuList.add(sitemap);
			List<Sitemap> subSitemaps = menuLoop(menuList, sitemap.getId());
			sitemap.setSubSitemaps(subSitemaps);
		}

		return list;
	}

	@Cacheable(value = "_Sitemaps")
	public Map<Integer, Sitemap> getSortedSitemaps(){

		List<Sitemap> menuList = new ArrayList<Sitemap>();
		menuLoop(menuList, null);

		//Map<Integer, SysRole> roleMap = sysRoleService.findAll();
		Map<Integer, Sitemap> map = new LinkedHashMap<>();
		for (Sitemap sitemap : menuList) {
			/*List<SysRole> sysRoles = new ArrayList<>();
			Set<Integer> roleIdSet = getRoleIdSet(sitemap.getId());
			for (Integer roleId : roleIdSet) {
				sysRoles.add(roleMap.get(roleId));
			}
			sitemap.setSysRoles(sysRoles);*/
			map.put(sitemap.getId(), sitemap);
		}

		return map;
	}

	/*@Transactional
	@CacheEvict(value="_Sitemaps", allEntries=true)
	public void updateRoles(int sitemapId, Integer[] roleIds){

		SitemapRoleExample example = new SitemapRoleExample();
		example.createCriteria().andSitemapIdEqualTo(sitemapId);
		sitemapRoleMapper.deleteByExample(example);

		if(roleIds!=null && roleIds.length>0){

			for (Integer roleId : roleIds) {
				SitemapRole record = new SitemapRole();
				record.setSitemapId(sitemapId);
				record.setRoleId(roleId);
				sitemapRoleMapper.insertSelective(record);
			}
		}
	}

	public Set<Integer> getRoleIdSet(int sitemapId){

		SitemapRoleExample example = new SitemapRoleExample();
		example.createCriteria().andSitemapIdEqualTo(sitemapId);
		List<SitemapRole> sitemapRoles = sitemapRoleMapper.selectByExample(example);
		Set<Integer> idSet = new HashSet<>();
		for (SitemapRole sitemapRole : sitemapRoles) {
			idSet.add(sitemapRole.getRoleId());
		}
		return idSet;
	}*/

	// 根据用户角色，得到对应的一组导航Id
	/*public Set<Integer> userSitemapIdSet(int userId){

		SysUserView sysUser = sysUserService.findById(userId);
		Set roleIds = sysUserService.getUserRoleIdSet(sysUser.getRoleIds());
		SitemapRoleExample example = new SitemapRoleExample();
		example.createCriteria().andRoleIdIn(new ArrayList<Integer>(roleIds));
		List<SitemapRole> sitemapRoles = sitemapRoleMapper.selectByExample(example);
		Set<Integer> idSet = new HashSet<>();
		for (SitemapRole sitemapRole : sitemapRoles) {
			idSet.add(sitemapRole.getSitemapId());
		}
		return idSet;
	}*/

	// 用户的导航列表（两级列表）
	public List<Sitemap> getUserTopSitemap(int userId) {

		SysUserView sysUser = sysUserService.findById(userId);
		Set<String> permissions = sysUserService.findPermissions(sysUser.getUsername(), false);
		Map<Integer, SysResource> resourceMap = sysResourceService.getSortedSysResources(false);

		List<Sitemap> topSitemap = new ArrayList<>();

		Map<Integer, Sitemap> sortedSitemaps = getSortedSitemaps();
		//Set<Integer> userSitemapIdSet = userSitemapIdSet(userId);

		Set<Integer> usedSitemapIdSet = new HashSet<>();
		for (Sitemap sitemap : sortedSitemaps.values()) {

			if(usedSitemapIdSet.contains(sitemap.getId())) continue;

 			Sitemap _siteMap = new Sitemap();
			try {
				BeanUtils.copyProperties(_siteMap, sitemap);
			} catch (IllegalAccessException e) {
				logger.error("异常", e);
				continue;
			} catch (InvocationTargetException e) {
				logger.error("异常", e);
				continue;
			}

			Integer resourceId = sitemap.getResourceId();
			SysResource sysResource = resourceId!=null?resourceMap.get(resourceId):null;
			String permission = (sysResource == null)?null:sysResource.getPermission();
			if (sitemap.getFid() == null &&
					// 一级节点可能没有权限要求
					(permission == null || permissions.contains(permission))) {

				List<Sitemap> _subSitemaps = new ArrayList<>();
				List<Sitemap> subSitemaps = sitemap.getSubSitemaps();
				if (subSitemaps != null && subSitemaps.size() > 0) {

					for (Sitemap subSitemap : subSitemaps) {

						Integer _resourceId = subSitemap.getResourceId();
						if (_resourceId == null) continue;
						SysResource _sysResource = resourceMap.get(_resourceId);
						if (_sysResource != null && permissions.contains(_sysResource.getPermission())) {
							usedSitemapIdSet.add(subSitemap.getId());
							_subSitemaps.add(subSitemap);
						}
					}

					_siteMap.setSubSitemaps(_subSitemaps);
				}

				usedSitemapIdSet.add(_siteMap.getId());
				topSitemap.add(_siteMap);
			}
		}

		return topSitemap;
	}

}
