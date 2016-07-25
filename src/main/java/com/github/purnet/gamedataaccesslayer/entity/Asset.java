package com.github.purnet.gamedataaccesslayer.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "asset")
public class Asset implements Serializable {
	
//	private int assetId;
	@Id
	@Column(name = "ASSET_CODE", nullable = false)
	private String assetCode;
	
	@Column(name = "ASSET_NAME", nullable = false)
	private String assetName;
	
	@Column(name = "URL", nullable = false)
	private String assetUrl;
	
	@Column(name="ASSET_CONTENT", length=10485760)
	private String assetContent; 
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="assets")
	private Set<Game> games = new HashSet<Game>();
	//private Game game;
	
	public Asset() {
		
	};
	public Asset(String code, String name, String url) {
		this.assetCode = code;
		this.assetName = name;
		this.assetUrl = url;
	}
	
	public String getAssetCode() {
		return assetCode;
	}
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	
	public String getAssetUrl() {
		return assetUrl;
	}
	public void setAssetUrl(String assetUrl) {
		this.assetUrl = assetUrl;
	}
	
//	@Id
//	@GeneratedValue(strategy = IDENTITY)
//	@Column(name = "ASSET_ID", unique = true, nullable = false)
//	public int getAssetId() {
//		return assetId;
//	}
//	public void setAssetId(int id) {
//		this.assetId = id;
//	};
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "GAME_ID", nullable = false)
	
	
	public Set<Game> getGame() {
		return games;
	}
	public void setGame(Set<Game> games) {
		this.games = games;
	}
	
	public String getAssetContent() {
		return assetContent;
	}
	public void setAssetContent(String assetContent) {
		this.assetContent = assetContent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assetCode == null) ? 0 : assetCode.hashCode());
		result = prime * result
				+ ((assetContent == null) ? 0 : assetContent.hashCode());
		result = prime * result
				+ ((assetName == null) ? 0 : assetName.hashCode());
		result = prime * result
				+ ((assetUrl == null) ? 0 : assetUrl.hashCode());
		result = prime * result + ((games == null) ? 0 : games.hashCode());
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
		Asset other = (Asset) obj;
		if (assetCode == null) {
			if (other.assetCode != null)
				return false;
		} else if (!assetCode.equals(other.assetCode))
			return false;
		if (assetContent == null) {
			if (other.assetContent != null)
				return false;
		} else if (!assetContent.equals(other.assetContent))
			return false;
		if (assetName == null) {
			if (other.assetName != null)
				return false;
		} else if (!assetName.equals(other.assetName))
			return false;
		if (assetUrl == null) {
			if (other.assetUrl != null)
				return false;
		} else if (!assetUrl.equals(other.assetUrl))
			return false;
		if (games == null) {
			if (other.games != null)
				return false;
		} else if (!games.equals(other.games))
			return false;
		return true;
	}

}
