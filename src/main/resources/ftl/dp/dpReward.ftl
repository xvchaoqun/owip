<#list dpRewards as dpReward>
<p>${rewardOnlyYear?string(dpReward.rewardTime?string("yyyy年"),dpReward.rewardTime?string("yyyy年MM月"))}，荣获“${dpReward.name}”<#if dpReward.rank?? && dpReward.rank gt 0>(排名第${dpReward.rank!})</#if>${((dpReward.unit??) && (dpReward.unit!=''))?string('，', '')}${dpReward.unit!}<#if dpReward_has_next>；</#if><#if !dpReward_has_next>。</#if></p>
</#list>
<#if dpPunishes?? && (dpPunishes?size>0)>
<#list dpPunishes as dpPunish>
<p>${rewardOnlyYear?string(dpPunish.punishTime?string("yyyy年"),dpPunish.punishTime?string("yyyy年MM月"))}，${((dpPunish.unit??) && (dpPunish.unit!=''))?string('受到', '')}${dpPunish.unit!}${((dpPunish.unit??) && (dpPunish.unit!=''))?string('给予的', '')}${dpPunish.name!}处分<#if dpPunish_has_next>；</#if><#if !dpPunish_has_next>。</#if></p>
</#list>
</#if>