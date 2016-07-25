package com.github.purnet.gamedataaccesslayer.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "move")
public class Move implements Serializable {

	private int id;
	private String tiles;
	private String gameState;
	
	private Game game;
	
	public Move() {
		
	};
	public Move (String tile, String state){
		this.tiles = tile;
		this.gameState = state;
	};
	
	@Column(name = "TILES")
	public String getTiles() {
		return tiles;
	}
	public void setTiles(String tiles) {
		this.tiles = tiles;
	}
	
	@Column(name = "GAME_STATE", length = 4000)
	public String getGameState() {
		return gameState;
	}
	public void setGameState(String gameState) {
		this.gameState = gameState;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "MOVE_ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id", nullable = false)
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((game == null) ? 0 : game.hashCode());
		result = prime * result
				+ ((gameState == null) ? 0 : gameState.hashCode());
		result = prime * result + ((tiles == null) ? 0 : tiles.hashCode());
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
		Move other = (Move) obj;
		if (game == null) {
			if (other.game != null)
				return false;
		} else if (!game.equals(other.game))
			return false;
		if (gameState == null) {
			if (other.gameState != null)
				return false;
		} else if (!gameState.equals(other.gameState))
			return false;
		if (tiles == null) {
			if (other.tiles != null)
				return false;
		} else if (!tiles.equals(other.tiles))
			return false;
		return true;
	}
	
}
