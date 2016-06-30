package com.github.purnet.gamedataaccesslayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import com.github.purnet.gamedataaccesslayer.entity.Game;
import com.github.purnet.gamedataaccesslayer.entity.GameAsset;
import com.github.purnet.gamedataaccesslayer.entity.Player;

public class EntityResolvers {
		
	public Game createGame(int gameId, String status, 
			List<GameAsset> assets, List<Player> players){
		
        SessionManager sm = SessionManager.getInstance();
        Session session = sm.getSession(ThreadId.get());
        
		Game game = new Game(gameId, status);
	    session.save(game);

		List<GameAsset> gameAssets = game.getAssets();
		for (GameAsset asset : assets) {
			asset.setGame(game);
			gameAssets.add(asset);
		    session.save(asset);
		}
		
		List<Player> gamePlayers = game.getPlayers();
		for (Player player : players) {
			player.setGame(game);
			gamePlayers.add(player);
		    session.save(player);
		}
		
		return game;
	    
	}
	
	//public void createGameAsset()
	
	public ArrayList<Game> getAllGames() {
		ArrayList<Game> games = null;
		return games;
	}
	
	public Game getGame(int id) {
		Game game = null;
		return game;
	}
	
}
