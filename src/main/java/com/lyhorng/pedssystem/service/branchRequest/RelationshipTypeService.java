package com.lyhorng.pedssystem.service.branchRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyhorng.pedssystem.model.branchRequest.RelationshipType;
import com.lyhorng.pedssystem.repository.branchRequest.RelationshipTypeRepository;

@Service
public class RelationshipTypeService {

    @Autowired
    private RelationshipTypeRepository relationshipTypeRepository;

    // Get all relationship types
    public List<RelationshipType> getAll() {
        return relationshipTypeRepository.findAll();
    }

    // Get relationship type by ID
    public RelationshipType getById(Long id) {
        return relationshipTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relationship Type not found with ID: " + id));
    }

    // Create new relationship type
    public RelationshipType create(RelationshipType relationshipType) {
        return relationshipTypeRepository.save(relationshipType);
    }

    // Update relationship type
    public RelationshipType update(Long id, RelationshipType relationshipType) {
        RelationshipType existing = getById(id);
        existing.setName(relationshipType.getName());
        existing.setDescription(relationshipType.getDescription());
        return relationshipTypeRepository.save(existing);
    }

    // Delete relationship type
    public void delete(Long id) {
        if (!relationshipTypeRepository.existsById(id)) {
            throw new RuntimeException("Relationship Type not found with ID: " + id);
        }
        relationshipTypeRepository.deleteById(id);
    }
}