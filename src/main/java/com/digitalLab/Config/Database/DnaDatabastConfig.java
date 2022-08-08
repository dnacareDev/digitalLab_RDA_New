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
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(value="com.digitalLab.Mapper", sqlSessionFactoryRef = "DnaSqlSessionFactory")
@EnableTransactionManagement
@EnableAutoConfiguration
public class DnaDatabastConfig {
    @Primary
    @Bean(name="DnaDataSource")
    @ConfigurationProperties(prefix = "spring.dna.datasource")
    public DataSource DnaDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name="DnaSqlSessionFactory")
    @Primary
    public SqlSessionFactory DnaSqlSessionFactory(@Qualifier("DnaDataSource") DataSource dnaDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dnaDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.digitalLab.Entity");
        return sqlSessionFactoryBean.getObject();
    }
    public SqlSessionTemplate DnaSqlSessionTemplate(SqlSessionFactory dnaSqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(dnaSqlSessionFactory);
    }
}
