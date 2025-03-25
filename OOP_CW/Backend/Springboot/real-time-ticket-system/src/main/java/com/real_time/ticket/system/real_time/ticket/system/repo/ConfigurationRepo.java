package com.real_time.ticket.system.real_time.ticket.system.repo;

// Importing the ConfigurationEntity class to work with the configuration data
import com.real_time.ticket.system.real_time.ticket.system.entities.ConfigurationEntity;

// Importing JpaRepository for database operations and Repository annotation for marking this interface as a repository
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This annotation tells Spring that this is a repository (data access layer)
// It helps with managing data interactions for the ConfigurationEntity
@Repository
public interface ConfigurationRepo extends JpaRepository<ConfigurationEntity, Long> {
    // This interface extends JpaRepository, which provides built-in methods for database operations
    // ConfigurationEntity: The type of the entity this repository will handle
    // Long: The type of the entity's primary key (in this case, the ID is of type Long)
}
