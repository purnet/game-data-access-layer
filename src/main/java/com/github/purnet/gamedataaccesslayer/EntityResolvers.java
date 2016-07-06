package com.github.purnet.gamedataaccesslayer;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.github.purnet.gamedataaccesslayer.entity.Game;
import com.github.purnet.gamedataaccesslayer.entity.GameAsset;
import com.github.purnet.gamedataaccesslayer.entity.Move;
import com.github.purnet.gamedataaccesslayer.entity.Player;

public class EntityResolvers {
	
    private static final String getGameByIdhql = "FROM com.github.purnet.gamedataaccesslayer.entity.Game g WHERE g.merkneraGameId = :game_id";
    
	public Game createGame(int gameId, String status, 
			List<GameAsset> assets, List<Player> players) throws HibernateException {
		
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
	
	public Move createMove(int gameId, String gameState, String tiles) throws HibernateException{
		SessionManager sm = SessionManager.getInstance();
        Session session = sm.getSession(ThreadId.get());
        Query query = session.createQuery(getGameByIdhql);
        query.setParameter("game_id", gameId);
        List<Game> result = (ArrayList<Game>) query.list(); 
        Game game = (Game) result.get(0);
		Move move = new Move(tiles, gameState);
		move.setGame(game);
	    session.save(move);
		return move;
	}
	
	//public void createGameAsset()
	
	public List<Game> getAllGames() {
		SessionManager sm = SessionManager.getInstance();
		Session session = sm.getSession(ThreadId.get());
		Criteria cr = session.createCriteria(Game.class);
		List<Game> games = (ArrayList<Game>) cr.list();
		return games;
	}
	
	public Game getGame(int id) {
		SessionManager sm = SessionManager.getInstance();
        Session session = sm.getSession(ThreadId.get());
		Query query = session.createQuery(getGameByIdhql);
        query.setParameter("game_id", id);
        List<Game> result = (ArrayList<Game>) query.list();
        Game game = null;
        if (!result.isEmpty()){
        	game = (Game) result.get(0);
        }
		return game;
	}
	
}
