package com.block7crudvalidation.domain;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class CodigoGenerador {
    private static int num = 0000;

    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        String prefix = "ID";
        num += 1;
        String generatedId = prefix + num; // Implementa la lógica para obtener el siguiente número
        return generatedId;
    }
}
