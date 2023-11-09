package dev.partenon.museums.contact.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactJpaRepository extends JpaRepository<ContactEntity, ContactPK> {
}
