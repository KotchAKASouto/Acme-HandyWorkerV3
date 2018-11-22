
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Finder;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	//Managed repository---------------------------------
	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	//Suporting services---------------------------------
	private FinderService			finderService;


	//Simple CRUD methods--------------------------------
	public HandyWorker create() {
		HandyWorker hw;
		Finder find;

		find = this.finderService.create();
		hw = new HandyWorker();

		find.setHandyWorker(hw);
		hw.setMake(hw.getName() + hw.getMiddleName() + hw.getSurname());
		return hw;
	}

	public Collection<HandyWorker> findAll() {
		Collection<HandyWorker> result;
		result = this.handyWorkerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public HandyWorker findOne(final int handyWorkerId) {
		HandyWorker hw;
		hw = this.handyWorkerRepository.findOne(handyWorkerId);
		Assert.notNull(hw);
		return hw;
	}

	public HandyWorker save(final HandyWorker handyWorker) {

		//Seguridad(Sacacr el principal y comprobar que es el mismo que el que estoy metiendo) Comprobar el ID para que puedas registrarte 
		LoginService.getPrincipal();
		Assert.notNull(handyWorker);
		HandyWorker hw;
		hw = this.handyWorkerRepository.save(handyWorker);
		return hw;
	}

	//Other business methods----------------------------

	public HandyWorker findByPrincipal() {
		HandyWorker hw;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		hw = this.findByUserAccount(userAccount);
		Assert.notNull(hw);

		return hw;
	}

	public HandyWorker findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		HandyWorker result;

		result = this.handyWorkerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Collection<HandyWorker> handyWorkersTenPerCentMore() {

		final Collection<HandyWorker> result = this.handyWorkerRepository.handyWorkersTenPerCentMore();
		Assert.notNull(result);
		return result;

	}

	public Collection<HandyWorker> topThreeHandyWorkersComplaints() {

		final Collection<HandyWorker> handyWorkers = this.handyWorkerRepository.rankingHandyWorkersComplaints();
		Assert.notNull(handyWorkers);

		final List<HandyWorker> ranking = new ArrayList<HandyWorker>();
		ranking.addAll(handyWorkers);
		final Collection<HandyWorker> result = ranking.subList(0, 3);

		return result;

	}

}