package com.koopey.api.service.base;

import com.koopey.api.repository.base.AuditRepository;
import java.io.Serializable;
import java.util.List;

public abstract class AuditService<T, Y extends Serializable> extends BaseService<T, Y> {

    protected abstract AuditRepository<T, Y> getRepository();  

    public List<T> findByName(String name) {
        return this.getRepository().findByName(name);
    }

    public List<T> findByType(String type) {
        return this.getRepository().findByType(type);
    }
}
