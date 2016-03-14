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
	public void updateApplySelfNote(String applySelfNote){

		SysConfig sysConfig = get();

		SysConfig record = new SysConfig();
		record.setId(sysConfig.getId());
		record.setApplySelfNote(applySelfNote);
		sysConfigMapper.updateByPrimaryKeySelective(record);
	}
}
