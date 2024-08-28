package com.example.tripit.user.repository;

import com.example.tripit.user.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {

    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
<<<<<<< HEAD

=======
    
>>>>>>> 2c389731a2c1c1f7297d5555a47cd92d5cf0241a
    Long deleteByEmail(String email);
}
