package com.br.spring.ioc.java;

public class Singer {
	
	private String name;
	private Music music;
	
	public Singer() { }

	public Singer(String name, Music music) {
		super();
		this.name = name;
		this.music = music;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	@Override
	public String toString() {
		return "Singer [name=" + name + ", music=" + music + "]";
	}
	
	

}
