package com.whh.springboot.h2;

import com.whh.springboot.h2.utils.JdbcUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;

/**
 * @author huahui.wu.
 *         Created on 2018/2/7.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestJdbcH2Connection {
    private static final Logger log = LoggerFactory.getLogger(TestJdbcH2Connection.class);


    @Test
    public void testJdbcH2Connection() throws Exception {
        JdbcUtil jdbcUtil = new JdbcUtil();
        ResultSet rs = jdbcUtil.executeQuery("SELECT * from TEST");
        while (rs.next()) {
            log.debug("ID:" + rs.getInt("ID") + "," + "NAME:" + rs.getString("NAME"));
        }
        jdbcUtil.close();
    }

}
