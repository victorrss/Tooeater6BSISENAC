package br.senac.backend.validator;

import br.senac.backend.exception.TooeatException;
import br.senac.backend.model.Tooeat;
import br.senac.backend.util.Util;

public class TooeatValidator {
	public static TooeatException validate(Tooeat tooeat) {
		// TIMESTAMP
		if (!Util.isDate(tooeat.getCreatedAt()))
			return new TooeatException("A data de criação do tooeat está inválida.");

		// NOT NULL
		if (Util.empty(tooeat.getText()) && Util.empty(tooeat.getMedia()))
			return new TooeatException("É obrigatório o envio de pelo menos o texto ou a mídia.");

		if (tooeat.getUser() == null)
			return new TooeatException("Tooeat sem usuário definido, o usuário é obrigatório.");

		return null;
	}
}