package com.digitusforum.perfil;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.digitusforum.perfil.util.M;
import com.digitusforum.perfil.util.PerfilType;
import com.digitusforum.perfil.util.RequestService;

@Service
public class PerfilService {

	@Autowired
	PerfilRepository perfilRepository;
	RequestService requestService = new RequestService();
	ModelMapper modelMapper = new ModelMapper();

	public PerfilVO create(PerfilVO perfilVO) {
		if (StringUtils.isBlank(perfilVO.getUserId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.PERFIL_MISSING_USER_ID);
		if (StringUtils.isBlank(perfilVO.getName()))
			perfilVO.setName("aluno novo");
		if (StringUtils.isBlank(perfilVO.getType()))
			perfilVO.setType(PerfilType.STUDENT.name());
		requestService.userExists(perfilVO.getUserId());
		PerfilEntity perfil = modelMapper.map(perfilVO, PerfilEntity.class);
		perfil = perfilRepository.save(perfil);
		perfilVO.setId(perfil.getId().toString());
		return perfilVO;
	}

	public List<PerfilEntity> retrieve(PerfilVO perfilVO) {
		requestService.userExists(perfilVO.getUserId());
		return perfilRepository.findByUserIdAndDeletedIsFalse(perfilVO.getUserId());
	}

	public PerfilEntity retrieveById(String id) {
		Optional<PerfilEntity> perfil = perfilRepository.findById(id);
		if (perfil.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.PERFIL_NOT_FOUND);

		// return perfilRepository.findById(id).orElse(throw new
		// ResponseStatusException(HttpStatus.NOT_FOUND, M.PERFIL_NOT_FOUND));
		return perfil.get();
	}

	public PerfilEntity retrieveByIdAndUserId(String id, String userId) {
		PerfilEntity perfil = perfilRepository.findByUserIdAndIdAndDeletedIsFalse(userId, id);
		if (perfil == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.PERFIL_NOT_FOUND);

		// return perfilRepository.findById(id).orElse(throw new
		// ResponseStatusException(HttpStatus.NOT_FOUND, M.PERFIL_NOT_FOUND));
		return perfil;
	}

	public PerfilVO retrieveLastUsed(PerfilVO perfilVO) {
		if (StringUtils.isBlank(perfilVO.getUserId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.PERFIL_MISSING_USER_ID);
		requestService.userExists(perfilVO.getUserId());

		List<PerfilEntity> perfilFromDB = perfilRepository.findByUserIdAndDeletedIsFalse(perfilVO.getUserId());
		if (perfilFromDB.isEmpty()) {
			perfilVO = create(perfilVO);
		} else {
			perfilVO = modelMapper.map(perfilFromDB.get(0), PerfilVO.class);
		}
		return perfilVO;
	}

	public PerfilVO update(PerfilVO perfilVO, String id) {
		if (StringUtils.isBlank(perfilVO.getUserId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.PERFIL_MISSING_USER_ID);
		if (StringUtils.isBlank(perfilVO.getName()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.PERFIL_MISSING_NAME);

		Optional<PerfilEntity> perfilFromDB = perfilRepository.findById(id);
		if (perfilFromDB.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.PERFIL_NOT_FOUND);

		perfilVO.setId(id);
		PerfilEntity perfil = perfilRepository.save(modelMapper.map(perfilVO, PerfilEntity.class));
		return perfilVO;
	}

	public PerfilEntity delete(String id) {
		Optional<PerfilEntity> perfilFromDB = perfilRepository.findById(id);
		if (perfilFromDB.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.PERFIL_NOT_FOUND);

		perfilFromDB.get().setDeleted(true);
		PerfilEntity perfil = perfilRepository.save(perfilFromDB.get());
		return perfil;
	}

}
