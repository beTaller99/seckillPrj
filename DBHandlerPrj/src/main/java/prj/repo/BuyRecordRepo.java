package prj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prj.model.BuyRecord;

/**
 * @className: BuyRecordRepo
 * @Description: TODO
 * @version: jdk11
 * @author: asher
 * @date: 2022/6/4 14:49
 */
@Repository
public interface BuyRecordRepo extends JpaRepository<BuyRecord, String> {
}
