package br.senac.backend.validator;

import br.senac.backend.dao.UserDao;
import br.senac.backend.exception.UserException;
import br.senac.backend.model.User;
import br.senac.backend.util.Util;

public class UserValidator {
	public static UserException validate(User user) {

		if (!Util.isDate(user.getBirthday()))
			return new UserException("A data de anivers�rio est� inv�lida.");

		if (!Util.isDate(user.getCreatedAt()))
			return new UserException("A data de cria��o do usu�rio est� inv�lida.");

		// Email 
		if (!Util.isValidEmailAddress(user.getEmail()))
			return new UserException("O endere�o de e-mail est� inv�lido.");


		// NOT NULL
		if (Util.empty(user.getFirstName()))
			return new UserException("O nome � obrigat�rio.");

		if (Util.empty(user.getEmail()))
			return new UserException("O email � obrigat�rio.");

		if (Util.empty(user.getNickname()))
			return new UserException("O nickname � obrigat�rio.");

		if (Util.empty(user.getPassword()))
			return new UserException("A senha � obrigat�ria.");

		// UNIQUE
			User exists = null;
			exists = UserDao.getInstance().getByNickName(user.getNickname());
			if (exists != null)
				return new UserException("Este nickname j� est� em uso. Tente outro.");

			exists = UserDao.getInstance().getByEmail(user.getEmail());
			if (exists != null)
				return new UserException("Este email j� est� em uso. Tente outro.");
		return null;
	}
	
	public static UserException validateUpdate(User user, String nicknameOld) {

		if (!Util.isDate(user.getBirthday()))
			return new UserException("A data de anivers�rio est� inv�lida.");

		if (!Util.isDate(user.getCreatedAt()))
			return new UserException("A data de cria��o do usu�rio est� inv�lida.");

		// Email 
		if (!Util.isValidEmailAddress(user.getEmail()))
			return new UserException("O endere�o de e-mail est� inv�lido.");


		// NOT NULL
		if (Util.empty(user.getFirstName()))
			return new UserException("O nome � obrigat�rio.");

		if (Util.empty(user.getEmail()))
			return new UserException("O email � obrigat�rio.");

		if (Util.empty(user.getNickname()))
			return new UserException("O nickname � obrigat�rio.");

		// UNIQUE
		if (!user.getNickname().equals(nicknameOld)) {
			User exists = null;
			exists = UserDao.getInstance().getByNickName(user.getNickname());
			if (exists != null)
				return new UserException("Este nickname j� est� em uso. Tente outro.");
		}
		return null;
	}
}
