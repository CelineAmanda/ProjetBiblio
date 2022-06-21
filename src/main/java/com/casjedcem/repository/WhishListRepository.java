
package com.casjedcem.repository;

import com.casjedcem.model.User;
import com.casjedcem.model.WishList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WhishListRepository extends JpaRepository<WishList, Long> {
	
	public List<WishList> findByUser(User user);
	
	@Query("DELETE FROM WishList c WHERE c.user.id = ?1 AND c.document.id = ?2")
	@Modifying
	public void deleteByUserAndDocument(Long userId, long documentId);
	
}
