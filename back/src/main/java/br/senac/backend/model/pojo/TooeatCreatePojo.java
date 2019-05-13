package br.senac.backend.model.pojo;

import br.senac.backend.model.Tooeat;

public class TooeatCreatePojo {
	private String text;
	private String media;

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}

	public static Tooeat convertToModel(TooeatCreatePojo pojo) {
		Tooeat model = new Tooeat();
		model.setText(pojo.getText());
		model.setMedia(pojo.getMedia());
		return model;
	}

}
