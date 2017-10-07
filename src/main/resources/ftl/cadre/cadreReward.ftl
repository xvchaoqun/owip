<#list cadreRewards as cadreReward>
<p>${cadreReward.rewardTime?string("yyyy")}年&nbsp;${cadreReward.name}<#if cadreReward.rank gt 0>(排名第${cadreReward.rank})</#if>${((cadreReward.unit??) && (cadreReward.unit==''))?string('，', '')}${cadreReward.unit!}</p>
</#list>