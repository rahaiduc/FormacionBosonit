package com.block7crudvalidation.domain.GeneradoresId;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GeneradorIdPersona implements IdentifierGenerator {

    private int currentId = 0;
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // Generar un ID único basado en UUID
        currentId++;
        String customId =Integer.toString(currentId);
        return customId;
    }
}
