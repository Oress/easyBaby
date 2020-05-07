package com.bshop.inventoryItem;

import com.bshop.inventoryItem.assembler.InventoryItemAssembler;
import com.bshop.inventoryItem.dto.InventoryItemFull;
import com.bshop.inventoryItem.model.InventoryItem;
import com.bshop.inventoryItem.model.InventoryItemRepository;
import com.bshop.product.model.Product;
import com.bshop.product.model.ProductRepository;
import com.bshop.user.model.User;
import com.bshop.user.model.UserRepository;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "iitem")
public class InventoryItemController {
    private InventoryItemRepository inventoryItemrepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    private InventoryItemAssembler inventoryItemAssembler = new InventoryItemAssembler();

    public InventoryItemController(InventoryItemRepository inventoryItemrepository, UserRepository userRepository, ProductRepository productRepository) {
        this.inventoryItemrepository = inventoryItemrepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @RequestMapping(path = "/product", method = RequestMethod.POST)
    public List<InventoryItemFull> getByProdId(@RequestParam Integer id) {
        List<InventoryItemFull> result = Lists.newArrayList();

        List<InventoryItem> list = inventoryItemrepository.findByProductId(id);
        result = list.stream().map(inventoryItemAssembler::assebleFullDto).collect(Collectors.toList());

        return result;
    }

    // TODO: 05.04.2020 Add validation logic
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void addNewItems(@RequestBody List<InventoryItemFull> itemsToAdd) {
        if (itemsToAdd != null) {
            List<InventoryItem> newInventoryItems = Lists.newArrayList();

            Set<Integer> productIds = itemsToAdd.stream().map(item -> item.productId).collect(Collectors.toSet());
            Set<Integer> staffIds = itemsToAdd.stream().map(item -> item.addedById).collect(Collectors.toSet());

            Map<Integer, Product> productMap = Lists.newArrayList(productRepository.findAllById(productIds))
                    .stream().collect(Collectors.toMap(Product::getId, item -> item));
            Map<Integer, User> staffMap  = Lists.newArrayList(userRepository.findAllById(staffIds))
                    .stream().collect(Collectors.toMap(User::getId, item -> item));

            for (InventoryItemFull itemDto : itemsToAdd) {
                InventoryItem item = inventoryItemAssembler.assemblePartialModel(itemDto);

                User addedBy = staffMap.get(itemDto.addedById);
                Product product = productMap.get(itemDto.productId);

                item.setProduct(product);
                item.setAddedBy(addedBy);

                newInventoryItems.add(item);
            }
            inventoryItemrepository.saveAll(newInventoryItems);
        }
    }
}
