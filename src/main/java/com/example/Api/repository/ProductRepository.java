package com.example.Api.repository;

import com.example.Api.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品リポジトリ
 *
 * @author asada
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  /**
   * タイトルが部分一致の商品を取得
   *
   * @param title 商品タイトル
   * @return 商品情報のリスト
   */
  List<Product> findByTitleContaining(String title);

  /**
   * タイトルが完全一致の商品を取得
   *
   * @param title 商品タイトル
   * @return 一件の商品情報
   */
  Product findByTitleEquals(String title);

  /**
   * IDが異なる同一タイトルの商品を取得
   *
   * @param title 商品タイトル
   * @param id 商品ID
   * @return 一件の商品情報
   */
  Product findByTitleEqualsAndIdNot(String title, long id);
}
