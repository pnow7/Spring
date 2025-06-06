package com.myspring.pro301.board.service;

import java.util.List;
import java.util.Map;

import com.myspring.pro301.board.vo.ArticleVO;

public interface BoardService {

	public List<ArticleVO> listArticles() throws Exception;

	public int addNewArticle(Map articleMap) throws Exception;
}