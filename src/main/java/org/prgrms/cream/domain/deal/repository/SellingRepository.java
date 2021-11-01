package org.prgrms.cream.domain.deal.repository;

import java.util.Optional;
import org.prgrms.cream.domain.deal.domain.SellingBid;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.user.domain.User;
import java.util.List;
import org.prgrms.cream.domain.deal.domain.SellingBid;
import org.prgrms.cream.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellingRepository extends JpaRepository<SellingBid, Long> {

	Optional<SellingBid> findByUserAndProductAndSize(User user, Product product, String size);

	boolean existsByUserAndProductAndSize(User user, Product product, String size);

	List<SellingBid> findFirst2ByProductAndSizeAndStatusOrderBySuggestPriceAscCreatedDateAsc(
		Product product,
		String size,
		String status
	);
}
