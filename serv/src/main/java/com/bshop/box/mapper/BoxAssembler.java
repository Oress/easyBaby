package com.bshop.box.mapper;

import com.bshop.box.dto.BoxFull;
import com.bshop.box.dto.BoxShort;
import com.bshop.box.model.Box;

public class BoxAssembler {

    public BoxShort assebleShortDto(Box model) {
        BoxShort result = new BoxShort();
        result.id = model.getId();
        result.title = model.getTitle();
        result.description = model.getDescription();
        result.price = model.getPrice();
//        result.image = model.getImage();
        return result;
    }

    public BoxFull assebleFullDto(Box model) {
        BoxFull result = new BoxFull();
        result.id = model.getId();
        result.title = model.getTitle();
        result.description = model.getDescription();
        result.price = model.getPrice();
//        result.image = model.getImage();
        return result;
    }

    public Box assembleModel(BoxFull dto) {
        Box result = new Box();
        result.setTitle(dto.title);
        result.setDescription(dto.description);
        result.setPrice(dto.price);

        // TODO: handle content
        result.setContent(null);

        // TODO: handle content
        result.setImage(null);

        return result;
    }

}
