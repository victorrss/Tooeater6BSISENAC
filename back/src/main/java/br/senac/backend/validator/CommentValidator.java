package br.senac.backend.validator;

import br.senac.backend.exception.CommentException;
import br.senac.backend.model.Comment;
import br.senac.backend.util.Util;

public class CommentValidator {
	
	public static CommentException validate(Comment comment) {
		// TIMESTAMP
		if (!Util.isDate(comment.getCreatedAt()))
			return new CommentException("A data de criação do usuário está inválida.");

		// NOT NULL
		if (Util.empty(comment.getText()))
			return new CommentException("O texto é obrigatório.");
		
		if (comment.getTooeat() == null)
			return new CommentException("Comentário sem tooeat  definido, o tooeat é obrigatório.");
		
		if (comment.getUser() == null)
			return new CommentException("Comentário sem usuário definido, o usuário é obrigatório.");

		return null;
	}
	
}
