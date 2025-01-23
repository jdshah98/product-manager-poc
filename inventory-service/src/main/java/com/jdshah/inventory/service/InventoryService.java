package com.jdshah.inventory.service;

import com.jdshah.inventory.entity.Inventory;
import com.jdshah.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryByProductId(String productId) {
        return inventoryRepository.findById(productId).orElse(null);
    }

    public Inventory updateInventory(String productId, int quantity) {
        Inventory inventory = inventoryRepository.findById(productId).orElse(new Inventory(productId, quantity));
        inventory.setQuantity(quantity);
        return inventoryRepository.save(inventory);
    }
}