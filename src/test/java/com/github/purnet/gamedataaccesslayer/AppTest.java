package com.github.purnet.gamedataaccesslayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.hibernate.Session;

import com.github.purnet.gamedataaccesslayer.entity.Asset;
import com.github.purnet.gamedataaccesslayer.entity.Game;
import com.github.purnet.gamedataaccesslayer.entity.Player;
 
/**
 * Unit tests for EntityResolvers.
 */
public class AppTest extends TestCase {
 
	private static final int testGameId = 35;
	
	public void testCreateGame() {

		SessionManager sm = SessionManager.getInstance();
		Session s = sm.createSession(ThreadId.get());
		EntityResolvers resolver = new EntityResolvers();
		
		s.beginTransaction();
		Set<Asset> assets = new HashSet<Asset>(0);
		Asset asset = new Asset("d34898ec4cadc2b7b85da262e9320a9646655931","gameboard","https://assets.merknera.com/scrabble/board_standard.json");
	    assets.add(asset);

	    asset = new Asset("caf2417c1d9fc2f2512922ae0514ebbc151fe789","dictionary","https://assets.merknera.com/scrabble/dictionary_sowpods.txt");
	    assets.add(asset);
	    
	    asset = new Asset("76d20712668ae0eb85d825b93acb71875a059a00","letters","https://assets.merknera.com/scrabble/letters_standard.json");
	    assets.add(asset);
	    
	    List<Player> players = new ArrayList<Player>(0);
	    Player player = new Player(1, "Fred");
	    players.add(player);
	    
	    player = new Player(2, "Jane");
	    players.add(player);

	    GameAdapter g = resolver.createGame(testGameId, "PENDING", assets, players);
	    s.getTransaction().commit();
	    
	    s.beginTransaction();
	    String board = "[" +
                "[\"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"\", \"C\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"P\", \"A\", \"S\", \"T\", \"A\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"\", \"L\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"\", \"C\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"\", \"U\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"\", \"L\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"\", \"A\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"\", \"T\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"\", \"E\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
                "[\"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\"]," +
           "]";
	    resolver.createMove(testGameId, board, "[\"G\", \"T\", \"C\", \"D\", \"*\", \"A\", \"A\"]");

	    s.getTransaction().commit();
	    sm.closeSession(ThreadId.get());
	}
	
}