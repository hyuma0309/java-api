package com.teamlab.engineering.restfulapi.repository;

import com.teamlab.engineering.restfulapi.entitiy.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * ログリポジトリ
 *
 * @author asada
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

  /**
   * ログのデータを、開始日・終了日で絞り込む
   *
   * @param startDate 開始日
   * @param endDate 終了日
   * @return 開始日から集計日までの間の集計日
   */
  List<Log> findByAggregateDateBetween(LocalDate startDate, LocalDate endDate);
}
