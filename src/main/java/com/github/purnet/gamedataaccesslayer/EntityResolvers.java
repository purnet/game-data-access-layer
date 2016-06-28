package com.github.purnet.gamedataaccesslayer;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.github.purnet.gamedataaccesslayer.entity.Game;
import com.github.purnet.gamedataaccesslayer.entity.GameAsset;
import com.github.purnet.gamedataaccesslayer.entity.Player;

public class EntityResolvers {
	private static EntityResolvers instance = null;
	private SessionFactory sessionFactory;
	
	protected EntityResolvers() {
		sessionFactory = new Configuration().configure()
				.buildSessionFactory();
	}
	
	public static EntityResolvers getInstance(){
		if (instance == null) {
			instance = new EntityResolvers();
		}
		return instance;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void createGame(SessionFactory sf, int gameId, String status, 
			Set<GameAsset> assets, Set<Player> players){
		
		Session session = sf.openSession();
		session.beginTransaction();
		
		Game game = new Game(gameId, status);
	    session.save(game);

		Set<GameAsset> gameAssets = game.getAssets();
		for (GameAsset asset : assets) {
			asset.setGame(game);
			gameAssets.add(asset);
		    session.save(asset);
		}
		
		Set<Player> gamePlayers = game.getPlayers();
		for (Player player : players) {
			player.setGame(game);
			gamePlayers.add(player);
		    session.save(player);
		}
	    
		session.getTransaction().commit();
		session.close();
	}
	
	//public void createGameAsset()
}
