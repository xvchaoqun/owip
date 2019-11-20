<#list cadreRewards as cadreReward>
<p>${rewardOnlyYear?string(cadreReward.rewardTime?string("yyyy年"),cadreReward.rewardTime?string("yyyy年MM月"))}，荣获“${cadreReward.name}”<#if cadreReward.rank?? && cadreReward.rank gt 0>(排名第${cadreReward.rank!})</#if>${((cadreReward.unit??) && (cadreReward.unit!=''))?string('，', '')}${cadreReward.unit!}<#if cadreReward_has_next>；</#if><#if !cadreReward_has_next>。</#if></p>
</#list>
<#if cadrePunishes?? && (cadrePunishes?size>0)>
<#list cadrePunishes as cadrePunish>
<p>${rewardOnlyYear?string(cadrePunish.punishTime?string("yyyy年"),cadrePunish.punishTime?string("yyyy年MM月"))}，${((cadrePunish.unit??) && (cadrePunish.unit!=''))?string('受到', '')}${cadrePunish.unit!}${((cadrePunish.unit??) && (cadrePunish.unit!=''))?string('给予的', '')}${cadrePunish.name!}处分<#if cadrePunish_has_next>；</#if><#if !cadrePunish_has_next>。</#if></p>
</#list>
</#if>