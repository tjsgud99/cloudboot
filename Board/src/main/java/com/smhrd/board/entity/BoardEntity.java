package com.smhrd.board.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "board") 
@Data
@AllArgsConstructor
@NoArgsConstructor 
public class BoardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String writer;
	@Column(nullable = false , columnDefinition = "TEXT")
	private String content;
	
	private String imgPath;
	
	@Column(nullable = false,updatable = false)
	private LocalDate writeDay;
	
	@PrePersist
	protected void onWriteDay(){
		this.writeDay = LocalDate.now();
	}
}
