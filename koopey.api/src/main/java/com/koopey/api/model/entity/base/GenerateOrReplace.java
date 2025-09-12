package com.koopey.api.model.entity.base;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

public class GenerateOrReplace extends UUIDGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Object id = session.getEntityPersister(null, object).getIdentifier(object, session);
        return id != null ? id : super.generate(session, object);
    }
}
