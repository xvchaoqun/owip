package service.common;

import domain.sys.SysUserView;
import domain.sys.SysUserViewExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.util.BytesRef;
import org.springframework.stereotype.Service;
import service.BaseMapper;
import sys.tool.lucene.UserSearchPage;
import sys.tool.lucene.UserSearchSupport;
import sys.tool.pinyin.PinYinUtils;
import sys.utils.PropertiesUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2018/4/22.
 */
@Service
public class LuceneService extends BaseMapper {

    private String indexPath = PropertiesUtils.getString("lucene.index.user");

    public void indexAllUsers(){

        UserSearchSupport uss = new UserSearchSupport(indexPath);
        SysUserViewExample example = new SysUserViewExample();
        example.setOrderByClause("id asc");
        int count = (int)sysUserViewMapper.countByExample(example);
        int pageSize = 1000;
        int pageNo = count/pageSize + (count%pageSize==0?0:1);

        for (int i = 0; i < pageNo; i++) {

            List<Document> docs = new ArrayList<>();
            List<SysUserView> sysUsers = sysUserViewMapper.selectByExampleWithRowbounds(example,
                    new RowBounds(i * pageSize, (i+1) * pageSize));
            for (SysUserView u : sysUsers) {

                if(u.getLocked()){
                    uss.delete(String.valueOf(u.getId()));
                }else{
                    String username = u.getUsername();
                    String code = u.getCode();
                    String realname = u.getRealname();
                    Date createTime = u.getCreateTime();
                    Document doc = new Document();
                    doc.add(new TextField("id", String.valueOf(u.getId()), Field.Store.YES));
                    doc.add(new TextField("type", String.valueOf(u.getType()), Field.Store.YES));
                    if(createTime!=null) {
                        String time = String.valueOf(createTime.getTime());
                        doc.add(new StoredField("orderStr", time));
                        doc.add(new SortedDocValuesField("orderStr", new BytesRef(time)));
                    }
                    if(StringUtils.isNotBlank(realname)) {
                        doc.add(new TextField("realname", realname, Field.Store.YES));
                        doc.add(new TextField("pinyin", PinYinUtils.getFullSpell(realname), Field.Store.NO));
                        doc.add(new TextField("firstPinyin", PinYinUtils.getFirstSpell(realname), Field.Store.NO));
                    }
                    if(StringUtils.isNotBlank(username)) {
                        doc.add(new TextField("username", username, Field.Store.YES));
                    }
                    if(StringUtils.isNotBlank(code)) {
                        doc.add(new TextField("code", code, Field.Store.YES));
                    }

                    docs.add(doc);
                }
            }

            uss.index(docs);
        }
    }

    public UserSearchPage searchUsers(String searchStr, Byte[] types, int pageNo, int pageSize) {

        UserSearchSupport uss = new UserSearchSupport(indexPath);
        return uss.search(searchStr, types, pageNo, pageSize);
    }

}
