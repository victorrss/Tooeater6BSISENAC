package br.senac.backend.validator;

import br.senac.backend.exception.TooeatException;
import br.senac.backend.model.Tooeat;
import br.senac.backend.util.Util;

public class TooeatValidator {
	public static TooeatException validate(Tooeat tooeat) {
		// TIMESTAMP
		if (!Util.isDate(tooeat.getCreatedAt()))
			return new TooeatException("A data de cria��o do tooeat est� inv�lida.");

		// NOT NULL
		if (Util.empty(tooeat.getText()) && Util.empty(tooeat.getMedia()))
			return new TooeatException("� obrigat�rio o envio de pelo menos o texto ou a m�dia.");

		if (tooeat.getUser() == null)
			return new TooeatException("Tooeat sem usu�rio definido, o usu�rio � obrigat�rio.");

		return null;
	}
}