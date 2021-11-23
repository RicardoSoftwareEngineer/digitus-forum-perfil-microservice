package com.digitusforum.perfil;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Perfil")
public class PerfilEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	private String userId;
	private LocalDateTime lastTimeUsed;
	private String name;
	private String type;
	private boolean deleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocalDateTime getLastTimeUsed() {
		return lastTimeUsed;
	}

	public void setLastTimeUsed(LocalDateTime lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}





	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
