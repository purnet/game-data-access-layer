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
	
	@Column(name = "PLAYER_NUMBER", nullable = false)
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
	@Column(name = "PLAYER_ID", unique = true, nullable = false)
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((game == null) ? 0 : game.hashCode());
		result = prime * result + playerNumber;
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
		Player other = (Player) obj;
		if (game == null) {
			if (other.game != null)
				return false;
		} else if (!game.equals(other.game))
			return false;
		if (playerNumber != other.playerNumber)
			return false;
		return true;
	}
	
	
}
