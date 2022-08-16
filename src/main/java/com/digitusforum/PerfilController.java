package com.digitusforum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitusforum.perfil.PerfilEntity;
import com.digitusforum.perfil.PerfilService;
import com.digitusforum.perfil.PerfilVO;

@RestController
public class PerfilController {
	@Autowired
	PerfilService perfilService;

	@RequestMapping(value = "/perfil/v1/create")
	public PerfilVO create(@RequestBody PerfilVO perfilVO) {
		return perfilService.create(perfilVO);
	}

	@RequestMapping(value = "/perfil/v1/retrieve")
	public List<PerfilEntity> retrieve(@RequestBody PerfilVO perfilVO) {
		return perfilService.retrieve(perfilVO);
	}

	@RequestMapping(value = "/perfil/v1/{id}/retrieve")
	public PerfilEntity retrieveById(@PathVariable String id) {
		return perfilService.retrieveById(id);
	}

	@RequestMapping(value = "/perfil/v1/{id}/belongToUser/{userId}")
	public PerfilEntity belongToUser(@PathVariable String id, @PathVariable String userId) {
		return perfilService.retrieveByIdAndUserId(id, userId);
	}

	@RequestMapping(value = "/perfil/v1/retrieve/lastUsed")
	public PerfilVO retrieveLastUsed(@RequestBody PerfilVO perfilVO) {
		return perfilService.retrieveLastUsed(perfilVO);
	}

	@RequestMapping(value = "/perfil/v1/{id}/update")
	public PerfilVO update(@PathVariable String id, @RequestBody PerfilVO perfil) {
		return perfilService.update(perfil, id);
	}

	@RequestMapping(value = "/perfil/v1/{id}/delete")
	public PerfilEntity delete(@PathVariable String id) {
		return perfilService.delete(id);
	}

}
