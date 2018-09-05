<#list cadreRewards as cadreReward>
<p>${cadreReward.rewardTime?string("yyyy")}年，荣获“${cadreReward.name}”<#if cadreReward.rank?? && cadreReward.rank gt 0>(排名第${cadreReward.rank!})</#if>${((cadreReward.unit??) && (cadreReward.unit!=''))?string('，', '')}${cadreReward.unit!}<#if cadreReward_has_next>；</#if><#if !cadreReward_has_next>。</#if></p>
</#list>