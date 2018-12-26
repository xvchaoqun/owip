package service.pcs;

import domain.pcs.PcsPrFile;
import domain.pcs.PcsPrFileExample;
import domain.pcs.PcsPrFileTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PcsPrFileService extends PcsBaseMapper {

    @Autowired
    protected PcsPrFileTemplateService pcsPrFileTemplateService;

    // 分党委已上传的大会材料列表
    public Map<Integer, PcsPrFile> fileMap(int configId, int partyId){

        // <templateId, PcsPrFile>
        Map<Integer, PcsPrFile> fileMap = new HashMap<>();
        PcsPrFileExample example = new PcsPrFileExample();
        example.createCriteria().andConfigIdEqualTo(configId).andPartyIdEqualTo(partyId);
        List<PcsPrFile> files = pcsPrFileMapper.selectByExample(example);
        for (PcsPrFile file : files) {
            fileMap.put(file.getTemplateId(), file);
        }

        return fileMap;
    }

    // 判断分党委是否上传完成所有的材料
    public boolean isUploadAll(int configId, int partyId){

        List<PcsPrFileTemplate> templates = pcsPrFileTemplateService.findAll(configId);
        Map<Integer, PcsPrFile> fileMap = fileMap(configId, partyId);

        for (PcsPrFileTemplate template : templates) {
            if(!fileMap.containsKey(template.getId()))
                return false;
        }

        // 有可能模板被管理员删除了，所以上传的数量比模板多
        return templates.size() <= fileMap.size();
    }


}
