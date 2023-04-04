package sk.aconsulting.service;

import sk.aconsulting.dto.ItemDto;
import sk.aconsulting.dto.ItemResponse;

import java.util.List;

public interface IItemService {

    ItemDto addItem(ItemDto itemDto);
    ItemDto getItemById(Long id);
    ItemResponse getAllItems(int pageNo, int pageSize);
    ItemDto updateItem(Long id, ItemDto itemDto);
    void deleteItem(Long id);
}
