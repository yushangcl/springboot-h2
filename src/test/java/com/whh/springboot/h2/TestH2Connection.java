package com.whh.springboot.h2;

import com.whh.springboot.h2.utils.JdbcUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.*;

/**
 * @author huahui.wu.
 *         Created on 2018/2/6.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestH2Connection {


    private static final Logger log = LoggerFactory.getLogger(TestH2Connection.class);

    @Test
    public void testH2Connection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test", "root", "123456");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from TEST");
        while (rs.next()) {
            log.debug("ID:" + rs.getInt("ID") + "," + "NAME:" + rs.getString("NAME"));
        }
        rs.close();
        stmt.close();
        conn.close();
    }



}
