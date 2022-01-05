package com.lq.testpostgresql;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import test.generated.tables.daos.SysUserDao;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

@RequestMapping("/test")
@Controller
public class Test {
    @Autowired
    DSLContext dslContext;

    @Autowired
    TransactionManager transactionManager;
    @Resource
    JdbcTemplate jdbcTemplate;

    @GetMapping("/testPgsql")
    @ResponseBody
    public String test(){
        HikariDataSource dataSource = (HikariDataSource) jdbcTemplate.getDataSource();
        System.out.println("dataSource.getPassword() = " + dataSource.getPassword());
        System.out.println("dataSource.getJdbcUrl() = " + dataSource.getJdbcUrl());
        System.out.println("dataSource.getUsername() = " + dataSource.getUsername());
        return "连接成功！";
    }

    @GetMapping("/testDsl")
    @ResponseBody
    public List testDsl(){
        SysUserDao sysUserDao = new SysUserDao();
        List<test.generated.tables.pojos.SysUser> all = sysUserDao.findAll();

        return  all;
    }
}
