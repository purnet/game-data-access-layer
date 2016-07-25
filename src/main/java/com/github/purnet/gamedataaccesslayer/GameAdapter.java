package com.github.purnet.gamedataaccesslayer;

import java.util.ArrayList;
import java.util.List;

import com.github.purnet.gamedataaccesslayer.entity.Asset;
import com.github.purnet.gamedataaccesslayer.entity.Game;
import com.github.purnet.gamedataaccesslayer.entity.Move;
import com.github.purnet.gamedataaccesslayer.entity.Player;

public class GameAdapter {
    private int merkneraGameId;
	private String status;
	private List<Player> players;
	private List<Move> moves;
	private List<Asset> assets = new ArrayList<Asset>();
	
	public GameAdapter(Game g) {
		this.merkneraGameId = g.getMerkneraGameId();
		this.status = g.getStatus();
		this.players = g.getPlayers();
		this.moves = g.getMoves();
		this.assets.addAll(g.getAssets());
	}

	public int getMerkneraGameId() {
		return merkneraGameId;
	}

	public void setMerkneraGameId(int merkneraGameId) {
		this.merkneraGameId = merkneraGameId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Move> getMoves() {
		return moves;
	}

	public void setMoves(List<Move> moves) {
		this.moves = moves;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}
	
	
}
