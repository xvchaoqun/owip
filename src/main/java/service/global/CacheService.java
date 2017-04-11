package service.global;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.BaseController;
import domain.abroad.ApproverType;
import domain.base.Location;
import domain.base.MetaType;
import domain.dispatch.DispatchType;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysRole;
import domain.train.TrainEvaTable;
import domain.unit.Unit;
import mixin.MetaTypeOptionMixin;
import mixin.OptionMixin;
import mixin.PartyOptionMixin;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import sys.constants.SystemConstants;
import sys.utils.ConfigUtil;
import sys.utils.FileUtils;
import sys.utils.JSONUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fafa on 2017/4/11.
 */
@Service
public class CacheService extends BaseController{

    private static String getJsFolder(){

        return ConfigUtil.defaultHomePath() + File.separator + "js";
    }

    public void flushLocation(){

        String content = "function Location() {this.items=" + locationService.toJSON() + ";}";
        FileUtils.writerText(getJsFolder(), content, "location.js", false);
    }

    public void flushMetadata() {

        /*Map map = new HashMap();
        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        for (MetaType metaType : metaTypeMap.values()) {
            map.put(metaType.getId(), metaType.getName());
        }
        modelMap.put("metaTypeMap", JSONUtils.toString(map));*/

        Map cMap = new HashMap();

        Map constantMap = new HashMap();
        Field[] fields = SystemConstants.class.getFields();
        for (Field field : fields) {
            if (StringUtils.equals(field.getType().getName(), "java.util.Map")) {
                try {
                    constantMap.put(field.getName(), field.get(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        cMap.putAll(constantMap);

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        cMap.put("metaTypeMap", metaTypeMap);

        Map metaMap = getMetaMap();
        cMap.putAll(metaMap);


        ObjectMapper mapper = JSONUtils.buildObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);

        Map<Class<?>, Class<?>> sourceMixins = new HashMap<>();
        sourceMixins.put(MetaType.class, MetaTypeOptionMixin.class);
        sourceMixins.put(Party.class, PartyOptionMixin.class);
        sourceMixins.put(Branch.class, PartyOptionMixin.class);
        //sourceMixins.put(Dispatch.class, OptionMixin.class);
        //sourceMixins.put(DispatchUnit.class, OptionMixin.class);
        sourceMixins.put(Unit.class, OptionMixin.class);
        //sourceMixins.put(Cadre.class, OptionMixin.class);
        sourceMixins.put(DispatchType.class, OptionMixin.class);
        //sourceMixins.put(SafeBox.class, OptionMixin.class);
        sourceMixins.put(ApproverType.class, OptionMixin.class);
        sourceMixins.put(Location.class, OptionMixin.class);
        //sourceMixins.put(Country.class, OptionMixin.class);

        sourceMixins.put(TrainEvaTable.class, OptionMixin.class);

        sourceMixins.put(SysRole.class, OptionMixin.class);

        mapper.setMixIns(sourceMixins);

        // 删除目前不需要的
        cMap.remove("dispatchMap");
        cMap.remove("cadreMap");
        cMap.remove("countryMap");
        cMap.remove("dispatchCadreMap");
        cMap.remove("safeBoxMap");

        try {
            FileUtils.writerText(getJsFolder(), "var _cMap=" + mapper.writeValueAsString(cMap), "metadata.js", false);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
