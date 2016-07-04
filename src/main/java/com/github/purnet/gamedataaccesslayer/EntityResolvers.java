package com.github.purnet.gamedataaccesslayer;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Query;

import com.github.purnet.gamedataaccesslayer.entity.Game;
import com.github.purnet.gamedataaccesslayer.entity.GameAsset;
import com.github.purnet.gamedataaccesslayer.entity.Move;
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
	
	public Move createMove(int gameId, String gameState, String tiles){
		SessionManager sm = SessionManager.getInstance();
        Session session = sm.getSession(ThreadId.get());
        String hql = "FROM com.github.purnet.gamedataaccesslayer.entity.Game g WHERE g.merkneraGameId = :game_id";
        Query query = session.createQuery(hql);
        query.setParameter("game_id", gameId);
        List result = query.list();
        Game game = (Game) result.get(0);
		Move move = new Move(tiles, gameState);
		move.setGame(game);
	    session.save(move);
		return move;
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
