package br.senac.backend.validator;

import br.senac.backend.exception.CommentException;
import br.senac.backend.model.Comment;
import br.senac.backend.util.Util;

public class CommentValidator {
	
	public static CommentException validate(Comment comment) {
		// TIMESTAMP
		if (!Util.isDate(comment.getCreatedAt()))
			return new CommentException("A data de cria��o do usu�rio est� inv�lida.");

		// NOT NULL
		if (Util.empty(comment.getText()))
			return new CommentException("O texto � obrigat�rio.");
		
		if (comment.getTooeat() == null)
			return new CommentException("Coment�rio sem tooeat  definido, o tooeat � obrigat�rio.");
		
		if (comment.getUser() == null)
			return new CommentException("Coment�rio sem usu�rio definido, o usu�rio � obrigat�rio.");

		return null;
	}
	
}
