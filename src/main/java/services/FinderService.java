package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import repositories.FinderRepository;
import domain.Finder;

@Service
@Transactional
public class FinderService {
	
	// Managed repository -------------------------------------------
	
		@Autowired
		private FinderRepository finderRepository;
		
		// Supporting services ------------------------------------------
		
		// Simple CRUD methods ------------------------------------------
		
		public Finder create() {
			return new Finder();

		}

		public Collection<Finder> findAll() {
			Assert.notNull(this.finderRepository);
			Collection<Finder> result = this.finderRepository.findAll();
			Assert.notNull(result);
			return result;

		}

		public Finder findOne(final int finderId) {
			Assert.isTrue(finderId != 0);
			Finder result = this.finderRepository.findOne(finderId);
			Assert.notNull(result);
			return result;

		}

		public Finder save(final Finder finder) {
			Assert.notNull(finder);
			
			return this.finderRepository.save(finder);

		}

		public void delete(final Finder finder) {
			Assert.notNull(finder);
			Assert.isTrue(finder.getId() != 0);
			Assert.isTrue(this.finderRepository.exists(finder.getId()));
			this.finderRepository.delete(finder);
		}
		
		// Other business methods -----------------------------------------
		
}

