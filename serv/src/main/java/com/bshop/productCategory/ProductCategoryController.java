package com.bshop.productCategory;

import com.bshop.productCategory.assembler.ProductCategoryAssembler;
import com.bshop.productCategory.dto.ProductCategoryFull;
import com.bshop.productCategory.model.ProductCategory;
import com.bshop.productCategory.model.ProductCategoryRepository;
import com.google.common.collect.Lists;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/prodCategory")
public class ProductCategoryController {
    ProductCategoryRepository productCategoryRepository;
    ProductCategoryAssembler productCategoryAssembler = new ProductCategoryAssembler();

    public ProductCategoryController(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProductCategoryFull>> getCategories() {
        List<ProductCategoryFull> result = null;

        List<ProductCategory> productCategories = Lists.newArrayList(productCategoryRepository.findAll());
        result = productCategories.stream().map(productCategoryAssembler::assembleFullDto).collect(Collectors.toList());


        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
        ResponseEntity responseEntity = new ResponseEntity(result, headers, HttpStatus.OK);

        return responseEntity;
    }

    @Transactional
    @GetMapping()
    @RequestMapping(path = "chainByComponent", method = RequestMethod.GET)
    public List<ProductCategoryFull> getChainByComponent(@RequestParam Integer id) {
        List<ProductCategoryFull> result = Lists.newArrayList();

        Optional<ProductCategory> categoryOptional =  productCategoryRepository.findById(id);
        if(categoryOptional.isPresent()){
            ProductCategory category = categoryOptional.get();
            do {
                result.add(productCategoryAssembler.assembleFullDto(category));
                category = category.getParent();
            } while (category != null);
        }

        return result;
    }

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public ResponseEntity saveCategories(@RequestBody List<ProductCategoryFull> categoriesToAdd) {
        if (categoriesToAdd != null) {
            List<ProductCategoryFull> parents = categoriesToAdd.stream().filter(cat -> cat.parentId == null).collect(Collectors.toList());

            for (ProductCategoryFull categoryToAdd : parents) {
                persistNestedCategory(categoryToAdd, null, categoriesToAdd);
            }
        }

        List<ProductCategory> all = Lists.newArrayList(productCategoryRepository.findAll());
        Set<String> set = categoriesToAdd.stream().map(item -> item.title).collect(Collectors.toSet());
        List<ProductCategory> toDelete = all.stream().filter(cat -> !set.contains(cat.getTitle())).collect(Collectors.toList());

        productCategoryRepository.deleteAll(toDelete);

        return ResponseEntity.ok().build();
    }

    private void persistNestedCategory(ProductCategoryFull item, ProductCategory parent, List<ProductCategoryFull> list) {
        List<ProductCategoryFull> children = list.stream().filter(cat -> cat.parentId == item.id).collect(Collectors.toList());

        ProductCategory persistedItem = productCategoryAssembler.assembleModel(item);
        if(persistedItem.getId() < 0) {
            persistedItem.setId(null);
        }
        if (parent != null) {
            persistedItem.setParent(parent);
        }
        productCategoryRepository.save(persistedItem);

        for (ProductCategoryFull cat : children) {
            persistNestedCategory(cat, persistedItem, list);
        }

    }
}
