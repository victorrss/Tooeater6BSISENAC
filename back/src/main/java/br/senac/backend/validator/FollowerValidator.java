package br.senac.backend.validator;

import br.senac.backend.exception.FollowerException;
import br.senac.backend.model.Follower;

public class FollowerValidator {
	
	public static FollowerException validate(Follower follower) {
		if (follower.getUserSlave() == null)
			return new FollowerException("Tooeat sem usu�rio seguidor definido, o usu�rio � obrigat�rio.");
		
		if (follower.getUserMaster() == null)
			return new FollowerException("Tooeat sem usu�rio seguido definido, o usu�rio � obrigat�rio.");

		return null;
	}
}
