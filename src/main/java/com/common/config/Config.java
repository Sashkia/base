package com.common.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import com.common.dao.security.User;
import com.common.service.UserService;

@Configuration
@ComponentScan(basePackages = { "com.coommon", "com.coommon.service" })
@EnableTransactionManagement
@PropertySource(value = "classpath:util.properties")
public class Config {
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("${jdbc.hsqldb.driverClass}")
	private String driverClass;
	@Value("${jdbc.hsqldb.url}")
	private String jdbcUrl;
	@Value("${jdbc.hsqldb.username}")
	private String jdbcUserName;
	@Value("${jdbc.hsqldb.password}")
	private String jdbcPassword;

	@Value("classpath:dbschema.sql")
	private Resource dbschemaSqlScript;
	@Value("classpath:test-data.sql")
	private Resource testDataSqlScript;

	@Bean(name = "dataSource")
	public DriverManagerDataSource getDriverManagerDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(jdbcUserName);
		dataSource.setPassword(jdbcPassword);
		return dataSource;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer() {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(getDriverManagerDataSource());
		initializer.setDatabasePopulator(getDatabasePopulator());
		return initializer;
	}

	private DatabasePopulator getDatabasePopulator() {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(dbschemaSqlScript);
		populator.addScript(testDataSqlScript);
		return populator;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPackagesToScan(new String[] { "com.common.dao" });
		em.setDataSource(getDriverManagerDataSource());

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		((HibernateJpaVendorAdapter) vendorAdapter).setGenerateDdl(true);
		((HibernateJpaVendorAdapter) vendorAdapter).setShowSql(true);
		em.setJpaVendorAdapter(vendorAdapter);

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect",
				"org.hibernate.dialect.MySQLDialect");
		jpaProperties.put("hibernate.show_sql", true);
		jpaProperties.put("hibernate.format_sql", "false");
		jpaProperties.put("hibernate.hbm2ddl.auto", "update");

		em.setJpaProperties(jpaProperties);
		return em;
	}

	@Bean(name = "jpaTransactionManager")
	public JpaTransactionManager getJpaTransactionManager() {
		JpaTransactionManager jpa = new JpaTransactionManager();
		jpa.setEntityManagerFactory(getLocalContainerEntityManagerFactoryBean()
				.getNativeEntityManagerFactory());
		return jpa;
	}

	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DriverManagerDataSource dataSource) {

		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(
				dataSource);

		sessionBuilder.scanPackages("com.common.dao.security");

		return sessionBuilder.buildSessionFactory();
	}

}
