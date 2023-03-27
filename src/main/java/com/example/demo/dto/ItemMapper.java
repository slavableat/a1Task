package com.example.demo.dto;

import com.example.demo.model.Crcy;
import com.example.demo.model.Item;
import com.example.demo.repository.CrcyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemMapper {
    private final CrcyRepository crcyRepository;

    @Autowired
    public ItemMapper(CrcyRepository crcyRepository) {
        this.crcyRepository = crcyRepository;
    }

    public Item itemFromDTO(ItemDTO itemDTO) {
        Item item = new Item();

        item.setDescription(itemDTO.getMaterialDescription());

        item.setQuantity(itemDTO.getQuantity());
        item.setBUn(itemDTO.getBUn());
        Optional<Crcy> crcy = crcyRepository.findByTitle(itemDTO.getCrcy());
        if (crcy.isPresent()) {
            item.setCrcy(crcy.get());
        } else item.setCrcy(new Crcy(null, itemDTO.getCrcy()));
        item.setAmount(itemDTO.getAmount());
        item.setOrdinalNumber(itemDTO.getOrdinalNumber());
        return item;
    }

    public ItemDTO itemDtoFromItem(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setMaterialDescription(item.getDescription());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setBUn(item.getBUn());
        itemDTO.setCrcy(item.getCrcy().getTitle());
        itemDTO.setAmount(item.getAmount());
        itemDTO.setOrdinalNumber(item.getOrdinalNumber());
        return itemDTO;
    }
}
