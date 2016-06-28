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
	
	public String getTiles() {
		return tiles;
	}
	public void setTiles(String tiles) {
		this.tiles = tiles;
	}
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
	
}
