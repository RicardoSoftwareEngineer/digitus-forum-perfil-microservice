package com.digitusforum.perfil;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PerfilRepository extends CrudRepository<PerfilEntity, String> {
	PerfilEntity findByUserIdAndPerfilIdAndDeletedIsFalse(String userId, String perfilId);

	// Optional<PerfilEntity> findByIdUserAndDeletedIsFalse(String idUser);
	List<PerfilEntity> findByUserIdAndDeletedIsFalse(String idUser);
}
