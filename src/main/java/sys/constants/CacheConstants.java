package sys.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lm on 2018/4/14.
 */
public class CacheConstants {

    // 菜单关联缓存数量( cache name: cache_counts)
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_EDU = 1;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_WORK = 2;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK = 3;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY = 4;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE = 5;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER = 6;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME = 7;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH = 9;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH = 11;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER = 12;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN = 10;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT = 8;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN = 13;
    public final static byte CACHE_KEY_MODIFY_BASE_APPLY = 14;


    public final static byte CACHE_KEY_ABROAD_PASSPORT_APPLY = 15;
    public final static byte CACHE_KEY_ABROAD_APPLY_SELF = 16;

    public final static byte CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_SELF = 17;
    public final static byte CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_TW = 18;
    public final static byte CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_OTHER = 19;
    public final static byte CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF = 20;

    public final static byte CACHE_KEY_TAIWAN_RECORD_HANDLE_TYPE = 21;

    public final static byte CACHE_KEY_CADRE_PARTY_TO_REMOVE = 22;

    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO = 30;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN = 31;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_POSTWORK = 32;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILY = 33;
    public final static byte CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILYABROAD = 34;

    public final static Map<Byte, String> CACHE_KEY_MAP = new LinkedHashMap<>();

    static {
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_EDU, "学习经历-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_WORK, "工作经历-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK, "出版著作情况-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY, "企业兼职情况-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE, "教学课程-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER, "发表论文情况-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME, "社会或学术兼职-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT, "主持科研项目情况-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN, "参与科研项目情况-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH, "教学成果及获奖情况-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH, "科研成果及获奖情况-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER, "其他奖励情况-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN, "培训情况-干部信息修改申请");

        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_BASE_APPLY, "干部基本信息修改");
        CACHE_KEY_MAP.put(CACHE_KEY_ABROAD_PASSPORT_APPLY, "办理证件审批");
        CACHE_KEY_MAP.put(CACHE_KEY_ABROAD_APPLY_SELF, "因私出国境审批（管理员）");

        CACHE_KEY_MAP.put(CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_SELF, "因私出国（境）-领取证件");
        CACHE_KEY_MAP.put(CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_TW, "因公赴台-领取证件");
        CACHE_KEY_MAP.put(CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_OTHER, "其他事务-领取证件");
        CACHE_KEY_MAP.put(CACHE_KEY_ABROAD_PASSPORT_DRAW_TYPE_LONG_SELF, "长期因公出国-领取证件");

        CACHE_KEY_MAP.put(CACHE_KEY_TAIWAN_RECORD_HANDLE_TYPE, "因公赴台备案-提醒管理员选择办理新证件方式");

        CACHE_KEY_MAP.put(CACHE_KEY_CADRE_PARTY_TO_REMOVE, "特殊党员干部库-已存在于党员信息库");

        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO, "专技岗位过程信息-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN, "管理岗位过程信息-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_POSTWORK, "工勤岗位过程信息-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILY, "家庭成员信息-干部信息修改申请");
        CACHE_KEY_MAP.put(CACHE_KEY_MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILYABROAD, "家庭成员移居国(境)外的情况-干部信息修改申请");
    }
}
