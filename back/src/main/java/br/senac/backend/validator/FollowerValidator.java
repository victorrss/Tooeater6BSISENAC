package br.senac.backend.validator;

import br.senac.backend.exception.FollowerException;
import br.senac.backend.model.Follower;

public class FollowerValidator {
	
	public static FollowerException validate(Follower follower) {
		if (follower.getUserSlave() == null)
			return new FollowerException("Tooeat sem usuário seguidor definido, o usuário é obrigatório.");
		
		if (follower.getUserMaster() == null)
			return new FollowerException("Tooeat sem usuário seguido definido, o usuário é obrigatório.");

		return null;
	}
}
