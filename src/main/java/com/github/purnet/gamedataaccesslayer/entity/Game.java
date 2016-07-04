package com.github.purnet.gamedataaccesslayer.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
 
@Entity
@Table(name = "game")
public class Game implements Serializable {
	
	private int gameId;
	private int merkneraGameId;
	private String status;
	private List<GameAsset> assets = new ArrayList<GameAsset>(0);
	private List<Player> players = new ArrayList<Player>(0);
	private List<Move> moves = new ArrayList<Move>(0);
	
	public Game(){
		
	};
	public Game(int gId, String status) {
		this.merkneraGameId = gId;
		this.status = status;				
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "GAME_ID", unique = true, nullable = false)
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int id) {
		this.gameId = id;
	}
	
	@Column(name = "MERKNERA_G_ID", nullable = false)
	public int getMerkneraGameId() {
		return merkneraGameId;
	}
	public void setMerkneraGameId(int merkneraGameId) {
		this.merkneraGameId = merkneraGameId;
	}
	
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
	public List<GameAsset> getAssets() {
		return assets;
	}
	public void setAssets(List<GameAsset> assets) {
		this.assets = assets;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
	public List<Move> getMoves() {
		return moves;
	}
	public void setMoves(List<Move> moves) {
		this.moves = moves;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + merkneraGameId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (merkneraGameId != other.merkneraGameId)
			return false;
		return true;
	};
	
	
}
