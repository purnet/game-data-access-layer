package com.github.purnet.gamedataaccesslayer.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
 
@Entity
@Table(name = "game")
public class Game implements Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "GAME_ID", unique = true, nullable = false)
	private int gameId;
	
	@Column(name = "MERKNERA_G_ID", unique = true, nullable = false)
	private int merkneraGameId;
	
	@Column(name = "STATUS")
	private String status;
	//private List<Asset> assets = new ArrayList<Asset>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
	private List<Player> players = new ArrayList<Player>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
	private List<Move> moves = new ArrayList<Move>(0);
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="GAME_ASSET", 
				joinColumns={@JoinColumn(name="GAME_ID")}, 
				inverseJoinColumns={@JoinColumn(name="ASSET_CODE")})
	private Set<Asset> assets = new HashSet<Asset>();
	
	public Game(){
		
	};
	public Game(int gId, String status) {
		this.merkneraGameId = gId;
		this.status = status;				
	}
	
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int id) {
		this.gameId = id;
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
	
	

	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
	public Set<Asset> getAssets() {
		return assets;
	}
	public void setAssets(Set<Asset> assets) {
		this.assets = assets;
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
