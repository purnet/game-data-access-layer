package com.github.purnet.gamedataaccesslayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.purnet.gamedataaccesslayer.entity.Asset;
import com.github.purnet.gamedataaccesslayer.entity.Game;
import com.github.purnet.gamedataaccesslayer.entity.Move;
import com.github.purnet.gamedataaccesslayer.entity.Player;
import com.github.purnet.webhelperlib.HTTPRequestHelper;
import com.github.purnet.webhelperlib.Response;

public class EntityResolvers {
	private static final Logger log = LoggerFactory.getLogger(EntityResolvers.class);
    private static final String getGameByIdhql = "FROM com.github.purnet.gamedataaccesslayer.entity.Game g WHERE g.merkneraGameId = :game_id";
    private static final String getAssetByIdhql = "FROM com.github.purnet.gamedataaccesslayer.entity.Asset a WHERE a.assetCode = :asset_code";
	
    public GameAdapter createGame(int gameId, String status, 
			Set<Asset> assets, List<Player> players) throws HibernateException {
		
        SessionManager sm = SessionManager.getInstance();
        Session session = sm.getSession(ThreadId.get());
        
		Game game = new Game(gameId, status);
		session.save(game);
		
		for (Asset a : assets) {
			Query query = session.createQuery(getAssetByIdhql);
		    query.setParameter("asset_code", a.getAssetCode());
		    List<Asset> result = (ArrayList<Asset>) query.list();
		    Asset asset = null;
		    if (!result.isEmpty()){
		    	asset = (Asset) result.get(0);
		    } else {
		    	Response r = HTTPRequestHelper.makeHTTPRequest(a.getAssetUrl(), null, "GET");
				BufferedReader bufReader = new BufferedReader(new StringReader(r.getBody()));
				String line=null;
				StringBuilder assetBody = new StringBuilder();
				try {
					while( (line=bufReader.readLine()) != null )
					{
						assetBody.append(line + System.lineSeparator());
					}
					asset = new Asset(a.getAssetCode(), a.getAssetName(), a.getAssetUrl());
					asset.setAssetContent(assetBody.toString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
			
			
			game.getAssets().add(asset);
		    session.save(asset);
		}
		
		List<Player> gamePlayers = game.getPlayers();
		for (Player player : players) {
			player.setGame(game);
			gamePlayers.add(player);
		    session.save(player);
		}
		GameAdapter ga = new GameAdapter(game);
		return ga;
	    
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
	
	public List<GameAdapter> getAllGames() {
		SessionManager sm = SessionManager.getInstance();
		Session session = sm.getSession(ThreadId.get());
		Criteria cr = session.createCriteria(Game.class);
		List<Game> games = (ArrayList<Game>) cr.list();
		List<GameAdapter> adaptedGames = new ArrayList<GameAdapter>();
		for (Game g : games){
			GameAdapter ga = new GameAdapter(g);
			adaptedGames.add(ga);
		}
		return adaptedGames;
	}
	
	public GameAdapter getGame(int id) {
		SessionManager sm = SessionManager.getInstance();
        Session session = sm.getSession(ThreadId.get());
		Query query = session.createQuery(getGameByIdhql);
        query.setParameter("game_id", id);
        List<Game> result = (ArrayList<Game>) query.list();
        Game game = null;
        if (!result.isEmpty()){
        	game = (Game) result.get(0);
        }
        GameAdapter ga = new GameAdapter(game);
		return ga;
	}
	
	public Asset getAsset(String assetCode) {
		SessionManager sm = SessionManager.getInstance();
	    Session session = sm.getSession(ThreadId.get());
		Query query = session.createQuery(getAssetByIdhql);
	    query.setParameter("asset_code", assetCode);
	    List<Asset> result = (ArrayList<Asset>) query.list();
	    Asset asset = null;
	    if (!result.isEmpty()){
	    	asset = (Asset) result.get(0);
	    }
		return asset;
	}
	
}
