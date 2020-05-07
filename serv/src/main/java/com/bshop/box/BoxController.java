package com.bshop.box;

import com.bshop.box.dto.BoxFull;
import com.bshop.box.dto.BoxShort;
import com.bshop.box.mapper.BoxAssembler;
import com.bshop.box.model.Box;
import com.bshop.box.model.BoxRepository;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/box")
public class BoxController {
    private BoxRepository boxRepository;
    private BoxAssembler boxAssembler = new BoxAssembler();

    public BoxController(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<BoxShort> getAllBoxesShort() {
        List<BoxShort> result = Lists.newArrayList();

        List<Box> boxList = Lists.newArrayList(boxRepository.findAll());
        result = boxList.stream().map(model -> boxAssembler.assebleShortDto(model)).collect(Collectors.toList());

        return result;
    }

    @RequestMapping(path = "/{boxId}", method = RequestMethod.GET)
    public BoxFull getProduct(@PathVariable Integer boxId) {
        BoxFull result = null;

        Optional<Box> boxOptional = boxRepository.findById(boxId);
        if (boxOptional.isPresent()) {
            result = boxAssembler.assebleFullDto(boxOptional.get());
        }

        return result;
    }


    //change return result
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void addNewBoxes(@RequestBody List<BoxFull> boxesToAdd) {
        // TODO: Validation logic
        List<Box> newBoxes = boxesToAdd.stream().map(dto -> boxAssembler.assembleModel(dto)).collect(Collectors.toList());
        boxRepository.saveAll(newBoxes);
    }

}
