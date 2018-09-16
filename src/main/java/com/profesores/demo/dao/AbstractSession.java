package com.profesores.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/*Es una clase abtracta se encarga de llamar al objecto session que ya persistimos en nuestra clase de configuración
 *Lo llamaremos con la anotación @Autowired.
 *Cunado se ejecute la herencia sea obligatorio sobreescribir los metodos.*/
public abstract class AbstractSession {
	
	/*Objeto para crear la persistencia*/
	/*Como ya esta persistido por spring no es necesario instanciarlo el objecto de la clase*/
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
