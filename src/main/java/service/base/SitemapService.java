package service.base;

import domain.base.Sitemap;
import domain.base.SitemapExample;
import domain.base.SitemapRole;
import domain.base.SitemapRoleExample;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysUserService;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class SitemapService extends BaseMapper{

	@Autowired
	private SysUserService sysUserService;

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

		Map<Integer, Sitemap> map = new LinkedHashMap<>();
		for (Sitemap sitemap : menuList) {
			map.put(sitemap.getId(), sitemap);
		}

		return map;
	}

	@Transactional
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
	}

	// 根据用户角色，得到对应的一组导航Id
	public Set<Integer> userSitemapIdSet(int userId){

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
	}

	// 用户的导航列表
	public List<Sitemap> getUserTopSitemap(int userId) {

		List<Sitemap> topSitemap = new ArrayList<>();

		Map<Integer, Sitemap> sortedSitemaps = getSortedSitemaps();
		Set<Integer> userSitemapIdSet = userSitemapIdSet(userId);

		for (Sitemap sitemap : sortedSitemaps.values()) {

			Integer sitemapId = sitemap.getId();
			if(sitemap.getFid()==null && userSitemapIdSet.contains(sitemapId.intValue())){

				Sitemap _siteMap = new Sitemap();
				try {
					BeanUtils.copyProperties(_siteMap, sitemap);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					continue;
				}

				List<Sitemap> _subSitemaps = new ArrayList<>();
				List<Sitemap> subSitemaps = sitemap.getSubSitemaps();
				if(subSitemaps!=null && subSitemaps.size()>0){

					for (Sitemap subSitemap : subSitemaps) {

						if(userSitemapIdSet.contains(subSitemap.getId().intValue())){
							_subSitemaps.add(subSitemap);
						}
					}

					_siteMap.setSubSitemaps(_subSitemaps);
				}

				topSitemap.add(_siteMap);
			}
		}

		return topSitemap;
	}

}
