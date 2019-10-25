<#list partyRewards as partyReward>
<p>${rewardOnlyYear?string(partyReward.rewardTime?string("yyyy年"),partyReward.rewardTime?string("yyyy年MM月"))}，荣获“${partyReward.name}”${((partyReward.unit??) && (partyReward.unit!=''))?string('，', '')}${partyReward.unit!}<#if partyReward_has_next>；</#if><#if !partyReward_has_next>。</#if></p>
</#list>
<#if partyPunishes??>
<#list partyPunishes as partyPunish>
<p>${rewardOnlyYear?string(partyPunish.punishTime?string("yyyy年"),partyPunish.punishTime?string("yyyy年MM月"))}，${((partyPunish.unit??) && (partyPunish.unit!=''))?string('受到', '')}${partyPunish.unit!}${((partyPunish.unit??) && (partyPunish.unit!=''))?string('给予的', '')}${partyPunish.name!}处分<#if partyPunish_has_next>；</#if><#if !partyPunish_has_next>。</#if></p>
</#list>
</#if>