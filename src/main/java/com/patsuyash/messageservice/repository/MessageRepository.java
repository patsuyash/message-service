package com.patsuyash.messageservice.repository;

import com.patsuyash.messageservice.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {
}
