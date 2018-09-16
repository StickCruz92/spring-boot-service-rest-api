package com.profesores.demo.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*@Configuration*/
/* Trabjar con un bean para trabjar con un interface que puede implementar de muchas formas o muchos comportamientos */

/*@EnableTransactionManagement*/
/* Conexion a base a datos ayudas a las transaciones (modulo de base de datos) que todas accion a la base de datos se ejecuque de moto transacional*/

@Configuration
@EnableTransactionManagement
public class DataBaseConfiguration {

	/*Cual se utiliza la etiqueta @Configuration tiene que ir acompañada de sus @Bean que ayuda al compotamiento y con quien se va 
	 * a instanciar. */
	
	/*LocalSessionFactoryBean*/
	@Bean
	public LocalSessionFactoryBean sessionFactory(){
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean(); 
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan("com.profesores.demo.model");
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		return sessionFactoryBean;
	}
	
	/*Object datasource*/
	/*Trascribe toda la confiración de hibernete de xml de la conexion a la base de datos a un 
	 * metodo en un clase con anotaciones o manejando la persistencia de los datos.*/
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/teachers");
		dataSource.setUsername("teachers");
		dataSource.setPassword("teachers");
		
		return dataSource;
	}
	
	/*Metodo de propiedades hibernate*/
	/*No es necesario que este persistiendo*/
	public Properties hibernateProperties(){
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("show_sql", "true");
		
		return properties;
	}
	
	/*Objecto HibernateTransactionManager*/
	/* Va obtener el objeto sessionFactory se lo pasa transeccion Manager y con esto trabja de modo
	 * transaccional*/
	/*@Autowired Se quiere que este metodo este persistido por Hibernate 
	 * Como se esta reutilizando objecto que ya esta persistido se hace esta etiqueta. */
	@Bean 
	@Autowired
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
		hibernateTransactionManager.setSessionFactory(sessionFactory().getObject());
		return hibernateTransactionManager;
	}
	
}
