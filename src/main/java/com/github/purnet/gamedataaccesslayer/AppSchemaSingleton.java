package com.github.purnet.gamedataaccesslayer;

import graphql.GraphQL;
import graphql.schema.GraphQLObjectType;
import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.Scalars.GraphQLString;
import static graphql.Scalars.GraphQLInt;


public class AppSchemaSingleton 
{
	private static AppSchemaSingleton instance = null;
	
	protected AppSchemaSingleton() {
    
		// Allergies Object Type
		GraphQLObjectType gameType = newObject()
			.name("Game")
			.description("A Game of Scrabble") 
			.field(newFieldDefinition() 
                .name("game_id") 
                .description("The name of the Allergy the patient has") 
                .type(GraphQLString) 
                .build()) 
        	.field(newFieldDefinition() 
                .name("date") 
                .description("Then date the patient identified the allergy") 
                .type(GraphQLString) 
                .build()) 
            .build();
	}
	
	public static AppSchemaSingleton getInstance(){
		if (instance == null) {
			instance = new AppSchemaSingleton();
		}
		return instance;
	}

}
