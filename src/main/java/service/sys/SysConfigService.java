package service.sys;

import domain.SysConfig;
import domain.SysConfigExample;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import service.BaseMapper;

import java.util.List;

@Service
public class SysConfigService extends BaseMapper {

	@Cacheable(value="sysConfigCache")
	public SysConfig get(){
		
		SysConfigExample example = new SysConfigExample();
		List<SysConfig> sysConfigs = sysConfigMapper.selectByExample(example);

		if(sysConfigs.size()==0){
			SysConfig record = new SysConfig();
			sysConfigMapper.insert(record);

			sysConfigs = sysConfigMapper.selectByExample(example);
		}
		return sysConfigs.get(0);
	}

	@CacheEvict(value="sysConfigCache", allEntries = true)
	public void updateApplySelfNote(String note){

		SysConfig sysConfig = get();

		SysConfig record = new SysConfig();
		record.setId(sysConfig.getId());
		record.setApplySelfNote(note);
		sysConfigMapper.updateByPrimaryKeySelective(record);
	}

	@CacheEvict(value="sysConfigCache", allEntries = true)
	public void updateApplySelfApprovalNote(String note){

		SysConfig sysConfig = get();

		SysConfig record = new SysConfig();
		record.setId(sysConfig.getId());
		record.setApplySelfApprovalNote(note);
		sysConfigMapper.updateByPrimaryKeySelective(record);
	}
}
