package com.lq.testpostgresql;

import com.lq.testpostgresql.tables.pojos.SysUser;
import com.lq.testpostgresql.tables.records.SysUserRecord;
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
import com.lq.testpostgresql.tables.daos.SysUserDao;

import javax.annotation.Resource;
import java.util.List;


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
    @Autowired
    private SysUserDao sysUserDao;
    @GetMapping("/testDsl")
    @ResponseBody
    public List testDsl(){

        Result<Record> fetch = dslContext.select().from(Tables.SYS_USER).where(Tables.SYS_USER.USER_ID.eq(1L)).fetch();
        List<SysUserRecord> into = fetch.into(SysUserRecord.class);
        into.forEach(r->{
            System.out.println("r.getUserName() = " + r.getUserName());
            System.out.println("r.getPassword() = " + r.getPassword());
        });
//        List<SysUser> all = sysUserDao.findAll();
        List<SysUser> all = sysUserDao.findAll();

        return  all;
    }
}
