package br.senac.backend.model.pojo;

import br.senac.backend.model.Comment;

public class CommentCreatePojo {
	private String text;

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public static Comment convertToModel(CommentCreatePojo pojo) {
		Comment model = new Comment();
		model.setText(pojo.getText());
		return model;
	}
}
