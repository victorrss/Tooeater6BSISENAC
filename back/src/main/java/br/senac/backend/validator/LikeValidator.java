package br.senac.backend.validator;

import br.senac.backend.exception.LikeException;
import br.senac.backend.model.Like;

public class LikeValidator {

	public static LikeException validate(Like like) {
/*
		// NOT NULL
		if (like.getTooeat() == null)
			return new LikeException("Like sem tooeat  definido, o tooeat � obrigat�rio.");

		if (like.getUser() == null)
			return new LikeException("Like sem usu�rio definido, o usu�rio � obrigat�rio.");
*/
		return null;
	}

}
