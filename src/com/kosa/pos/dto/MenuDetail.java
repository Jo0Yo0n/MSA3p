package com.kosa.pos.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDetail {
	private int rank;
	private Menu menu;

	private int count;
	private double avgScore;

	private List<Review> orderByReviewDateDescList;
	private List<Review> orderByRatingDescList;
	private List<Review> orderByReviewDateAscList;

}
