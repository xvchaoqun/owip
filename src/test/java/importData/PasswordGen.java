package importData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import shiro.PasswordHelper;
import sys.shiro.SaltPassword;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by fafa on 2016/12/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class PasswordGen {

    @Autowired
    private DataSource dataSource;
    @Autowired
    protected PasswordHelper passwordHelper;

    @Test
    public void gen() throws SQLException {

        Connection conn = dataSource.getConnection();
        Statement stat = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "select username, raw from tmp_import_ma";
            String updateSql = "update tmp_import_ma set salt=?, password=? where username=?";
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs != null && rs.next()) {
                String username = rs.getString("username");
                String raw = rs.getString("raw");

                SaltPassword encrypt = passwordHelper.encryptByRandomSalt(raw);
                String salt = encrypt.getSalt();
                String passsword = encrypt.getPassword();

                preparedStatement = conn.prepareStatement(updateSql);
                preparedStatement.setString(1, salt);
                preparedStatement.setString(2, passsword);
                preparedStatement.setString(3, username);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            //throw new Exception("连接数据库失败,请检查数据库连接。");
        } finally {
            //close(conn, stat, rs);
            try {
                stat.close();
                preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
