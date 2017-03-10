<#list dataList as row>
<w:p wsp:rsidR="002174D1" wsp:rsidRPr="00AA7960" wsp:rsidRDefault="002174D1" wsp:rsidP="002174D1">
    <w:pPr>
        <w:spacing w:line="480" w:line-rule="exact"/>
        <w:ind w:first-line-chars="235" w:first-line="661"/>
        <w:rPr>
            <w:rFonts w:ascii="宋体" w:h-ansi="宋体"/>
            <wx:font wx:val="宋体"/>
            <w:b/>
            <w:sz w:val="28"/>
            <w:sz-cs w:val="28"/>
        </w:rPr>
    </w:pPr>
    <#--<w:r wsp:rsidRPr="00AA7960">
        <w:rPr>
            <w:rFonts w:ascii="宋体" w:h-ansi="宋体" w:hint="fareast"/>
            <wx:font wx:val="宋体"/>
            <w:b/>
            <w:sz w:val="28"/>
            <w:sz-cs w:val="28"/>
        </w:rPr>
        <w:t>不足或希望：</w:t>
    </w:r>-->
    <w:r wsp:rsidRPr="00AA7960">
        <w:rPr>
            <w:rFonts w:ascii="宋体" w:h-ansi="宋体" w:hint="fareast"/>
            <wx:font wx:val="宋体"/>
            <w:sz w:val="28"/>
            <w:sz-cs w:val="28"/>
        </w:rPr>
        <w:t>${row}</w:t>
    </w:r>
</w:p>
</#list>