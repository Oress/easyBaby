package com.bshop.productChar;

import com.bshop.productChar.assembler.ProductCharAssembler;
import com.bshop.productChar.dto.ProductCharFull;
import com.bshop.productChar.model.ProductChar;
import com.bshop.productChar.model.ProductCharRepository;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/prodChar")
public class ProductCharController {
    private ProductCharAssembler productCharAssembler = new ProductCharAssembler();

    private ProductCharRepository productCharRepository;

    public ProductCharController(ProductCharRepository productCharRepository) {
        this.productCharRepository = productCharRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ProductCharFull> getAll() {
        List<ProductCharFull> result = Lists.newArrayList();

        List<ProductChar> prodChars = Lists.newArrayList(productCharRepository.findAll());
        result = prodChars.stream().map(productCharAssembler::assebleFullDto).collect(Collectors.toList());

        return result;
    }

    @RequestMapping(path = "like", method = RequestMethod.GET)
    public List<ProductCharFull> getByNameLike(@RequestParam String title) {
        List<ProductCharFull> result = Lists.newArrayList();

        if (!Strings.isNullOrEmpty(title)) {
            List<ProductChar> prodChars = Lists.newArrayList(productCharRepository.findByTitleStartingWith(title));
            result = prodChars.stream().map(productCharAssembler::assebleFullDto).collect(Collectors.toList());
        }

        return result;
    }

    // TODO: 05.04.2020 change return type
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void addProductChars(@RequestBody List<ProductCharFull> productCharsToAdd) {
        if (productCharsToAdd != null) {
            List<ProductChar> newProductChars = productCharsToAdd.stream().map(productCharAssembler::assembleModel).collect(Collectors.toList());
            productCharRepository.saveAll(newProductChars);
        }
    }
}
