package com.kfir.chat.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface messageRepository extends JpaRepository<chatMessage,Long> {
    boolean existsByReceiver(String receiver);

}
