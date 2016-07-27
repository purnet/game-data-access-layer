package com.github.purnet.gamedataaccesslayer.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("Scrabble")
@Table(name = "move")
public class ScrabbleMove extends Move{
	private String tiles;
	
	public ScrabbleMove() {
		
	};
	public ScrabbleMove (String tile, String state){
		this.tiles = tile;
		this.setGameState(state);
	};
	@Column(name = "TILES")
	public String getTiles() {
		return tiles;
	}
	public void setTiles(String tiles) {
		this.tiles = tiles;
	}
	
}
