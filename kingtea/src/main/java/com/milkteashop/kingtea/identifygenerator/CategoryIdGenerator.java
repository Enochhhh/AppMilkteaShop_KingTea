package com.milkteashop.kingtea.identifygenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CategoryIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) 
			throws HibernateException {
		Logger logger = LoggerFactory.getLogger(CategoryIdGenerator.class);
		String prefix = "CATE";
		Connection connection = session.connection();
		
		try {
			Statement statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("select count(category_id) as Id from category");
			
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				String generatedId = prefix + String.valueOf(id);
				return generatedId;
			}
		} catch (SQLException e) {
			logger.info("Generator Id for Category unsuccessfully !");
			logger.error(e.getMessage());
		}
		return null;
	}

}
