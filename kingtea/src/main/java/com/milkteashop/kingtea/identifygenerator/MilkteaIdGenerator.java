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

public class MilkteaIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) 
			throws HibernateException {
		Logger logger = LoggerFactory.getLogger(MilkteaIdGenerator.class);
		String prefix = "MTEA";
		Connection connection = session.connection();
		
		try {
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("select count(milktea_id) as Id from milk_tea");
			
			if(rs.next()) {
				int id = rs.getInt(1) + 1;
				String generatedId = prefix + String.valueOf(id);
				return generatedId;
			}
		} catch(SQLException e) {
			logger.info("Generator Id for Milktea unsuccessfully !");
			logger.error(e.getMessage());
		}
		return null;
	}
	
}
