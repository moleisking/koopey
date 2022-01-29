package com.koopey.api.resolver;

//import graphql.kickstart.tools.GraphQLMutationResolver;
//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.koopey.api.model.entity.Advert;
import com.koopey.api.service.AdvertService;
import com.netflix.graphql.dgs.DgsComponent;

import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class AdvertResolver /*implements GraphQLMutationResolver*/ {

    @Autowired
    private AdvertService advertService;

    public Advert setAdvert(String description, String name, String type) {
        Advert advert = new Advert();
        advert.setDescription(description);
        advert.setName(name);
        advert.setType(type);
        return advertService.save(advert);
    }
}
