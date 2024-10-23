package com.br.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AttachDto {

	private int fileNo;
	private String filePath;
	private String originalName;
	private String filesystemName;
	private int refBoardNo;
	
}
