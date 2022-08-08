package com.digitalLab.Config.Database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@MapperScan(value="com.digitalLab.Small.Mapper", sqlSessionFactoryRef = "SmallSqlSessionFactory")
@EnableTransactionManagement
public class SmallDatabaseConfig {
    @Bean(name = "SmallDataSource")
    @ConfigurationProperties(prefix="spring.small.datasource")
    public DataSource SmallDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name="SmallSqlSessionFactory")
    public SqlSessionFactory SmallSqlSessionFactory(@Qualifier("SmallDataSource") DataSource smallDataSource, ApplicationContext applicationContext) throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(smallDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:smallMappers/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.digitalLab.Entity");
        return sqlSessionFactoryBean.getObject();
    }

    public SqlSessionTemplate SmallSqlSessionTemplate(SqlSessionFactory smallSqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(smallSqlSessionFactory);
    }
}
