package br.senac.backend.validator;

import br.senac.backend.exception.LikeException;
import br.senac.backend.model.Like;

public class LikeValidator {

	public static LikeException validate(Like like) {
/*
		// NOT NULL
		if (like.getTooeat() == null)
			return new LikeException("Like sem tooeat  definido, o tooeat é obrigatório.");

		if (like.getUser() == null)
			return new LikeException("Like sem usuário definido, o usuário é obrigatório.");
*/
		return null;
	}

}
