package com.github.purnet.gamedataaccesslayer;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInputObjectField.newInputObjectField;
import static graphql.schema.GraphQLInputObjectType.newInputObject;
import static graphql.schema.GraphQLObjectType.newObject;
import graphql.GraphQLException;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.purnet.gamedataaccesslayer.entity.Asset;
import com.github.purnet.gamedataaccesslayer.entity.Game;
import com.github.purnet.gamedataaccesslayer.entity.Player;
import com.github.purnet.webhelperlib.HTTPRequestHelper;
import com.github.purnet.webhelperlib.Response;


public class AppSchemaSingleton {
	
	private static AppSchemaSingleton instance = null;
	private GraphQLSchema schema;
	private static final Logger log = LoggerFactory.getLogger(AppSchemaSingleton.class);
	
	protected AppSchemaSingleton() {
    
		// Asset Object Type
		GraphQLObjectType assetType = newObject()
			.name("Asset")
			.description("An asset used to play scrabble eg board definition, dictionary etc") 
			.field(newFieldDefinition() 
                .name("assetCode") 
                .description("The hash code for the asset used") 
                .type(GraphQLString) 
                .build()) 
        	.field(newFieldDefinition() 
                .name("assetName") 
                .description("short name of the asset ie dictionary, gameboard") 
                .type(GraphQLString) 
                .build())
            .field(newFieldDefinition() 
                .name("assetUrl") 
                .description("download url location for the asset") 
                .type(GraphQLString) 
                .build())
            .field(newFieldDefinition() 
                .name("assetContent") 
                .description("download asset content") 
                .type(GraphQLString) 
                .build())
            .build();
		
		GraphQLInputObjectType assetInputType = newInputObject()
				.name("AssetInput")
				.description("An asset used to play scrabble eg board definition, dictionary etc") 
				.field(newInputObjectField() 
	                .name("code") 
	                .description("The hash code for the asset used") 
	                .type(GraphQLString) 
	                .build()) 
	        	.field(newInputObjectField() 
	                .name("name") 
	                .description("short name of the asset ie dictionary, gameboard") 
	                .type(GraphQLString) 
	                .build()) 
	            .field(newInputObjectField() 
	                .name("url") 
	                .description("download url location for the asset") 
	                .type(GraphQLString) 
	                .build())
	            .build();
				
		// Player Object Type
		GraphQLObjectType playerType = newObject()
			.name("Player")
			.description("a player of a game") 
			.field(newFieldDefinition() 
                .name("playerNumber") 
                .description("The players number") 
                .type(GraphQLString) 
                .build()) 
        	.field(newFieldDefinition() 
                .name("playerName") 
                .description("The players name") 
                .type(GraphQLString) 
                .build()) 
            .build();
		
		GraphQLInputObjectType playerInputType = newInputObject()
				.name("PlayerInput")
				.description("a player of a game") 
				.field(newInputObjectField() 
	                .name("playerNum") 
	                .description("The players number") 
	                .type(GraphQLString) 
	                .build()) 
	        	.field(newInputObjectField() 
	                .name("playerName") 
	                .description("The players name") 
	                .type(GraphQLString) 
	                .build()) 
	            .build();
	
		// Move Object Type
		GraphQLObjectType moveType = newObject()
			.name("Move")
			.description("a captured game state for a move in scrabble") 
			.field(newFieldDefinition() 
                .name("id") 
                .description("id of the move made") 
                .type(GraphQLString) 
                .build()) 
        	.field(newFieldDefinition() 
                .name("gameState") 
                .description("The game state at the end of the move") 
                .type(GraphQLString) 
                .build()) 
             .field(newFieldDefinition() 
                .name("tiles") 
                .description("The game state at the end of the move") 
                .type(GraphQLString) 
                .build()) 
            .build();
				
		// Game Object Type
		GraphQLObjectType gameType = newObject()
			.name("Game")
			.description("A Game of Scrabble") 
			.field(newFieldDefinition() 
                .name("merkneraGameId") 
                .description("The Merknera provided game id") 
                .type(GraphQLString) 
                .build()) 
        	.field(newFieldDefinition() 
                .name("status") 
                .description("The game status, ready, pending or complete") 
                .type(GraphQLString) 
                .build()) 
            .field(newFieldDefinition() 
                .name("assets") 
                .description("The assets defined for a game")
                .type(new GraphQLList(assetType)) 
                .build())
            .field(newFieldDefinition() 
                .name("players") 
                .description("The players registered for a game")
                .type(new GraphQLList(playerType)) 
                .build())
            .field(newFieldDefinition() 
                .name("moves") 
                .description("all of the moves made for a game")
                .type(new GraphQLList(moveType)) 
                .build())
            .build();
		
		GraphQLObjectType queryType = newObject()
                .name("RootQuery")
                .field(newFieldDefinition() 
                    .name("games") 
                    .type(new GraphQLList(gameType)) 
                    .dataFetcher(new DataFetcher() { 
                        public Object get(DataFetchingEnvironment environment) { 
                        	EntityResolvers resolver = new EntityResolvers();
                        	List<GameAdapter> games = resolver.getAllGames();
                            return games; 
                        } 
                    }) 
                    .build())
                .field(newFieldDefinition() 
                    .name("game") 
                    .type(gameType) 
                    .argument(newArgument() 
                            .name("id") 
                            .type(new GraphQLNonNull(GraphQLInt)) 
                            .build()) 
                    .dataFetcher(new DataFetcher() { 
						public Object get(DataFetchingEnvironment environment) {
							int id = environment.getArgument("id");
							EntityResolvers resolver = new EntityResolvers();
							GameAdapter g = resolver.getGame(id);
							if (g == null) {
								throw new GraphQLException("No Game Found");
							}
							return resolver.getGame(id);
						} 
                    }) 
                    .build())
                .field(newFieldDefinition() 
                    .name("asset") 
                    .type(assetType) 
                    .argument(newArgument() 
                            .name("assetCode") 
                            .type(new GraphQLNonNull(GraphQLString)) 
                            .build()) 
                    .dataFetcher(new DataFetcher() { 
                        public Object get(DataFetchingEnvironment environment) { 
                        	String assetCode = environment.getArgument("assetCode");
                        	EntityResolvers resolver = new EntityResolvers();
                        	Asset asset = resolver.getAsset(assetCode);
                            return asset; 
                        } 
                    }) 
                    .build())
                .build();
		
		GraphQLObjectType mutationType = newObject() 
	            .name("mutationType")
	            .field(newFieldDefinition() 
	                    .name("createGame") 
	                    .type(gameType) 
	                    .argument(newArgument() 
	                            .name("merkneraGameId") 
	                            .type(new GraphQLNonNull(GraphQLInt)) 
	                            .build()) 
	                    .argument(newArgument() 
	                            .name("status") 
	                            .type(GraphQLString) 
	                            .build()) 
	                    .argument(newArgument() 
	                            .name("playerInput") 
	                            .type(new GraphQLList(playerInputType)) 
	                            .build()) 
	                    .argument(newArgument() 
	                            .name("assetInput") 
	                            .type(new GraphQLList(assetInputType)) 
	                            .build()) 
	                    .dataFetcher(new DataFetcher() { 
	                        public Object get(DataFetchingEnvironment environment) { 
	                            int gameId = environment.getArgument("merkneraGameId"); 
	                            String status = environment.getArgument("status");
	                            ArrayList<LinkedHashMap<String, String>> playerListArgs = environment.getArgument("playerInput");
	                            ArrayList<LinkedHashMap<String, String>> assetListArgs = environment.getArgument("assetInput");
	                            List<Player> players = new ArrayList<Player>(0);
	                            Set<Asset> assets = new HashSet<Asset>();
	   
	                            for (int i = 0; i < playerListArgs.size(); i++) {
	                            	LinkedHashMap<String, String> playerArgs = playerListArgs.get(i);
	                            	String pNum = null;
	                            	String pName = null;
	                            	for (String key : playerArgs.keySet()) {
	                            		switch (key) {
		                        	        case "playerName":  
		                        	        	 pName = playerArgs.get(key);
		                        	             break;
		                        	        case "playerNum":  
		                        	        	 pNum = playerArgs.get(key);
		                        	             break;
		                        	        default: 
		                                         break;
		                        		}
		                            }
	                            	Player player = new Player(Integer.parseInt(pNum), pName);
	                            	players.add(player);
	                            }
	                            
	                            for (int i = 0; i < assetListArgs.size(); i++) {
	                            	LinkedHashMap<String, String> assetArgs = assetListArgs.get(i);
	                            	String url = null;
	                            	String code = null;
	                            	String name = null;
	                            	for (String key : assetArgs.keySet()) {
	                            		switch (key) {
		                        	        case "code":  
		                        	        	 code = assetArgs.get(key);
		                        	             break;
		                        	        case "name":  
		                        	        	 name = assetArgs.get(key);
		                        	             break;
		                        	        case "url":  
		                        	        	 url = assetArgs.get(key);
		                        	             break;
		                        	        default: 
		                                         break;
		                        		}
		                            }
	                            	Asset asset = new Asset(code, name, url);
	                            	assets.add(asset);
	                            }
	                            
	                            EntityResolvers resolver = new EntityResolvers();

	                    	    return resolver.createGame(gameId, status, assets, players);
	                        } 
	                    }) 
	                    .build()) 
	            .field(newFieldDefinition() 
	                    .name("createMove") 
	                    .type(moveType) 
	                    .argument(newArgument() 
	                            .name("merkneraGameId") 
	                            .type(new GraphQLNonNull(GraphQLInt)) 
	                            .build()) 
	                    .argument(newArgument() 
	                            .name("gameState") 
	                            .type(new GraphQLNonNull(GraphQLString)) 
	                            .build()) 
	                    .argument(newArgument() 
	                            .name("tiles") 
	                            .type(new GraphQLNonNull(GraphQLString)) 
	                            .build()) 
	                    .dataFetcher(new DataFetcher() { 
	                        public Object get(DataFetchingEnvironment environment) { 
	                            int gameId = environment.getArgument("merkneraGameId"); 
	                            String gameState = environment.getArgument("gameState");
	                            String tiles = environment.getArgument("tiles");
	                            EntityResolvers resolver = new EntityResolvers();
	                    	    return resolver.createMove(gameId, gameState, tiles);
	                        } 
	                    }) 
	                    .build())
	            .build(); 
		
		schema = GraphQLSchema.newSchema()
                .query(queryType)
                .mutation(mutationType)
                .build();
	}
	
	public static AppSchemaSingleton getInstance(){
		if (instance == null) {
			instance = new AppSchemaSingleton();
		}
		return instance;
	}
	
	public GraphQLSchema getSchema() {
		return schema;
	}

}
