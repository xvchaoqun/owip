<#if title??>
    <w:p wsp:rsidR="00E759EF" wsp:rsidRPr="00B62C14" wsp:rsidRDefault="003445E9" wsp:rsidP="0011161C">
        <w:pPr>
            <w:spacing w:line="${line}" w:line-rule="exact"/>
            <w:ind w:left="1841" w:hanging="1841"/>
            <w:rPr>
                <w:b/>
            </w:rPr>
        </w:pPr>
        <w:r wsp:rsidRPr="00B62C14">
            <w:rPr>
                <w:rFonts w:hint="fareast"/>
                <wx:font wx:val="宋体"/>
                <w:b/>
            </w:rPr>
            <w:t>${title}：</w:t>
        </w:r>
    </w:p>
</#if>
<#list dataList as row>
    <w:p wsp:rsidR="007F619C" wsp:rsidRPr="009B666C" wsp:rsidRDefault="007F619C" wsp:rsidP="009B666C">
        <w:pPr>
            <w:spacing w:line="${line}" w:line-rule="exact"/>
            <#if needHanging?? && needHanging>
                <w:ind w:left="1800" w:hanging="${row[1]?starts_with("（")?string("236","1800")}"/>
            </#if>
        </w:pPr>
        <#list row as col>
            <#if col_index!=0>
                <w:r wsp:rsidRPr="009B666C">
                    <w:rPr>
                        <w:rFonts w:ascii="Times New Roman" w:fareast="仿宋_GB2312" w:h-ansi="Times New Roman"/>
                        <wx:font wx:val="Times New Roman"/>
                        <w:sz w:val="22"/>
                    </w:rPr>
                    <w:t><#if needWhiteSpace?? && needWhiteSpace>    </#if>${col}</w:t>
                </w:r>
                <#if col_has_next>
                    <w:r wsp:rsidRPr="009B666C">
                        <w:rPr>
                            <w:rFonts w:ascii="Times New Roman" w:fareast="仿宋_GB2312" w:h-ansi="Times New Roman"/>
                            <wx:font wx:val="Times New Roman"/>
                            <w:sz w:val="22"/>
                        </w:rPr>
                        <w:t></w:t>
                    </w:r>
                </#if>
            </#if>
        </#list>
    </w:p>
</#list>
