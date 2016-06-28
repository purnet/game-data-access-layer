package com.github.purnet.gamedataaccesslayer.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
	private int merkeraGameId;
	private String status;
	private Set<GameAsset> assets = new HashSet<GameAsset>(0);
	private Set<Player> players = new HashSet<Player>(0);
	private Set<Move> moves = new HashSet<Move>(0);
	
	public Game(){
		
	};
	public Game(int gId, String statusa) {
		this.merkeraGameId = gId;
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
	public int getmerkeraGameId() {
		return merkeraGameId;
	}
	public void setmerkeraGameId(int merkeraGameId) {
		this.merkeraGameId = merkeraGameId;
	}
	
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
	public Set<GameAsset> getAssets() {
		return assets;
	}
	public void setAssets(Set<GameAsset> assets) {
		this.assets = assets;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
	public Set<Player> getPlayers() {
		return players;
	}
	public void setPlayers(Set<Player> players) {
		this.players = players;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
	public Set<Move> getMoves() {
		return moves;
	}
	public void setMoves(Set<Move> moves) {
		this.moves = moves;
	};
	
	
}
