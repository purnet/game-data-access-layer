package com.github.purnet.gamedataaccesslayer;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.github.purnet.gamedataaccesslayer.entity.Game;
import com.github.purnet.gamedataaccesslayer.entity.GameAsset;
import com.github.purnet.gamedataaccesslayer.entity.Player;
 
/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
 
	public void testApp() {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
 
		AppUser user = new AppUser("firstuser");
		session.save(user);
		
		Set<GameAsset> assets = new HashSet<GameAsset>(0);
		GameAsset asset = new GameAsset("d34898ec4cadc2b7b85da262e9320a9646655931","gameboard","https://assets.merknera.com/scrabble/board_standard.json");
	    assets.add(asset);
	    asset = new GameAsset("caf2417c1d9fc2f2512922ae0514ebbc151fe789","dictionary","https://assets.merknera.com/scrabble/dictionary_sowpods.txt");
	    assets.add(asset);
	    
	    Set<Player> players = new HashSet<Player>(0);
	    Player player = new Player(1, "Fred");
	    players.add(player);
	    player = new Player(2, "Jane");
	    players.add(player);
	    
	    Game game = new Game(35, "EATING", players, assets);
	    session.save(game);
	    
	    
		session.getTransaction().commit();
		session.close();
	}
}