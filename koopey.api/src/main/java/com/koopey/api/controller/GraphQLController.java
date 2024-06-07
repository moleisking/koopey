package com.koopey.api.controller;

import javax.servlet.annotation.WebServlet;

import com.koopey.api.repository.LocationRepository;
import com.koopey.api.resolver.LocationResolver;

//import com.coxautodev.graphql.tools.SchemaParser;
//import graphql.schema.GraphQLSchema;
//import graphql.servlet.SimpleGraphQLHttpServlet;

//@WebServlet(urlPatterns = "/graphql")
public class GraphQLController /*extends SimpleGraphQLHttpServlet*/ {

  /*  public GraphQLController() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {

        return SchemaParser.newParser()
                .file("data.graphqls")
                .resolvers(new LocationResolver())
                .build()
                .makeExecutableSchema();
    }*/
}