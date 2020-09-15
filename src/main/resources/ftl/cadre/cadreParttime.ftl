<#list cadreParttimes as cadreParttime>
    <p>${(cadreParttime.startTime?string("yyyy.MM"))!}${cadreParttime.endTime???string("—","—至今")}${(cadreParttime.endTime?string("yyyy.MM"))!}
        &nbsp;${cadreParttime.unit!}${cadreParttime.post!}</p>
</#list>