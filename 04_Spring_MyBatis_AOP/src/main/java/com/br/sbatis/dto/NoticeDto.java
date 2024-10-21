package com.br.sbatis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
// @AllArgsConstructor 매개변수 생성자는 dto엔 사용할 일이 없다.
@Getter
@Setter
@ToString
public class NoticeDto {

	private int no;
	private String title;
	private String content;
	
}
