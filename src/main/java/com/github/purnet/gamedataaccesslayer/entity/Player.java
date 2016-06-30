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
@Table(name = "player")
public class Player implements Serializable {

	private int id;
	private int playerNumber;
	private String playerName;
	private Game game;
	
	public Player(){
		
	};
	public Player(int number, String name) {
		this.playerNumber = number;
		this.playerName = name;
	}
	
	@Column(name = "PLAYER_NUMBER", unique = true, nullable = false)
	public int getPlayerNumber() {
		return playerNumber;
	}
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	@Column(name = "PLAYER_NAME")
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ASSET_ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	};
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GAME_ID", nullable = false)
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	
}
