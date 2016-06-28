package com.github.purnet.gamedataaccesslayer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "asset")
public class GameAsset implements Serializable {
	
	private int assetId;
	private String assetCode;
	private String assetName;
	private String assetUrl;
	private Game game;
	
	public GameAsset() {
		
	};
	public GameAsset(String code, String name, String url) {
		this.assetCode = code;
		this.assetName = name;
		this.assetUrl = url;
	}
	
	@Column(name = "asset_code", nullable = false)
	public String getAssetCode() {
		return assetCode;
	}
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	
	@Column(name = "asset_name", nullable = false)
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	
	@Column(name = "url", nullable = false)
	public String getAssetUrl() {
		return assetUrl;
	}
	public void setAssetUrl(String assetUrl) {
		this.assetUrl = assetUrl;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ASSET_ID", unique = true, nullable = false)
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int id) {
		this.assetId = id;
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
