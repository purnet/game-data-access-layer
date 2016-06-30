package com.github.purnet.gamedataaccesslayer;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.Root;

import com.github.purnet.gamedataaccesslayer.entity.Game;
import com.github.purnet.gamedataaccesslayer.entity.GameAsset;
import com.github.purnet.gamedataaccesslayer.entity.Player;


public class AppSchemaSingleton {
	
	private static AppSchemaSingleton instance = null;
	private GraphQLSchema schema;
	
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
                .name("name") 
                .description("short name of the asset ie dictionary, gameboard") 
                .type(GraphQLString) 
                .build())
            .field(newFieldDefinition() 
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
	
		// Move Object Type
		GraphQLObjectType moveType = newObject()
			.name("Move")
			.description("a captured game state for a move in scrabble") 
			.field(newFieldDefinition() 
                .name("moveId") 
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
                .name("gameId") 
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
                        	ArrayList<Game> games = resolver.getAllGames();
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
							return resolver.getGame(id);
						} 
                    }) 
                    .build())
                .field(newFieldDefinition() 
                    .name("hello") 
                    .type(GraphQLString) 
                    .dataFetcher(new DataFetcher() { 
						public Object get(DataFetchingEnvironment environment) {
							return "World";
						} 
                    }) 
                    .build())
                .build();
		
		GraphQLObjectType mutationType = newObject() 
	            .name("mutationType")
	            .field(newFieldDefinition() 
	                    .name("createTest") 
	                    .type(GraphQLInt) 
	                    .argument(newArgument() 
	                            .name("id") 
	                            .type(GraphQLInt) 
	                            .build()) 
	                    .dataFetcher(new DataFetcher() { 
	                        public Object get(DataFetchingEnvironment environment) { 
	                            int id = environment.getArgument("id"); 
	                            System.out.println("mike mike");
	                            return id; 
	                        } 
	                    }) 
	                    .build())
//	            .field(newFieldDefinition() 
//	                    .name("createGame") 
//	                    .type(gameType) 
//	                    .argument(newArgument() 
//	                            .name("gameId") 
//	                            .type(GraphQLInt) 
//	                            .build()) 
//	                    .argument(newArgument() 
//	                            .name("status") 
//	                            .type(GraphQLString) 
//	                            .build()) 
//	                    .dataFetcher(new DataFetcher() { 
//	                        public Object get(DataFetchingEnvironment environment) { 
//	                            int gameId = environment.getArgument("gameId"); 
//	                            String status = environment.getArgument("status");
//	                            //Root root = (Root) environment.getSource(); 
//	                            //return root.changeNumber(newNumber); 
//	                            System.out.println("got to data resolver");
//	                            // above is the example
//	                            EntityResolvers resolver = new EntityResolvers();
//	                            Set<GameAsset> assets = new HashSet<GameAsset>(0);
//	                    		GameAsset asset = new GameAsset("d34898ec4cadc2b7b85da262e9320a9646655931","gameboard","https://assets.merknera.com/scrabble/board_standard.json");
//	                    	    assets.add(asset);
//
//	                    	    asset = new GameAsset("caf2417c1d9fc2f2512922ae0514ebbc151fe789","dictionary","https://assets.merknera.com/scrabble/dictionary_sowpods.txt");
//	                    	    assets.add(asset);
//	                    	    
//	                    	    asset = new GameAsset("76d20712668ae0eb85d825b93acb71875a059a00","letters","https://assets.merknera.com/scrabble/letters_standard.json");
//	                    	    assets.add(asset);
//	                    	    
//	                    	    Set<Player> players = new HashSet<Player>(0);
//	                    	    Player player = new Player(1, "Fred");
//	                    	    players.add(player);
//	                    	    
//	                    	    player = new Player(2, "Jane");
//	                    	    players.add(player);
//
//	                    	    return resolver.createGame(gameId, "status", assets, players);
//	                        } 
//	                    }) 
//	                    .build()) 
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
