package com.smhrd.board.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user") // 이미 만들어놓은 DB 사용 / 매개변수에 값을 넣어서 기본테이블 이름을 변경할수도 있음
@Data //게터와 세터가 모두 들어있는 어노테이션 (@Data = @Getter+@Setter)
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // PK값 : Auto Increase
	private Long idx;
	
	
	@Column(nullable = false, unique = true) // 제약조건 : Unique Value
	private String id;	   
	
	private String pw;
	private String name;	
	private int age;	
}
