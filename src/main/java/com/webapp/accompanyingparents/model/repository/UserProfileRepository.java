package com.webapp.accompanyingparents.model.repository;


import com.webapp.accompanyingparents.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {
    UserProfile findUserProfileByAccountEmail(String email);
}
