package org.prgrms.cream.domain.deal.service;

import java.util.List;
import org.prgrms.cream.domain.deal.domain.BuyingBid;
import org.prgrms.cream.domain.deal.domain.Deal;
import org.prgrms.cream.domain.deal.dto.DealHistoryResponse;
import org.prgrms.cream.domain.deal.model.DealStatus;
import org.prgrms.cream.domain.deal.repository.DealRepository;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.user.domain.User;
import org.prgrms.cream.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DealService {

	private final DealRepository dealRepository;
	private final UserService userService;

	public DealService(
		DealRepository dealRepository,
		UserService userService
	) {
		this.dealRepository = dealRepository;
		this.userService = userService;
	}

	@Transactional
	public Deal createDeal(BuyingBid buyingBid, String size, User user, Product product) {
		Deal deal = Deal
			.builder()
			.buyer(buyingBid.getUser())
			.seller(user)
			.product(product)
			.size(size)
			.price(buyingBid.getSuggestPrice())
			.build();

		buyingBid.changeStatus(DealStatus.BID_COMPLETED);

		return dealRepository.save(deal);
	}

	public Deal createDeal(Deal deal) {
		return dealRepository.save(deal);
	}

	@Transactional(readOnly = true)
	public List<DealHistoryResponse> getPendingDealByStatus(Long userId, String status) {
		return dealRepository
			.findAllByBuyerAndBuyingStatusAndIsFinishedFalse(
				userService.findActiveUser(userId),
				status
			)
			.stream()
			.map(
				deal ->
					new DealHistoryResponse(
						deal.getId(),
						deal
							.getProduct()
							.getImage(),
						deal
							.getProduct()
							.getEnglishName(),
						deal.getSize(),
						deal.getBuyingStatus()
					)
			)
			.toList();
	}

	@Transactional(readOnly = true)
	public List<DealHistoryResponse> getAllPendingDealHistory(Long userId) {
		return dealRepository
			.findAllByBuyerAndIsFinishedFalse(userService.findActiveUser(userId))
			.stream()
			.map(
				deal ->
					new DealHistoryResponse(
						deal.getId(),
						deal
							.getProduct()
							.getImage(),
						deal
							.getProduct()
							.getEnglishName(),
						deal.getSize(),
						deal.getBuyingStatus()
					)
			)
			.toList();
	}

}
