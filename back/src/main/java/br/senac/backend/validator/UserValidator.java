package br.senac.backend.validator;

import java.util.Arrays;
import java.util.List;

import br.senac.backend.dao.UserDao;
import br.senac.backend.exception.UserException;
import br.senac.backend.model.User;
import br.senac.backend.util.ImageUtil;
import br.senac.backend.util.Util;

public class UserValidator {
	public static UserException validate(User user) {

		if (!Util.isDate(user.getBirthday()))
			return new UserException("A data de aniversário está inválida.");

		if (!Util.isDate(user.getCreatedAt()))
			return new UserException("A data de criação do usuário está inválida.");

		// Email 
		if (!Util.isValidEmailAddress(user.getEmail()))
			return new UserException("O endereço de e-mail está inválido.");

		// MIME TYPE PHOTO
		try {
			if (!user.getPhoto().isEmpty()) {
				List<String> mimes = Arrays.asList(ImageUtil.mimeTypesAllow);
				if (!mimes.contains(ImageUtil.getMimeType(user.getPhoto()))) 
					return new UserException("A foto está com o formato("+ImageUtil.getExtension(ImageUtil.getMimeType(user.getPhoto()))+") invalido inválido.");
			}	
		} catch(Exception e) {}
		
		// NOT NULL
		if (Util.empty(user.getFirstName()))
			return new UserException("O nome é obrigatório.");

		if (Util.empty(user.getEmail()))
			return new UserException("O email é obrigatório.");

		if (Util.empty(user.getNickname()))
			return new UserException("O nickname é obrigatório.");

		if (Util.empty(user.getPassword()))
			return new UserException("A senha é obrigatória.");

		// UNIQUE
			User exists = null;
			exists = UserDao.getInstance().getByNickName(user.getNickname());
			if (exists != null)
				return new UserException("Este nickname já está em uso. Tente outro.");

			exists = UserDao.getInstance().getByEmail(user.getEmail());
			if (exists != null)
				return new UserException("Este email já está em uso. Tente outro.");
		return null;
	}
	
	public static UserException validateUpdate(User user, String nicknameOld) {

		if (!Util.isDate(user.getBirthday()))
			return new UserException("A data de aniversário está inválida.");

		if (!Util.isDate(user.getCreatedAt()))
			return new UserException("A data de criação do usuário está inválida.");

		// Email 
		if (!Util.isValidEmailAddress(user.getEmail()))
			return new UserException("O endereço de e-mail está inválido.");

		// MIME TYPE PHOTO
		try {
			if (!user.getPhoto().isEmpty()) {
				List<String> mimes = Arrays.asList(ImageUtil.mimeTypesAllow);
				if (!mimes.contains(ImageUtil.getMimeType(user.getPhoto()))) 
					return new UserException("A foto está com o formato("+ImageUtil.getExtension(ImageUtil.getMimeType(user.getPhoto()))+") invalido inválido.");
			}	
		} catch(Exception e) {}
		
		// NOT NULL
		if (Util.empty(user.getFirstName()))
			return new UserException("O nome é obrigatório.");

		if (Util.empty(user.getEmail()))
			return new UserException("O email é obrigatório.");

		if (Util.empty(user.getNickname()))
			return new UserException("O nickname é obrigatório.");

		// UNIQUE
		if (!user.getNickname().equals(nicknameOld)) {
			User exists = null;
			exists = UserDao.getInstance().getByNickName(user.getNickname());
			if (exists != null)
				return new UserException("Este nickname já está em uso. Tente outro.");
		}
		return null;
	}
}
